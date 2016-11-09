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
        Log.d("HorizontalPagerAdapter", "构造");
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mWeather = weather;
        initLibraryObject(mWeather);
    }

    private void initLibraryObject(Weather mWeather) {
        if (mWeather.status == null) {
            Log.d("initLibraryObject", "mweather为空");
            return;
        }else {
            Log.d("initLibraryObject", "mweather不为空" + mWeather.basic.city);
            Log.d("initLibraryObject", "daily size =" + mWeather.dailyForecast.size());
        }

        LIBRARIES = new Utils.LibraryObject[] {
                new Utils.LibraryObject(
                        R.drawable.midnightinthevalley2,
                        "今日",
                        mWeather.dailyForecast.get(0).cond.txtD,
                        mWeather.now.tmp
                ),
                new Utils.LibraryObject(
                        R.drawable.midnightinthevalley2,
                        "明日",
                        mWeather.dailyForecast.get(1).cond.txtD,
                        mWeather.dailyForecast.get(1).tmp.max
                ),
                new Utils.LibraryObject(
                        R.drawable.midnightinthevalley2,
                        Utils.dayForWeek(mWeather.dailyForecast.get(2).date),
                        mWeather.dailyForecast.get(2).cond.txtD,
                        mWeather.dailyForecast.get(2).tmp.max
                ),
                new Utils.LibraryObject(
                        R.drawable.midnightinthevalley2,
                        Utils.dayForWeek(mWeather.dailyForecast.get(3).date),
                        mWeather.dailyForecast.get(3).cond.txtD,
                        mWeather.dailyForecast.get(3).tmp.max
                ),
                new Utils.LibraryObject(
                        R.drawable.midnightinthevalley2,
                        Utils.dayForWeek(mWeather.dailyForecast.get(4).date),
                        mWeather.dailyForecast.get(4).cond.txtD,
                        mWeather.dailyForecast.get(4).tmp.max
                ),
                new Utils.LibraryObject(
                        R.drawable.midnightinthevalley2,
                        Utils.dayForWeek(mWeather.dailyForecast.get(5).date),
                        mWeather.dailyForecast.get(5).cond.txtD,
                        mWeather.dailyForecast.get(5).tmp.max
                ),
                new Utils.LibraryObject(
                        R.drawable.midnightinthevalley2,
                        Utils.dayForWeek(mWeather.dailyForecast.get(6).date),
                        mWeather.dailyForecast.get(6).cond.txtD,
                        mWeather.dailyForecast.get(6).tmp.max
                )
        };
    }


    @Override
    public int getCount() {
        Log.d("HorizontalPagerPager", "getcount");
        return mWeather.status == null ? 0 : LIBRARIES.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d("HorizontalPagerAdapter", "instantiateItem");
        final View view;
        /*
        if (mIsTwoWay) {
            view = mLayoutInflater.inflate(R.layout.two_way_item, container, false);

            final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
                    (VerticalInfiniteCycleViewPager) view.findViewById(R.id.vicvp);
            verticalInfiniteCycleViewPager.setAdapter(new VerticalPagerAdapter(mContext));
            verticalInfiniteCycleViewPager.setCurrentItem(position);
        }
        */
        view = mLayoutInflater.inflate(R.layout.item, container, false);
        setupItem(view, LIBRARIES[position], mContext);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d("HorizontalPagerPager", "destoryItem");
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        Log.d("HorizontalPagerPager", "isViewFromObject");
        return view.equals(object);
    }

    @Override
    public int getItemPosition(Object object) {
        Log.d("HorizontalPagerPager", "isViewFromObject");
        return POSITION_NONE;
    }
}
