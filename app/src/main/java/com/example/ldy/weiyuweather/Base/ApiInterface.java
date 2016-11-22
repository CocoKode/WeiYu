package com.example.ldy.weiyuweather.Base;

import com.example.ldy.weiyuweather.Gson.WeatherApi;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LDY on 2016/10/9.
 * 更新接口
 */
public interface ApiInterface {
    String HOST = "https://api.heweather.com/v5/";

    @GET("weather")
    Observable<WeatherApi> mWeather(@Query("city") String city, @Query("key") String key);
}
