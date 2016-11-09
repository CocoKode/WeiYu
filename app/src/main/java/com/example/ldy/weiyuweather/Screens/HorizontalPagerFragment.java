package com.example.ldy.weiyuweather.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ldy.weiyuweather.Adapters.HorizontalPagerAdapter;
import com.example.ldy.weiyuweather.Base.ApiInterface;
import com.example.ldy.weiyuweather.Base.BaseApplication;
import com.example.ldy.weiyuweather.Base.SimpleSubscriber;
import com.example.ldy.weiyuweather.Base.refreshEvent;
import com.example.ldy.weiyuweather.Gson.Weather;
import com.example.ldy.weiyuweather.R;
import com.example.ldy.weiyuweather.Utils.RxBus;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by LDY on 2016/9/29.
 */

public class HorizontalPagerFragment extends Fragment{
    private Weather mWeather = new Weather();

    public static Fragment newInstance (Weather weather) {
        Log.d("HorizontalPagerFragment", "创建新fragment");
        if (weather.status == null) {
            Log.d("HorizontalPagerFragment", "newInstance中weather为空");
        } else {
            Log.d("HorizontalPagerFragment", "newInstance中weather不为空" + weather.basic.city);
        }
        HorizontalPagerFragment hPagerFragment = new HorizontalPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BaseApplication.getmAppContext().getResources().getString(R.string.BUNDLE_KEY), weather);
        hPagerFragment.setArguments(bundle);
        return hPagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("HorizontalPagerFragment", "onCreate");
        super.onCreate(savedInstanceState);

        if (mWeather.status == null){
            Log.d("HorizontalPagerFragment", "未接收");
        }else {
            Log.d("HorizontalPagerFragment", "接收" + mWeather.basic.city);
        }

        Bundle arg = getArguments();
        if (arg != null)
            mWeather = (Weather) arg.getSerializable(this.getResources().getString(R.string.BUNDLE_KEY));
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        Log.d("HorizontalPagerFragment", "创建视图");
        return inflater.inflate(R.layout.fragment_horizontal, container, false);
    }

    //在onCreateView执行完后执行onViewCreate
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("HorizontalPagerFragment", "视图创建后");
        if (getContext() == null){
            Log.d("onViewCreate", "content为空");
        }else {
            Log.d("onViewCreate", "content不为空"+getContext().toString());
        }
        HorizontalPagerAdapter mPagerAdapter = new HorizontalPagerAdapter(getContext(), mWeather);

        /*
        RxBus.getDefault().toObservable(refreshEvent.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new SimpleSubscriber<refreshEvent>() {
                            @Override
                            public void onNext(refreshEvent refreshEvent) {
                                Log.d("HorizontalPagerFragment", "刷新前");
                                mWeather = refreshEvent.getWeather();
                                mPagerAdapter.notifyDataSetChanged();
                                Log.d("HorizontalPagerFragment", "刷新后");
                            }
                        }
                );
        */
        final HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        horizontalInfiniteCycleViewPager.setAdapter(mPagerAdapter);
    }
}
