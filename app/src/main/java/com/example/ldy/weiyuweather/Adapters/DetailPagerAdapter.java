package com.example.ldy.weiyuweather.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ldy.weiyuweather.R;
import com.example.ldy.weiyuweather.Utils.Utils;

import static com.example.ldy.weiyuweather.Utils.Utils.setupDetailItem;

/**
 * Created by LDY on 2016/10/1.
 */
public class DetailPagerAdapter extends PagerAdapter {
    private final Utils.LibraryObject[] DETAIL_LIBRARIES = new Utils.LibraryObject[] {
            new Utils.LibraryObject(
                    R.drawable.clothes,
                    "穿衣指数"
            ),
            new Utils.LibraryObject(
                    R.drawable.uv,
                    "紫外线强度"
            ),
            new Utils.LibraryObject(
                    R.drawable.flu,
                    "感冒指数"
            ),
            new Utils.LibraryObject(
                    R.drawable.car,
                    "洗车指数"
            ),
            new Utils.LibraryObject(
                    R.drawable.sport,
                    "运动指数"
            ),
            new Utils.LibraryObject(
                    R.drawable.travel,
                    "旅游指数"
            )
    };

    private LayoutInflater mLayoutInflater;

    public DetailPagerAdapter(final Context context, final boolean isTwoWay) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return DETAIL_LIBRARIES.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view;
        view = mLayoutInflater.inflate(R.layout.detail_item, container, false);
        setupDetailItem(view, DETAIL_LIBRARIES[position]);
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
