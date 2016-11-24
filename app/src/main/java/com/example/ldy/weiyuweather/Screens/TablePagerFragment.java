package com.example.ldy.weiyuweather.Screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ldy.weiyuweather.Adapters.TablePagerAdapter;
import com.example.ldy.weiyuweather.Base.BaseApplication;
import com.example.ldy.weiyuweather.Gson.Weather;
import com.example.ldy.weiyuweather.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.orm.dsl.Table;

/**
 * Created by LDY on 2016/10/3.
 */
public class TablePagerFragment extends Fragment {
    private Weather mWeather;

    public static Fragment newInstance (Weather weather) {
        TablePagerFragment tPagerFragment = new TablePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BaseApplication.getmAppContext().getResources().getString(R.string.BUNDLE_KEY), weather);
        tPagerFragment.setArguments(bundle);
        return tPagerFragment;
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
    public View onCreateView(final LayoutInflater inflater, final @Nullable ViewGroup container, final @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_table, container, false);
    }

    @Override
    public void onViewCreated(final View view, final @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] data = new String[] {
            "当前天气",
            "一周天气",
            "风力",
            "湿度",
            "日出日落"
        };
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new TablePagerAdapter(data, mWeather, getContext()));
    }
}
