package com.example.ldy.weiyuweather.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ldy.weiyuweather.Adapters.HorizontalPagerAdapter;
import com.example.ldy.weiyuweather.Base.BaseApplication;
import com.example.ldy.weiyuweather.Gson.Weather;
import com.example.ldy.weiyuweather.R;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

/**
 * Created by LDY on 2016/9/29.
 */

public class HorizontalPagerFragment extends Fragment{
    private Weather mWeather = new Weather();

    public static Fragment newInstance (Weather weather) {
        HorizontalPagerFragment hPagerFragment = new HorizontalPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BaseApplication.getmAppContext().getResources().getString(R.string.BUNDLE_KEY), weather);
        hPagerFragment.setArguments(bundle);
        return hPagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arg = getArguments();
        if (arg != null)
            mWeather = (Weather) arg.getSerializable(this.getResources().getString(R.string.BUNDLE_KEY));
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_horizontal, container, false);
    }

    //在onCreateView执行完后执行onViewCreate
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HorizontalPagerAdapter mPagerAdapter = new HorizontalPagerAdapter(getContext(), mWeather);
        final HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        horizontalInfiniteCycleViewPager.setAdapter(mPagerAdapter);
    }
}
