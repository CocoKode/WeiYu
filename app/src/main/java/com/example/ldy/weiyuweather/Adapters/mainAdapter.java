package com.example.ldy.weiyuweather.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ldy.weiyuweather.Screens.DetailPagerFragment;
import com.example.ldy.weiyuweather.Screens.HorizontalPagerFragment;
import com.example.ldy.weiyuweather.Screens.TablePagerFragment;

/**
 * Created by LDY on 2016/9/29.
 */
public class mainAdapter extends FragmentStatePagerAdapter {
    private final static int COUNT = 4;

    private final static int HORIZONTAL = 0;
    private final static int TABLES = 1;
    private final static int DETAIL_HORIZONTAL = 2;

    public mainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case DETAIL_HORIZONTAL:
                return new DetailPagerFragment();
            case TABLES:
                return new TablePagerFragment();
            case HORIZONTAL:
            default:
                return new HorizontalPagerFragment();
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
