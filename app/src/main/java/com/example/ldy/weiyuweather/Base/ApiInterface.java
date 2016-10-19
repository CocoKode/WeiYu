package com.example.ldy.weiyuweather.Base;

import com.example.ldy.weiyuweather.Json.WeatherApi;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LDY on 2016/10/9.
 */
public interface ApiInterface {
    String HOST = "https://api.heweather.com/x3/";

    @GET("weather")
    Observable<WeatherApi> mWeather(@Query("cityid") String cityId, @Query("key") String key);
}
