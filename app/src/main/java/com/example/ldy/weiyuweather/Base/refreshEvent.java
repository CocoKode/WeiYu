package com.example.ldy.weiyuweather.Base;

import com.example.ldy.weiyuweather.Gson.Weather;

/**
 * Created by LDY on 2016/10/29.
 */
public class refreshEvent {
    private Weather mWeather;
    public refreshEvent (Weather weather) {
        mWeather = weather;
    }

    public Weather getWeather() {
        return mWeather;
    }
}
