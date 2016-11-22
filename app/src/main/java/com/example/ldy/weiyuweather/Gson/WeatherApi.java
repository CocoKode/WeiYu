package com.example.ldy.weiyuweather.Gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LDY on 2016/10/10.
 * 更新接口
 */
public class WeatherApi {
    @SerializedName("HeWeather5")
    @Expose
    public List<Weather> mHeWeather5 = new ArrayList<>();
}
