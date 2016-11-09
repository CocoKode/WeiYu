package com.example.ldy.weiyuweather.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.ldy.weiyuweather.Base.refreshEvent;
import com.example.ldy.weiyuweather.Gson.Weather;
import com.example.ldy.weiyuweather.Screens.DetailPagerFragment;
import com.example.ldy.weiyuweather.Screens.HorizontalPagerFragment;
import com.example.ldy.weiyuweather.Screens.TablePagerFragment;
import com.example.ldy.weiyuweather.Utils.RxBus;

import java.util.ArrayList;

/**
 * Created by LDY on 2016/9/29.
 */
public class MainAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> mFragments;
    Weather mWeather = new Weather();

    private final static int COUNT = 4;

    private final static int HORIZONTAL = 0;
    private final static int TABLES = 1;
    private final static int DETAIL_HORIZONTAL = 2;

    public MainAdapter(FragmentManager fm, Weather weather) {
        super(fm);
        mWeather = weather;
        Log.d("mainadapter", "构造");
        //initFragments();
    }

    public void updateWeather(Weather weather) {
        mWeather = weather;
    }
    private void initFragments() {
        if(mWeather.status == null){
            Log.d("mainadapter", "mweather为空");
        } else {
            Log.d("mainadapter", "mweather不为空" + mWeather.status);
        }

        ArrayList<Fragment> fms = new ArrayList<>();
        fms.add(HorizontalPagerFragment.newInstance(mWeather));
        fms.add(TablePagerFragment.newInstance(mWeather));
        fms.add(new DetailPagerFragment());

        setFragments(fms);

        //Log.d("mainadapter", "发出事件");
        //RxBus.getDefault().post(new refreshEvent(mWeather));
    }

    private void setFragments(ArrayList<Fragment> fms) {
        if (mFragments != null)
            mFragments.clear();
        mFragments = fms;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("MainAdapter", "getItem");
        //Log.d("MainAdapter", "size:" + mFragments.size());
        switch (position) {
            case DETAIL_HORIZONTAL:
                return DetailPagerFragment.newInstance(mWeather);
            case TABLES:
                return TablePagerFragment.newInstance(mWeather);
            case HORIZONTAL:
            default:
                return HorizontalPagerFragment.newInstance(mWeather);
        }
        //Log.d("MainAdapter", "position:" + position);
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
