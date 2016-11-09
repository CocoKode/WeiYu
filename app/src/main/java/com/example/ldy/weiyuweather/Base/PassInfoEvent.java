package com.example.ldy.weiyuweather.Base;

import com.example.ldy.weiyuweather.Gson.Weather;

/**
 * Created by LDY on 2016/10/17.
 */
public class PassInfoEvent {
    private Weather mWeather;

    public PassInfoEvent(Weather weather) {
        mWeather = weather;
    }
}
