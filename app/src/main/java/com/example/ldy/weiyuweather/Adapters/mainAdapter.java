package com.example.ldy.weiyuweather.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ldy.weiyuweather.Gson.Weather;
import com.example.ldy.weiyuweather.Screens.DetailPagerFragment;
import com.example.ldy.weiyuweather.Screens.HorizontalPagerFragment;
import com.example.ldy.weiyuweather.Screens.TablePagerFragment;

/**
 * Created by LDY on 2016/9/29.
 */
public class MainAdapter extends FragmentStatePagerAdapter {
    Weather mWeather = new Weather();

    private final static int COUNT = 4;

    private final static int HORIZONTAL = 0;
    private final static int TABLES = 1;
    private final static int DETAIL_HORIZONTAL = 2;

    public MainAdapter(FragmentManager fm, Weather weather) {
        super(fm);
        mWeather = weather;
    }

    public void updateWeather(Weather weather) {
        mWeather = weather;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case DETAIL_HORIZONTAL:
                return DetailPagerFragment.newInstance(mWeather);
            case TABLES:
                return TablePagerFragment.newInstance(mWeather);
            case HORIZONTAL:
            default:
                return HorizontalPagerFragment.newInstance(mWeather);
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
