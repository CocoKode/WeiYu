package com.example.ldy.weiyuweather.Screens;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ldy.weiyuweather.Adapters.mainAdapter;
import com.example.ldy.weiyuweather.Base.ChangeCityEvent;
import com.example.ldy.weiyuweather.Base.SimpleSubscriber;
import com.example.ldy.weiyuweather.Json.Weather;
import com.example.ldy.weiyuweather.NetWork.RetrofitSingleton;
import com.example.ldy.weiyuweather.R;
import com.example.ldy.weiyuweather.Utils.RxBus;
import com.example.ldy.weiyuweather.Utils.SharedPreferenceUtil;
import com.example.ldy.weiyuweather.Utils.ToastUtil;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.thbs.skycons.library.Cloud;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends RxAppCompatActivity {
    @Bind(R.id.vp_main)
    ViewPager viewPager;

    private static Weather mWeather = new Weather();

    private static mainAdapter mMainAdapter;

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

        /*
        RxBus.getDefault().toObservable(ChangeCityEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new SimpleSubscriber<ChangeCityEvent>() {
                            @Override
                            public void onNext(ChangeCityEvent changeCityEvent) {
                                load();
                            }
                        }
                );
        */

        ButterKnife.bind(this);
        load();
        //initIcons();
        initViewPager();
    }

    private void initViewPager() {
        mMainAdapter = new mainAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mMainAdapter);
        viewPager.setOffscreenPageLimit(2);

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
        navigationTabBar.setViewPager(viewPager, 0);
    }

    //加载数据到mWeather中
    private void load() {
        fetchDataFromNetwork()
                .doOnError(throwable -> {
                    SharedPreferenceUtil.getInstance().setCityId("CN101110101");
                })
                .subscribe(
                        new Subscriber<Weather>() {
            @Override
            public void onCompleted() {
                ToastUtil.showShort("加载完毕噜");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Weather weather) {
                mWeather.status = weather.status;
                mWeather.api = weather.api;
                mWeather.basic = weather.basic;
                mWeather.suggestion = weather.suggestion;
                mWeather.now = weather.now;
                mWeather.dailyForecast = weather.dailyForecast;
                mWeather.hourlyForecast = weather.hourlyForecast;
                SharedPreferenceUtil.save(mWeather);
            }
        });
    }

    //从网络获取数据
    private Observable<Weather> fetchDataFromNetwork() {
        String cityId = "CN101110101";
        return RetrofitSingleton.getInstance()
                .fetchWeather(cityId)
                .compose(this.bindToLifecycle());
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
