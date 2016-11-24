package com.example.ldy.weiyuweather.Screens;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldy.weiyuweather.Adapters.MainAdapter;
import com.example.ldy.weiyuweather.Base.BaseApplication;
import com.example.ldy.weiyuweather.Database.Bean.CityId;
import com.example.ldy.weiyuweather.Database.Handle.HandleDatabase;
import com.example.ldy.weiyuweather.Gson.Weather;
import com.example.ldy.weiyuweather.NetWork.RetrofitSingleton;
import com.example.ldy.weiyuweather.R;
import com.example.ldy.weiyuweather.Service.AutoUpdateService;
import com.example.ldy.weiyuweather.Utils.SharedPreferenceUtil;
import com.example.ldy.weiyuweather.Utils.ToastUtil;
import com.example.ldy.weiyuweather.Utils.Utils;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

public class MainActivity extends RxAppCompatActivity {
    @Bind(R.id.vp_main)
    ViewPager mViewPager;

    @Bind(R.id.sw_layout)
    SwipeRefreshLayout mSwLayout;

    @Bind(R.id.toolbar_txt)
    TextView mToolbarTxt;

    @Bind(R.id.settings)
    Button mSettings;

    @Bind(R.id.toolbar_search)
    MaterialSearchView mSearchView;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.error_img)
    ImageView errImg;
    ///////////////////////////////////////////////
    private static Weather mWeather = new Weather();

    private static MainAdapter mMainAdapter;

    private static final String UPDATE_SERVICE = "com.example.ldy.weiyuweather.Service.AutoUpdateService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initWidget();
        initViewPager();
        HandleDatabase.handleWithRx(BaseApplication.getmAppContext());
        load();
    }

    private void initWidget() {
        mViewPager.setVisibility(View.GONE);

        //设置toolbar
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        //设置button
        mSettings.setOnClickListener(view ->  {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivityForResult(intent, 20);
            }
        );

        //设置搜索框
        mSearchView.setVoiceSearch(false);
        mSearchView.setBackgroundResource(R.drawable.search_bg);
        mSearchView.setHint("输入您想查询的城市");
        mSearchView.setCursorDrawable(R.drawable.custom_cursor);
        mSearchView.setEllipsize(true);
        mSearchView.setSubmitOnClick(true);
        mSearchView.setSuggestions(getResources().getStringArray(R.array.query_cities));
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCity(query);
                load();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //设置刷新
        if (mSwLayout != null) {
            mSwLayout.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_orange_dark
            );
            mSwLayout.setOnRefreshListener(
                    () -> mSwLayout.postDelayed(this::load, 1000));
        }
    }

    private void searchCity(String query) {
        if (!TextUtils.isEmpty(query)) {
            List<CityId> ids = CityId.find(CityId.class, "city_Name = ?", query);
            if (ids.size() == 0) {
                ToastUtil.showShort("抱歉，未找到该城市，请重新输入");
                return;
            }
            CityId id = ids.get(0);
            String cityCode = id.cityId;
            String cityName = id.cityName;
            SharedPreferenceUtil.getInstance().setCityId(cityCode);
            SharedPreferenceUtil.getInstance().setCityName(cityName);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        mSearchView.setMenuItem(item);

        return true;
    }

    private void initViewPager() {
        mMainAdapter = new MainAdapter(getSupportFragmentManager(), mWeather);
        mViewPager.setAdapter(mMainAdapter);
        mViewPager.setOffscreenPageLimit(2);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.weather),
                        Color.parseColor("#f9bb72"))
                        .title("一周天气")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.table),
                        Color.parseColor("#a3a3a3"))
                        .title("预测图表")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.notice),
                        Color.parseColor("#499092"))
                        .title("生活指数")
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(mViewPager, 0);
    }

    //加载数据到mWeather中
    private void load() {
        if (!Utils.isNetworkConnected(this)) {
            ToastUtil.showShort("加载失败，请检查你的网络");
        }

        fetchDataFromNetwork()
                .doOnRequest(aLong -> {
                    mToolbarTxt.setText("正在加载...");
                    mSwLayout.setRefreshing(true);
                    mViewPager.setVisibility(View.GONE);
                })
                .doOnError(throwable -> {
                    mToolbarTxt.setText("加载失败");
                    errImg.setVisibility(View.VISIBLE);
                    mViewPager.setVisibility(View.GONE);
                })
                .doOnNext(weather -> {
                    errImg.setVisibility(View.GONE);
                })
                .doOnTerminate(() -> {
                    mSwLayout.setRefreshing(false);
                })
                .subscribe(
                        new Subscriber<Weather>() {
            @Override
            public void onCompleted() {
                mViewPager.setVisibility(View.VISIBLE);
                mToolbarTxt.setText(mWeather.basic.city);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Weather weather) {
                mWeather = weather;
                mMainAdapter.updateWeather(weather);
                mMainAdapter.notifyDataSetChanged();
            }
        });
    }

    //从网络获取数据
    private Observable<Weather> fetchDataFromNetwork() {
        String cityId = SharedPreferenceUtil.getInstance().getCityId();
        return RetrofitSingleton.getInstance()
                .fetchWeather(cityId)
                .compose(this.bindToLifecycle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (SharedPreferenceUtil.getInstance().getUpdateChecked()) {
            if (resultCode == 10) {
                if (data == null)
                    return;

                int updateHour = data.getIntExtra("test", 0);
                if (updateHour == 0) return;

                if (Utils.isServiceRunning(this, UPDATE_SERVICE)) {
                    Intent stopIntent = new Intent(this, AutoUpdateService.class);
                    stopService(stopIntent);
                }

                Intent startIntent = new Intent(this, AutoUpdateService.class);
                startIntent.putExtra("updateTime", updateHour);
                startService(startIntent);
            }
        } else {
            if (Utils.isServiceRunning(this, UPDATE_SERVICE)) {
                Intent stopIntent = new Intent(this, AutoUpdateService.class);
                stopService(stopIntent);
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen())
            mSearchView.closeSearch();
        else if (!Utils.doubleExit()) {
            ToastUtil.showShort("再按一次退出~");
        } else{
            super.onBackPressed();
        }
    }
}
