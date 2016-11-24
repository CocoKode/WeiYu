package com.example.ldy.weiyuweather.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ldy.weiyuweather.Gson.Weather;
import com.example.ldy.weiyuweather.R;
import com.example.ldy.weiyuweather.Utils.SharedPreferenceUtil;
import com.example.ldy.weiyuweather.Utils.Utils;

import static com.example.ldy.weiyuweather.Utils.Utils.setupItem;

/**
 * Created by LDY on 2016/9/29.
 */
public class HorizontalPagerAdapter extends PagerAdapter{
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private Weather mWeather;

    private Utils.LibraryObject[] LIBRARIES = null;

    public HorizontalPagerAdapter(final Context context, final Weather weather) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mWeather = weather;
        initLibraryObject(mWeather);
    }

    private void initLibraryObject(Weather mWeather) {
        if (mWeather.status == null)
            return;

        LIBRARIES = new Utils.LibraryObject[] {
                new Utils.LibraryObject(
                        matchWeather(mWeather.dailyForecast.get(0).cond.txtD),
                        "今日",
                        mWeather.dailyForecast.get(0).cond.txtD,
                        mWeather.now.tmp
                ),
                new Utils.LibraryObject(
                        matchWeather(mWeather.dailyForecast.get(1).cond.txtD),
                        "明日",
                        mWeather.dailyForecast.get(1).cond.txtD,
                        mWeather.dailyForecast.get(1).tmp.max
                ),
                new Utils.LibraryObject(
                        matchWeather(mWeather.dailyForecast.get(2).cond.txtD),
                        Utils.dayForWeek(mWeather.dailyForecast.get(2).date),
                        mWeather.dailyForecast.get(2).cond.txtD,
                        mWeather.dailyForecast.get(2).tmp.max
                ),
                new Utils.LibraryObject(
                        matchWeather(mWeather.dailyForecast.get(3).cond.txtD),
                        Utils.dayForWeek(mWeather.dailyForecast.get(3).date),
                        mWeather.dailyForecast.get(3).cond.txtD,
                        mWeather.dailyForecast.get(3).tmp.max
                ),
                new Utils.LibraryObject(
                        matchWeather(mWeather.dailyForecast.get(4).cond.txtD),
                        Utils.dayForWeek(mWeather.dailyForecast.get(4).date),
                        mWeather.dailyForecast.get(4).cond.txtD,
                        mWeather.dailyForecast.get(4).tmp.max
                ),
                new Utils.LibraryObject(
                        matchWeather(mWeather.dailyForecast.get(5).cond.txtD),
                        Utils.dayForWeek(mWeather.dailyForecast.get(5).date),
                        mWeather.dailyForecast.get(5).cond.txtD,
                        mWeather.dailyForecast.get(5).tmp.max
                ),
                new Utils.LibraryObject(
                        matchWeather(mWeather.dailyForecast.get(6).cond.txtD),
                        Utils.dayForWeek(mWeather.dailyForecast.get(6).date),
                        mWeather.dailyForecast.get(6).cond.txtD,
                        mWeather.dailyForecast.get(6).tmp.max
                )
        };
    }

    @Override
    public int getCount() {
        return mWeather.status == null ? 0 : LIBRARIES.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view;
        view = mLayoutInflater.inflate(R.layout.item, container, false);
        setupItem(view, LIBRARIES[position], mContext);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    private int matchWeather(String txtD) {
        switch (txtD) {
            case "阴":
                return R.mipmap.yin;
            case "晴间多云":
            case "多云":
            case "少云":
                return R.mipmap.cloudy;
            case "小雨":
            case "中雨":
            case "大雨":
            case "阵雨":
            case "雨夹雪":
                return R.mipmap.rain3;
            case "雷阵雨":
                return R.mipmap.thunder;
            case "霾":
            case "雾":
                return R.mipmap.fog1;
            case "小雪":
            case "中雪":
            case "大雪":
                return R.mipmap.snow1;
            default:
                return R.mipmap.sunny3;
        }
    }
}
