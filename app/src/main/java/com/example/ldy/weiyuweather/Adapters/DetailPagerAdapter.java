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

import static com.example.ldy.weiyuweather.Utils.Utils.setupDetailItem;

/**
 * Created by LDY on 2016/10/1.
 */
public class DetailPagerAdapter extends PagerAdapter {
    private Context mContext;

    private Weather mWeather = new Weather();

    private Utils.DetailLibraryObject[] DETAIL_LIBRARIES;

    private LayoutInflater mLayoutInflater;

    public DetailPagerAdapter(final Context context, final Weather weather) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mWeather = weather;
        initSuggestion();
    }

    private void initSuggestion() {
        if (mWeather.status == null) {
            Log.d("initSuggestion", "未接收");
            return;
        }

        DETAIL_LIBRARIES = new Utils.DetailLibraryObject[] {
                new Utils.DetailLibraryObject(
                        R.drawable.clothes,
                        R.mipmap.clothes_flat,
                        "穿衣指数",
                        mWeather.suggestion.drsg.txt
                ),
                new Utils.DetailLibraryObject(
                        R.drawable.uv,
                        R.mipmap.uv_flat,
                        "防晒指数",
                        mWeather.suggestion.uv.txt
                ),
                new Utils.DetailLibraryObject(
                        R.drawable.flu,
                        R.mipmap.pills_flat,
                        "感冒指数",
                        mWeather.suggestion.flu.txt
                ),
                new Utils.DetailLibraryObject(
                        R.drawable.car,
                        R.mipmap.car_flat,
                        "洗车指数",
                        mWeather.suggestion.cw.txt
                ),
                new Utils.DetailLibraryObject(
                        R.drawable.sport,
                        R.mipmap.sport_flat,
                        "运动指数",
                        mWeather.suggestion.sport.txt
                ),
                new Utils.DetailLibraryObject(
                        R.drawable.travel,
                        R.mipmap.travel_flat,
                        "旅游指数",
                        mWeather.suggestion.trav.txt
                )
        };
    }

    @Override
    public int getCount() {
        return mWeather.status == null ? 0 : DETAIL_LIBRARIES.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view;
        view = mLayoutInflater.inflate(R.layout.detail_item, container, false);
        setupDetailItem(view, DETAIL_LIBRARIES[position], mContext);
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
