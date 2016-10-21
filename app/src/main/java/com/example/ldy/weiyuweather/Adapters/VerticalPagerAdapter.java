package com.example.ldy.weiyuweather.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ldy.weiyuweather.R;
import com.example.ldy.weiyuweather.Utils.Utils;

import static com.example.ldy.weiyuweather.Utils.Utils.setupItem;

/**
 * Created by LDY on 2016/9/30.
 */
public class VerticalPagerAdapter extends PagerAdapter {
    private final Utils.LibraryObject[] TWO_WAY_LIBRARIES = new Utils.LibraryObject[] {
            new Utils.LibraryObject(
                    R.drawable.clound,
                    "待办事项",
                    "未知",
                    "未知"
            )
    };

    private LayoutInflater mLayoutInflater;

    public VerticalPagerAdapter(final Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return TWO_WAY_LIBRARIES.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view = mLayoutInflater.inflate(R.layout.item, container);

        //setupItem(view, TWO_WAY_LIBRARIES[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals( object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
