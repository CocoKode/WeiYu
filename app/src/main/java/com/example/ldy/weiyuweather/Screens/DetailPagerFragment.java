package com.example.ldy.weiyuweather.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ldy.weiyuweather.Adapters.DetailPagerAdapter;
import com.example.ldy.weiyuweather.Adapters.HorizontalPagerAdapter;
import com.example.ldy.weiyuweather.Base.BaseApplication;
import com.example.ldy.weiyuweather.Gson.Weather;
import com.example.ldy.weiyuweather.R;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

/**
 * Created by LDY on 2016/10/1.
 */
public class DetailPagerFragment extends Fragment {
    private Weather mWeather;
    private TextView mDetailTmp;

    public static Fragment newInstance (Weather weather) {
        Log.d("DetailPagerFragment", "创建新fragment");
        if (weather.status == null) {
            Log.d("DetailPagerFragment", "newInstance中weather为空");
        } else {
            Log.d("DetailPagerFragment", "newInstance中weather不为空" + weather.basic.city);
        }
        DetailPagerFragment dPagerFragment = new DetailPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BaseApplication.getmAppContext().getResources().getString(R.string.BUNDLE_KEY), weather);
        dPagerFragment.setArguments(bundle);
        return dPagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("DetailPagerFragment", "onCreate");
        super.onCreate(savedInstanceState);

        Bundle arg = getArguments();
        if (arg != null)
            mWeather = (Weather) arg.getSerializable(this.getResources().getString(R.string.BUNDLE_KEY));

        if (mWeather.status == null){
            Log.d("DetailPagerFragment", "未接收");
        }else {
            Log.d("DetailPagerFragment", "接收" + mWeather.basic.city);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_horizontal, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        horizontalInfiniteCycleViewPager.setAdapter(new DetailPagerAdapter(getContext(), mWeather));

        mDetailTmp =(TextView) view.findViewById(R.id.detail_tmp);
        if (mWeather.status != null)
            mDetailTmp.setText(String.format("%s°", mWeather.now.tmp));
    }
}
