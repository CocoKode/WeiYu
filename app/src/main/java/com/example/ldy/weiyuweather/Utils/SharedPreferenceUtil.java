package com.example.ldy.weiyuweather.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ldy.weiyuweather.Base.BaseApplication;
import com.example.ldy.weiyuweather.Gson.Weather;

/**
 * Created by LDY on 2016/10/14.
 */
public class SharedPreferenceUtil {
    private static final String CITY_ID = "城市代码";
    private static final String CITY_NAME = "城市名称";
    private static final String CITY_LON = "lon";
    private static final String CITY_LAT = "lat";
    private static final String UPDATE_CHECKED = "update";
    private static final String UPDATE_TIME = "update_time";

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
        saveNowWeather(weather);
        saveWind(weather);
        saveHumidity(weather);
        saveSuggestions(weather);
    }

    private static void saveWeekWeather(Weather weather) {
        try {
            //存入当天日出日落时间
            putString("sunRise", weather.dailyForecast.get(0).astro.sr);
            putString("sunSet", weather.dailyForecast.get(0).astro.ss);

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

            putString("day1MinTmp", weather.dailyForecast.get(0).tmp.min);
            putString("day1MaxTmp", weather.dailyForecast.get(0).tmp.max);
            putString("day2MinTmp", weather.dailyForecast.get(1).tmp.min);
            putString("day2MaxTmp", weather.dailyForecast.get(1).tmp.max);
            putString("day3MinTmp", weather.dailyForecast.get(1).tmp.min);
            putString("day3MaxTmp", weather.dailyForecast.get(2).tmp.max);
            putString("day4MinTmp", weather.dailyForecast.get(3).tmp.min);
            putString("day4MaxTmp", weather.dailyForecast.get(3).tmp.max);
            putString("day5MinTmp", weather.dailyForecast.get(4).tmp.min);
            putString("day5MaxTmp", weather.dailyForecast.get(4).tmp.max);
            putString("day6MinTmp", weather.dailyForecast.get(5).tmp.min);
            putString("day6MaxTmp", weather.dailyForecast.get(5).tmp.max);
            putString("day7MinTmp", weather.dailyForecast.get(6).tmp.min);
            putString("day7MaxTmp", weather.dailyForecast.get(6).tmp.max);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveNowWeather(Weather weather) {
        putString("NowTmp", weather.now.tmp);
        putString("aqi", weather.aqi.city.aqi);
        putString("qlty", weather.aqi.city.qlty);
        putString("pm25", weather.aqi.city.pm25);
        putString("WindDir", weather.aqi.city.pm25);
        putString("WindSc", weather.now.wind.sc);
        //概率后面记得加“%”
        putString("RainPop", weather.dailyForecast.get(0).pop);
    }

    private static void saveWind(Weather weather) {
        putString("day1Wind", weather.dailyForecast.get(0).wind.spd);
        putString("day2Wind", weather.dailyForecast.get(1).wind.spd);
        putString("day3Wind", weather.dailyForecast.get(2).wind.spd);
        putString("day4Wind", weather.dailyForecast.get(3).wind.spd);
        putString("day5Wind", weather.dailyForecast.get(4).wind.spd);
        putString("day6Wind", weather.dailyForecast.get(5).wind.spd);
        putString("day7Wind", weather.dailyForecast.get(6).wind.spd);
    }

    private static void saveHumidity(Weather weather) {
        putString("day1Hum", weather.dailyForecast.get(0).hum);
        putString("day2Hum", weather.dailyForecast.get(1).hum);
        putString("day3Hum", weather.dailyForecast.get(2).hum);
        putString("day4Hum", weather.dailyForecast.get(3).hum);
        putString("day5Hum", weather.dailyForecast.get(4).hum);
        putString("day6Hum", weather.dailyForecast.get(5).hum);
        putString("day7Hum", weather.dailyForecast.get(6).hum);
    }

    private static void saveSuggestions(Weather weather) {
        putString("dressTxt", weather.suggestion.drsg.txt);
        putString("uvTxt", weather.suggestion.uv.txt);
        putString("fluTxt", weather.suggestion.flu.txt);
        putString("carTxt", weather.suggestion.cw.txt);
        putString("sportTxt", weather.suggestion.sport.txt);
        putString("travelTxt", weather.suggestion.trav.txt);
    }

    public boolean getUpdateChecked() {
        return mSpfs.getBoolean(UPDATE_CHECKED, true);
    }

    public void setUpdateChecked(boolean b) {
        mSpfs.edit().putBoolean(UPDATE_CHECKED, b).commit();
    }

    public String getUpdateTime() {
        return mSpfs.getString(UPDATE_TIME, "");
    }

    public void setUpdateTime(String time) {
        mSpfs.edit().putString(UPDATE_TIME, time).commit();
    }

    public void setCityId(String cityId) {
        mSpfs.edit().putString(CITY_ID, cityId).commit();
    }
    public String getCityId() {
        return mSpfs.getString(CITY_ID, "CN101110101");
    }
    public void setCityName(String cityName) {
        mSpfs.edit().putString(CITY_NAME, cityName).commit();
    }
    public String getCityName() {
        return mSpfs.getString(CITY_NAME, "西安");
    }

    public String getCityLon() {
        return mSpfs.getString(CITY_LON, "108.969000");
    }
    public void setCityLon(String lon) {
        mSpfs.edit().putString(CITY_LON, lon).commit();
    }
    public String getCityLat() {
        return mSpfs.getString(CITY_LAT, "34.285000");
    }
    public void setCityLat(String lat) {
        mSpfs.edit().putString(CITY_LAT, lat).commit();
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

