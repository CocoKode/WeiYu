package com.example.ldy.weiyuweather.NetWork;

import com.example.ldy.weiyuweather.Base.ApiInterface;
import com.example.ldy.weiyuweather.Base.BaseApplication;
import com.example.ldy.weiyuweather.Base.HeWeather;
import com.example.ldy.weiyuweather.Base.WeeklyApiInterface;
import com.example.ldy.weiyuweather.Decode.DecodeStringConverterFactory;
import com.example.ldy.weiyuweather.Gson.Weather;
import com.example.ldy.weiyuweather.Gson.WholeWeather;
import com.example.ldy.weiyuweather.Utils.RxUtils;
import com.example.ldy.weiyuweather.Utils.Utils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by LDY on 2016/10/12.
 */
public class RetrofitSingleton {
    private static ApiInterface apiService = null;
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;

    /*
    private static WeeklyApiInterface weeklyApiInterface = null;
    private static Retrofit retrofitWeekly = null;
    private static Weather mWeather = new Weather();
    */

    private void init() {
        initOkHttp();
        initRetrofit();
        apiService = retrofit.create(ApiInterface.class);

        //weeklyApiInterface = retrofitWeekly.create(WeeklyApiInterface.class);
    }

    //单例
    private RetrofitSingleton() { init(); }

    public static RetrofitSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RetrofitSingleton INSTANCE = new RetrofitSingleton();
    }

    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            //设置拦截器，输出网络请求和结果的log
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        //缓存机制,大小50M
        File cacheFile = new File(BaseApplication.cacheDir, "/NetCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = chain -> {
            Request request = chain.request();
            //没网时仅用缓存
            if (!Utils.isNetworkConnected(BaseApplication.getmAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                //FORCE_CACHE,仅仅使用缓存;FORCE_NETWORK, 仅仅使用网络
                //http://www.cnblogs.com/whoislcj/p/5537640.html
            }
            Response response = chain.proceed(request);
            //有网
            if (Utils.isNetworkConnected(BaseApplication.getmAppContext())) {
                int maxAge = 0;
                //有网时设置响应头，缓存超时时间为0
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                //没网时，超时时间为4周，只使用缓存
                int maxStale = 60 * 60 * 24 * 7 * 4;
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        };
        //设置拦截器
        builder.cache(cache).addInterceptor(cacheInterceptor);
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    private static void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        /*
        retrofitWeekly = new Retrofit.Builder()
                .baseUrl(weeklyApiInterface.HOST)
                .client(okHttpClient)
                .addConverterFactory(DecodeStringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        */
    }

    public Observable<Weather> fetchWeather(String city) {
        return apiService.mWeather(city, HeWeather.PERSONAL_KEY)
                .flatMap(weatherApi -> {
                    String status = weatherApi.mHeWeatherDataService30.get(0).status;
                    if ("no more requests".equals(status))
                        return Observable.error(new RuntimeException("查询次数用完噜"));
                    else if ("unknown city".equals(status))
                        return Observable.error(new RuntimeException(String.format("没有查到%s", city)));

                    return Observable.just(weatherApi);
                })
                .map(weatherAPI -> weatherAPI.mHeWeatherDataService30.get(0))
                .compose(RxUtils.rxSchedulerHelper());
    }

    /*封印
    public Observable<WholeWeather> fetchWeather(String city) {
        return apiService.mWeather(city, HeWeather.PERSONAL_KEY)
                .flatMap(weatherApi -> {
                    String status = weatherApi.mHeWeatherDataService30.get(0).status;
                    if ("no more requests".equals(status))
                        return Observable.error(new RuntimeException("查询次数用完噜"));
                    else if ("unknown city".equals(status))
                        return Observable.error(new RuntimeException(String.format("没有查到%s", city)));

                    mWeather = weatherApi.mHeWeatherDataService30.get(0);
                    String lon = mWeather.basic.lon;
                    String lat = mWeather.basic.lat;
                    return weeklyApiInterface.mWeeklyWeather(lon, lat);
                })
                .map(s -> {
                    WholeWeather ww = Utils.saveWeeklyWeather(s);
                    ww.weather = mWeather;
                    return ww;
                })
                .compose(RxUtils.rxSchedulerHelper());
    }

    public Observable<String> fetchWeeklyWeather(String lon, String lat) {
        return weeklyApiInterface.mWeeklyWeather(lon, lat)
                .compose(RxUtils.rxSchedulerHelper());
    }
    */
}
