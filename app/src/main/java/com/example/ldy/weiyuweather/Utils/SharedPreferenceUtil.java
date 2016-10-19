package com.example.ldy.weiyuweather.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ldy.weiyuweather.Base.BaseApplication;
import com.example.ldy.weiyuweather.Json.Weather;

/**
 * Created by LDY on 2016/10/14.
 */
public class SharedPreferenceUtil {
    private static final String CITY_ID = "城市代码";

    private static  SharedPreferences mSpfs;

    public static SharedPreferenceUtil getInstance() {
        return SPHolder.sInstance;
    }

    private static class SPHolder {
        private static final SharedPreferenceUtil sInstance = new SharedPreferenceUtil();
    }

    //构造方法
    private SharedPreferenceUtil() {
        mSpfs = BaseApplication.getmAppContext().getSharedPreferences("weatherSetting", Context.MODE_PRIVATE);
    }

    public static void save(Weather weather) {
        saveWeekWeather(weather);
        saveSuggestions(weather);
    }

    private static void saveWeekWeather(Weather weather) {
        try {
            putString("day1", "今日");
            putString("day2", "明日");
            putString("day3", Utils.dayForWeek(weather.dailyForecast.get(2).date));
            putString("day4", Utils.dayForWeek(weather.dailyForecast.get(3).date));
            putString("day5", Utils.dayForWeek(weather.dailyForecast.get(4).date));
            putString("day6", Utils.dayForWeek(weather.dailyForecast.get(5).date));
            putString("day7", Utils.dayForWeek(weather.dailyForecast.get(6).date));

            putString("day1Info", weather.dailyForecast.get(0).cond.txtD);
            putString("day2Info", weather.dailyForecast.get(1).cond.txtD);
            putString("day3Info", weather.dailyForecast.get(2).cond.txtD);
            putString("day4Info", weather.dailyForecast.get(3).cond.txtD);
            putString("day5Info", weather.dailyForecast.get(4).cond.txtD);
            putString("day6Info", weather.dailyForecast.get(5).cond.txtD);
            putString("day7Info", weather.dailyForecast.get(6).cond.txtD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveSuggestions(Weather weather) {
        putString("dressTxt", weather.suggestion.drsg.txt);
        putString("uvTxt", weather.suggestion.uv.txt);
        putString("fluTxt", weather.suggestion.flu.txt);
        putString("carTxt", weather.suggestion.cw.txt);
        putString("sportTxt", weather.suggestion.sport.txt);
        putString("travelTxt", weather.suggestion.trav.txt);
    }

    public void setCityId(String cityId) {
        mSpfs.edit().putString(CITY_ID, cityId).apply();
    }
    public String getCityId() {
        return mSpfs.getString(CITY_ID, "CN101110101");
    }

    public static void putInt(String key, int value) {
        mSpfs.edit().putInt(key, value).apply();
    }
    public int getInt(String key, int defValue) {
        return mSpfs.getInt(key, defValue);
    }

    public static void putString(String key, String value) {
        mSpfs.edit().putString(key, value).apply();
    }
    public String getString(String key, String defValue) {
        return mSpfs.getString(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        mSpfs.edit().putBoolean(key, value).apply();
    }
    public boolean getBoolean(String key, boolean defValue) {
        return mSpfs.getBoolean(key, defValue);
    }
}

