package com.example.ldy.weiyuweather.Json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LDY on 2016/10/10.
 */
public class WeatherApi {
    @SerializedName("HeWeather data service 3.0")
    @Expose
    public List<Weather> mHeWeatherDataService30 = new ArrayList<>();
}
