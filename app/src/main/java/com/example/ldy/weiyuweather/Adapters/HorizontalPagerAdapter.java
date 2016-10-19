package com.example.ldy.weiyuweather.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ldy.weiyuweather.R;
import com.example.ldy.weiyuweather.Utils.SharedPreferenceUtil;
import com.example.ldy.weiyuweather.Utils.Utils;
import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;

import static com.example.ldy.weiyuweather.Utils.Utils.setupItem;

/**
 * Created by LDY on 2016/9/29.
 */
public class HorizontalPagerAdapter extends PagerAdapter{
    private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[] {
            new Utils.LibraryObject(
                    R.drawable.clound,
                    SharedPreferenceUtil.getInstance().getString("day1", "未知")
            ),
            new Utils.LibraryObject(
                    R.drawable.clound,
                    SharedPreferenceUtil.getInstance().getString("day2", "未知")
            ),
            new Utils.LibraryObject(
                    R.drawable.clound,
                    SharedPreferenceUtil.getInstance().getString("day3", "未知")
            ),
            new Utils.LibraryObject(
                    R.drawable.clound,
                    SharedPreferenceUtil.getInstance().getString("day4", "未知")
            ),
            new Utils.LibraryObject(
                    R.drawable.clound,
                    SharedPreferenceUtil.getInstance().getString("day5", "未知")
            ),
            new Utils.LibraryObject(
                    R.drawable.clound,
                    SharedPreferenceUtil.getInstance().getString("day6", "未知")
            ),
            new Utils.LibraryObject(
                    R.drawable.clound,
                    SharedPreferenceUtil.getInstance().getString("day7", "未知")
            )
    };

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private boolean mIsTwoWay;

    public HorizontalPagerAdapter(final Context context, final boolean isTwoWay) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mIsTwoWay = isTwoWay;
    }


    @Override
    public int getCount() {
        return mIsTwoWay ? 6 : LIBRARIES.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view;
        if (mIsTwoWay) {
            view = mLayoutInflater.inflate(R.layout.two_way_item, container, false);

            final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
                    (VerticalInfiniteCycleViewPager) view.findViewById(R.id.vicvp);
            verticalInfiniteCycleViewPager.setAdapter(new VerticalPagerAdapter(mContext));
            verticalInfiniteCycleViewPager.setCurrentItem(position);
        } else {
            view = mLayoutInflater.inflate(R.layout.item, container, false);
            setupItem(view, LIBRARIES[position]);
        }
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
}
