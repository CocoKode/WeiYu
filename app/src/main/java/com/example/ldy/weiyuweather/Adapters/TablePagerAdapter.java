package com.example.ldy.weiyuweather.Adapters;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
import com.example.ldy.weiyuweather.Gson.Weather;
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

    private Context mContext;
    private String[] mData;
    private static Paint gridPaint = new Paint();
    private Weather mWeather = new Weather();

    private String[] mNoLabel = {"", "", "", "", "", "", ""};
    private String[] mWeekLabel;
    private float[] mMaxTmpValue;
    private float[] mMinTmpValue;
    private float[] mWindValue;
    private float[] mHumValue;

    public TablePagerAdapter(String[] data, Weather weather, Context context) {
        mData = data;
        mWeather = weather;
        mContext = context;
        initWeatherData();

        gridPaint.setColor(Color.parseColor("#F7B151"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(1));
        gridPaint.setPathEffect(new DashPathEffect(new float[]{20, 20}, 10));
    }

    private void initWeatherData() {
        if (mWeather.status == null) {
            Log.d("initWeatherData", "mweather为空");
            return;
        }else {
            Log.d("initWeatherData", "mweather不为空" + mWeather.basic.city);
            Log.d("initweatherdata", "daily size =" + mWeather.dailyForecast.size());
        }

        mWeekLabel = new String[]{
                "今日",
                "明日",
                Utils.dayForWeek(mWeather.dailyForecast.get(2).date),
                Utils.dayForWeek(mWeather.dailyForecast.get(3).date),
                Utils.dayForWeek(mWeather.dailyForecast.get(4).date),
                Utils.dayForWeek(mWeather.dailyForecast.get(5).date),
                Utils.dayForWeek(mWeather.dailyForecast.get(6).date)
        };
        mMaxTmpValue = new float[]{
                Float.parseFloat(mWeather.dailyForecast.get(0).tmp.max),
                Float.parseFloat(mWeather.dailyForecast.get(1).tmp.max),
                Float.parseFloat(mWeather.dailyForecast.get(2).tmp.max),
                Float.parseFloat(mWeather.dailyForecast.get(3).tmp.max),
                Float.parseFloat(mWeather.dailyForecast.get(4).tmp.max),
                Float.parseFloat(mWeather.dailyForecast.get(5).tmp.max),
                Float.parseFloat(mWeather.dailyForecast.get(6).tmp.max),
        };
        mMinTmpValue = new float[]{
                Float.parseFloat(mWeather.dailyForecast.get(0).tmp.min),
                Float.parseFloat(mWeather.dailyForecast.get(1).tmp.min),
                Float.parseFloat(mWeather.dailyForecast.get(2).tmp.min),
                Float.parseFloat(mWeather.dailyForecast.get(3).tmp.min),
                Float.parseFloat(mWeather.dailyForecast.get(4).tmp.min),
                Float.parseFloat(mWeather.dailyForecast.get(5).tmp.min),
                Float.parseFloat(mWeather.dailyForecast.get(6).tmp.min),
        };
        mWindValue = new float[]{
                Float.parseFloat(mWeather.dailyForecast.get(0).wind.spd),
                Float.parseFloat(mWeather.dailyForecast.get(1).wind.spd),
                Float.parseFloat(mWeather.dailyForecast.get(2).wind.spd),
                Float.parseFloat(mWeather.dailyForecast.get(3).wind.spd),
                Float.parseFloat(mWeather.dailyForecast.get(4).wind.spd),
                Float.parseFloat(mWeather.dailyForecast.get(5).wind.spd),
                Float.parseFloat(mWeather.dailyForecast.get(6).wind.spd),
        };
        mHumValue = new float[]{
                Float.parseFloat(mWeather.dailyForecast.get(0).hum),
                Float.parseFloat(mWeather.dailyForecast.get(1).hum),
                Float.parseFloat(mWeather.dailyForecast.get(2).hum),
                Float.parseFloat(mWeather.dailyForecast.get(3).hum),
                Float.parseFloat(mWeather.dailyForecast.get(4).hum),
                Float.parseFloat(mWeather.dailyForecast.get(5).hum),
                Float.parseFloat(mWeather.dailyForecast.get(6).hum),
        };
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
        if (mWeather.status == null) return;
        if (holder instanceof nowWeatherViewHolder)
            bindNowWeatherViewHolder((nowWeatherViewHolder)holder, position);
        else if (holder instanceof  chartViewHolder)
            bindChartViewHolder((chartViewHolder)holder, position);
        else
            bindSunViewHolder((sunViewHolder)holder, position);
    }

    private void bindNowWeatherViewHolder(nowWeatherViewHolder holder, int position) {
        holder.nowTitle.setText(mData[position].toString());
        holder.nowTmp.setText(String.format("%s℃", mWeather.now.tmp));
        holder.airAqi.setText(mWeather.aqi.city.aqi);
        holder.airQlty.setText(String.format("空气%s", mWeather.aqi.city.qlty));
        holder.pm25.setText(mWeather.aqi.city.pm25);
        holder.windDir.setText(mWeather.now.wind.dir);
        holder.windSc.setText(String.format("%s级", mWeather.now.wind.sc));
        holder.rainPop.setText(String.format("%s%%", mWeather.dailyForecast.get(0).pop));
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
                mWeather.dailyForecast.get(0).astro.sr,
                mWeather.dailyForecast.get(0).astro.ss
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

        //chartView.setTooltips(mTip);

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
                .setLabelsColor(Color.parseColor("#000000"))
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setXAxis(false)
                .setYAxis(false)
                .setAxisBorderValues(min-5, max+5);

        //mTip.
        //chartView.showTooltip(mTip, true);

        chartView.show();
    }

    private void showWindChart(LineChartView chartView) {
        LineSet dataset = new LineSet(mWeekLabel, mWindValue);
        dataset.setColor(Color.parseColor("#C97ED7"))
                .setSmooth(true)
                .setThickness(Tools.fromDpToPx(0))
                .setFill(Color.parseColor("#D1D0D2"))
                .setGradientFill(
                        new int[] {Color.parseColor("#C97ED7"), Color.parseColor("#D1D0D2")}, null
                );
        chartView.addData(dataset);

        int maxWind = Utils.maxTmp(mWindValue);
        chartView.setBorderSpacing(1)
                .setAxisBorderValues(0, maxWind)
                .setAxisLabelsSpacing(Tools.fromDpToPx(5))
                .setXLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.parseColor("#000000"))
                .setYLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setXAxis(false)
                .setYAxis(false)
                .setBorderSpacing(Tools.fromDpToPx(0));

        //chartView.setGrid(ChartView.GridType.HORIZONTAL, gridPaint);

        chartView.show();
    }

    private void showHumChart(LineChartView chartView) {
        LineSet dataset = new LineSet(mWeekLabel, mHumValue);
        dataset.setColor(Color.parseColor("#4A38B4"))
                .setSmooth(true)
                .setThickness(0)
                .setFill(Color.parseColor("#D1D0D2"))
                .setGradientFill(
                        new int[] {Color.parseColor("#4A38B4"), Color.parseColor("#9966CA")}, null
                );
        chartView.addData(dataset);

        int maxHum = Utils.maxTmp(mHumValue);
        chartView.setBorderSpacing(1)
                .setAxisBorderValues(0, maxHum)
                .setAxisLabelsSpacing(Tools.fromDpToPx(5))
                .setXLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.parseColor("#000000"))
                .setYLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setXAxis(false)
                .setYAxis(false)
                .setBorderSpacing(Tools.fromDpToPx(0));

        //chartView.setGrid(ChartView.GridType.HORIZONTAL, gridPaint);

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
