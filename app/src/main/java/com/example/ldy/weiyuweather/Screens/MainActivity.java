package com.example.ldy.weiyuweather.Screens;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.TintAwareDrawable;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldy.weiyuweather.Adapters.MainAdapter;
import com.example.ldy.weiyuweather.Base.BaseApplication;
import com.example.ldy.weiyuweather.Base.ChangeInfoEvent;
import com.example.ldy.weiyuweather.Base.SimpleSubscriber;
import com.example.ldy.weiyuweather.Database.Bean.CityId;
import com.example.ldy.weiyuweather.Database.Handle.HandleDatabase;
import com.example.ldy.weiyuweather.Gson.Weather;
import com.example.ldy.weiyuweather.NetWork.RetrofitSingleton;
import com.example.ldy.weiyuweather.R;
import com.example.ldy.weiyuweather.Service.AutoUpdateService;
import com.example.ldy.weiyuweather.Utils.RxBus;
import com.example.ldy.weiyuweather.Utils.SharedPreferenceUtil;
import com.example.ldy.weiyuweather.Utils.ToastUtil;
import com.example.ldy.weiyuweather.Utils.Utils;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongFunction;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

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

    //图标代号
    private int UNkNOWN = 0;
    private int SUNNY = 1;
    private int NIGHT = 2;
    private int PARTLY_CLOUD_DAY = 3;
    private int PARTLY_CLOUD_NIGHT = 4;
    private int CLOUDY = 5;
    private int RAIN = 6;
    private int HEAVY_RAIN = 7;
    private int SNOW = 8;
    private int WIND = 9;
    private int FOG = 10;
    private int THUNDER = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initWidget();
        initViewPager();
        HandleDatabase.handleWithRx(BaseApplication.getmAppContext());
        load();
        //initIcons();
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

        //监听城市更改事件
        RxBus.getDefault().toObservable(ChangeInfoEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new SimpleSubscriber<ChangeInfoEvent>() {
                            @Override
                            public void onNext(ChangeInfoEvent changeInfoEvent) {
                                load();
                            }
                        }
                );

        //监听刷新事件
        /*
        RxBus.getDefault().toObservable(refreshEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new SimpleSubscriber<refreshEvent>() {
                            @Override
                            public void onNext(refreshEvent refreshEvent) {
                                Log.d("mainactivity", "刷新前");
                                mMainAdapter.notifyDataSetChanged();
                                Log.d("mainacyivity", "刷新后");
                            }
                        }
                );
        */
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
            //RxBus.getDefault().post(new ChangeInfoEvent());
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
            //mSwLayout.setRefreshing(false);
            //return;
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
                    //SharedPreferenceUtil.getInstance().setCityId("CN101110101");
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
                ToastUtil.showShort("加载完毕噜");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Weather weather) {
                /*
                mWeather.status = weather.status;
                mWeather.aqi = weather.aqi;
                mWeather.basic = weather.basic;
                mWeather.suggestion = weather.suggestion;
                mWeather.now = weather.now;
                mWeather.dailyForecast = weather.dailyForecast;
                mWeather.hourlyForecast = weather.hourlyForecast;
                SharedPreferenceUtil.save(weather);
                */

                mWeather = weather;
                Log.d("mainactivity", "mainadapter刷新");
                mMainAdapter.updateWeather(weather);
                mMainAdapter.notifyDataSetChanged();
                //RxBus.getDefault().post(new refreshEvent(mWeather));
            }
        });
    }

    //从网络获取数据
    private Observable<Weather> fetchDataFromNetwork() {
        String cityId = SharedPreferenceUtil.getInstance().getCityId();
        Log.d("mainactivity", "当前城市是" + cityId);
        return RetrofitSingleton.getInstance()
                .fetchWeather(cityId)
                .compose(this.bindToLifecycle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (SharedPreferenceUtil.getInstance().getUpdateChecked()) {
            if (resultCode == 10) {
                if (data == null) {
                    Log.d("mainactivity", "返回值为空");
                    return;
                }
                int updateHour = data.getIntExtra("test", 0);
                if (updateHour == 0) return;
                Log.d("mainactivity", "" + updateHour);

                if (Utils.isServiceRunning(this, UPDATE_SERVICE)) {
                    Log.d("mainactivity", "停止服务，开启新服务");
                    Intent stopIntent = new Intent(this, AutoUpdateService.class);
                    stopService(stopIntent);
                }

                Log.d("mainactivity", "开启服务" + Utils.isServiceRunning(this, UPDATE_SERVICE));
                Intent startIntent = new Intent(this, AutoUpdateService.class);
                startIntent.putExtra("updateTime", updateHour);
                startService(startIntent);
            }
            Log.d("mainactivity", "返回码" + resultCode);
        } else {
            if (Utils.isServiceRunning(this, UPDATE_SERVICE)) {
                Log.d("mainactivity", "若有服务存在则停止");
                Intent stopIntent = new Intent(this, AutoUpdateService.class);
                stopService(stopIntent);
            }
            Log.d("mainactivity", "没有服务存在");
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

    //初始化天气图标
    private void initIcons() {
        SharedPreferenceUtil.putInt("未知", UNkNOWN);
        SharedPreferenceUtil.putInt("晴", SUNNY);
        SharedPreferenceUtil.putInt("阴", CLOUDY);
        SharedPreferenceUtil.putInt("多云", CLOUDY);
        SharedPreferenceUtil.putInt("少云", CLOUDY);
        SharedPreferenceUtil.putInt("晴间多云", PARTLY_CLOUD_DAY);
        SharedPreferenceUtil.putInt("小雨", RAIN);
        SharedPreferenceUtil.putInt("中雨", RAIN);
        SharedPreferenceUtil.putInt("大雨", HEAVY_RAIN);
        SharedPreferenceUtil.putInt("阵雨", HEAVY_RAIN);
        SharedPreferenceUtil.putInt("雷阵雨", THUNDER);
        SharedPreferenceUtil.putInt("霾", FOG);
        SharedPreferenceUtil.putInt("雾", FOG);
        SharedPreferenceUtil.putInt("小雪", SNOW);
        SharedPreferenceUtil.putInt("中雪", SNOW);
        SharedPreferenceUtil.putInt("大雪", SNOW);
        SharedPreferenceUtil.putInt("有风", WIND);
        SharedPreferenceUtil.putInt("微风", WIND);
        SharedPreferenceUtil.putInt("大风", WIND);
    }
}
