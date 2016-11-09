package com.example.ldy.weiyuweather.Base;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by LDY on 2016/11/8.
 * 封印
 */
public interface WeeklyApiInterface {
    String HOST = "https://api.caiyunapp.com/v2/";

    @GET("BjEVuqiQs2QXD0-P/{Longitude},{Latitude}/forecast.json")
    Observable<String> mWeeklyWeather(@Path("Longitude") String lon, @Path("Latitude") String lat);
}
