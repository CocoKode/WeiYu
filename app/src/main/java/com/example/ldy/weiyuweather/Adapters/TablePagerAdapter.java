package com.example.ldy.weiyuweather.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.LineChartView;
import com.example.ldy.weiyuweather.R;
import com.example.ldy.weiyuweather.Utils.SharedPreferenceUtil;
import com.example.ldy.weiyuweather.Utils.SunRiseDownView;
import com.example.ldy.weiyuweather.Utils.Utils;

/**
 * Created by LDY on 2016/10/4.
 */
public class TablePagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NOW_WEATHER_ITEM = 0;
    private static final int CHART_ITEM = 1;
    private static final int SUN_VIEW = 2;

    private String[] mData;

    private String[] mNoLabel = {"", "", "", "", "", "", ""};
    private String[] mWeekLabel = {
            SharedPreferenceUtil.getInstance().getString("day1", "未知"),
            SharedPreferenceUtil.getInstance().getString("day2", "未知"),
            SharedPreferenceUtil.getInstance().getString("day3", "未知"),
            SharedPreferenceUtil.getInstance().getString("day4", "未知"),
            SharedPreferenceUtil.getInstance().getString("day5", "未知"),
            SharedPreferenceUtil.getInstance().getString("day6", "未知"),
            SharedPreferenceUtil.getInstance().getString("day7", "未知")
    };
    private float[] mMaxTmpValue = {
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day1MaxTmp", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day2MaxTmp", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day3MaxTmp", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day4MaxTmp", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day5MaxTmp", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day6MaxTmp", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day7MaxTmp", "0"))
    };
    private float[] mMinTmpValue = {
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day1MinTmp", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day2MinTmp", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day3MinTmp", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day4MinTmp", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day5MinTmp", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day6MinTmp", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day7MinTmp", "0")),
    };
    private float[] mWindValue = {
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day1Wind", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day2Wind", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day3Wind", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day4Wind", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day5Wind", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day6Wind", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day7Wind", "0"))
    };
    private float[] mHumValue = {
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day1Hum", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day2Hum", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day3Hum", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day4Hum", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day5Hum", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day6Hum", "0")),
            Float.parseFloat(SharedPreferenceUtil.getInstance().getString("day7Hum", "0"))
    };
    public TablePagerAdapter(String[] data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NOW_WEATHER_ITEM)
            return new nowWeatherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.now_weather_item, parent, false));
        else if (viewType == CHART_ITEM)
            return new chartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item, parent, false));
        else
            return new sunViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sunitem, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof nowWeatherViewHolder)
            bindNowWeatherViewHolder((nowWeatherViewHolder)holder, position);
        else if (holder instanceof  chartViewHolder)
            bindChartViewHolder((chartViewHolder)holder, position);
        else
            bindSunViewHolder((sunViewHolder)holder, position);
    }

    private void bindNowWeatherViewHolder(nowWeatherViewHolder holder, int position) {
        holder.nowTitle.setText(mData[position].toString());
        holder.nowTmp.setText(SharedPreferenceUtil.getInstance().getString("NowTmp", "未知"));
        holder.airAqi.setText(SharedPreferenceUtil.getInstance().getString("aqi", "未知"));
        holder.airQlty.setText(SharedPreferenceUtil.getInstance().getString("qlty", "未知"));
        holder.pm25.setText(SharedPreferenceUtil.getInstance().getString("pm25", "未知"));
        holder.windDir.setText(SharedPreferenceUtil.getInstance().getString("WindDir", "未知"));
        holder.windSc.setText(SharedPreferenceUtil.getInstance().getString("WindSc", "未知"));
        holder.rainPop.setText(SharedPreferenceUtil.getInstance().getString("RainPop", "未知"));
    }
    private void bindChartViewHolder(chartViewHolder holder, int position) {
        holder.title.setText(mData[position].toString());

        switch (position) {
            case 1:
                showTmpChart(holder.mChart);
                break;
            case 2:
                showWindChart(holder.mChart);
                break;
            case 3:
                showHumChart(holder.mChart);
                break;
            default:
                break;
        }
    }
    private void bindSunViewHolder(sunViewHolder holder, int position) {
        holder.sunTitle.setText(mData[position].toString());
        holder.sunView.setSunRiseDownTime(
                SharedPreferenceUtil.getInstance().getString("sunRise", "05:00"),
                SharedPreferenceUtil.getInstance().getString("sunSet", "18:46")
        );
    }

    //根据位置判断相应的ViewHolder
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return NOW_WEATHER_ITEM;
        else if (position == (mData.length - 1))
            return SUN_VIEW;
        else
            return CHART_ITEM;
    }

    private void showTmpChart(LineChartView chartView) {
        LineSet maxDataset = new LineSet(mWeekLabel, mMaxTmpValue);
        maxDataset.setColor(Color.parseColor("#004f7f"))
                .setThickness(Tools.fromDpToPx(3))
                .setSmooth(true)
                .beginAt(0)
                .endAt(7);

        for (int i = 0; i < mWeekLabel.length; i++) {
            Point point = (Point) maxDataset.getEntry(i);
            point.setColor(Color.parseColor("#ffffff"));
            point.setStrokeColor(Color.parseColor("#0290c3"));
        }
        chartView.addData(maxDataset);

        LineSet minDataset = new LineSet(mNoLabel, mMinTmpValue);
        minDataset.setColor(Color.parseColor("#004f7f"))
                .setThickness(Tools.fromDpToPx(3))
                .setSmooth(true)
                .beginAt(0)
                .endAt(7);

        for (int i = 0; i < mWeekLabel.length; i++) {
            Point point = (Point) minDataset.getEntry(i);
            point.setColor(Color.parseColor("#ffffff"));
            point.setStrokeColor(Color.parseColor("#0290c3"));
        }
        chartView.addData(minDataset);

        int min = Utils.minTmp(mMinTmpValue);
        int max = Utils.maxTmp(mMaxTmpValue);
        chartView.setBorderSpacing(Tools.fromDpToPx(0))
                .setXLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.parseColor("#ffffff"))
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setXAxis(false)
                .setYAxis(false)
                .setAxisBorderValues(min-5, max+5);
        chartView.show();
    }

    private void showWindChart(LineChartView chartView) {
        LineSet dataset = new LineSet(mNoLabel, mWindValue);
        dataset.setColor(Color.parseColor("#53c1bd"))
                .setSmooth(true)
                .setFill(Color.parseColor("#3d6c73"))
                .setGradientFill(
                        new int[] {Color.parseColor("#364d5a"), Color.parseColor("#3f7178")}, null
                );
        chartView.addData(dataset);

        chartView.setBorderSpacing(1)
                .setXLabels(AxisRenderer.LabelPosition.NONE)
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setXAxis(false)
                .setYAxis(false)
                .setBorderSpacing(Tools.fromDpToPx(0));

        chartView.show();
    }

    private void showHumChart(LineChartView chartView) {
        LineSet dataset = new LineSet(mNoLabel, mHumValue);
        dataset.setColor(Color.parseColor("#53c1bd"))
                .setSmooth(true)
                .setFill(Color.parseColor("#3d6c73"))
                .setGradientFill(
                        new int[] {Color.parseColor("#364d5a"), Color.parseColor("#3f7178")}, null
                );
        chartView.addData(dataset);

        chartView.setBorderSpacing(1)
                .setXLabels(AxisRenderer.LabelPosition.NONE)
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setXAxis(false)
                .setYAxis(false)
                .setBorderSpacing(Tools.fromDpToPx(0));

        chartView.show();
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public static class chartViewHolder extends RecyclerView.ViewHolder{
        //图表布局
        TextView title;
        LineChartView mChart;

        public chartViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.table_title);
            mChart = (LineChartView) itemView.findViewById(R.id.tmp_chart);
        }
    }

    private class nowWeatherViewHolder extends RecyclerView.ViewHolder {
        //当前天气布局
        TextView nowTitle;
        TextView nowTmp;
        TextView airAqi;
        TextView airQlty;
        TextView windDir;
        TextView windSc;
        TextView pm25;
        TextView rainPop;

        public nowWeatherViewHolder(View itemView) {
            super(itemView);

            nowTitle = (TextView) itemView.findViewById(R.id.now_title);
            nowTmp = (TextView) itemView.findViewById(R.id.now_weather_tmp);
            airAqi = (TextView) itemView.findViewById(R.id.air_aqi);
            airQlty = (TextView) itemView.findViewById(R.id.air_qlty);
            windDir = (TextView) itemView.findViewById(R.id.wind_dir);
            windSc = (TextView) itemView.findViewById(R.id.wind_sc);
            pm25 = (TextView) itemView.findViewById(R.id.pm25);
            rainPop = (TextView) itemView.findViewById(R.id.rain_pop);
        }
    }

    public static class sunViewHolder extends RecyclerView.ViewHolder{
        //图表布局
        TextView sunTitle;
        SunRiseDownView sunView;

        public sunViewHolder(View itemView) {
            super(itemView);

            sunTitle = (TextView) itemView.findViewById(R.id.sun_title);
            sunView = (SunRiseDownView) itemView.findViewById(R.id.sun_view);
        }
    }
}
