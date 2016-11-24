package com.example.ldy.weiyuweather.Adapters;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.animation.Animation;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.view.LineChartView;
import com.example.ldy.weiyuweather.Gson.Weather;
import com.example.ldy.weiyuweather.R;
import com.example.ldy.weiyuweather.Utils.AddValueLinearChartView;
import com.example.ldy.weiyuweather.Utils.ImageLoadUtil;
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
    }

    private void initWeatherData() {
        if (mWeather.status == null)
            return;

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
        if (mWeather.aqi != null) {
            holder.airAqi.setText(mWeather.aqi.city.aqi);
            holder.airQlty.setText(String.format("空气%s", mWeather.aqi.city.qlty));
            holder.pm25.setText(mWeather.aqi.city.pm25);
        }
        holder.windDir.setText(mWeather.now.wind.dir);
        holder.windSc.setText(String.format("%s级", mWeather.now.wind.sc));
        holder.rainPop.setText(String.format("%s%%", mWeather.dailyForecast.get(0).pop));

        String info = mWeather.now.cond.txt;
        int iconRes = switchIcon(info);

        holder.nowTxt.setText(info);
        ImageLoadUtil.load(mContext, iconRes, holder.nowIcon);
    }

    private int switchIcon(String info) {
        switch (info) {
            case "晴":
                return R.mipmap.qingtian;
            case "阴":
            case "多云":
            case "少云":
                return R.mipmap.yingtian;
            case "晴间多云":
                return R.mipmap.qingyun;
            case "小雨":
            case "中雨":
                return R.mipmap.xiaoyu;
            case "雨夹雪":
            case "大雨":
            case "阵雨":
                return R.mipmap.dayu;
            case "雷阵雨":
                return R.mipmap.leiyu;
            case "霾":
            case "雾":
                return R.mipmap.mai;
            case "小雪":
            case "中雪":
            case "大雪":
                return R.mipmap.youxue;
            case "有风":
            case "微风":
            case "大风":
                return R.mipmap.youfeng;
            default:
                return R.mipmap.qingtian;
        }
    }

    private void bindChartViewHolder(chartViewHolder holder, int position) {
        holder.title.setText(mData[position].toString());

        switch (position) {
            case 1:
                holder.unit.setText("单位：℃");
                holder.cardView.setCardBackgroundColor(Color.parseColor("#384666"));
                showTmpChart((LineChartView) holder.mChart);
                break;
            case 2:
                holder.unit.setText("单位：Km/h");
                showWindChart((LineChartView) holder.mChart);
                break;
            case 3:
                holder.unit.setText("单位：%");
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
        Tooltip maxTmpTip = new Tooltip(mContext, R.layout.max_tmp_chart_tip, R.id.tip_tmp);
        maxTmpTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        maxTmpTip.setDimensions((int) Tools.fromDpToPx(58), (int) Tools.fromDpToPx(25));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            maxTmpTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

            maxTmpTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

            maxTmpTip.setPivotX(Tools.fromDpToPx(65) / 2);
            maxTmpTip.setPivotY(Tools.fromDpToPx(25));
        }
        chartView.setTooltips(maxTmpTip);

        Tooltip minTmpTip = new Tooltip(mContext, R.layout.min_tmp_chart_tip, R.id.tip_min_tmp);
        minTmpTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_BOTTOM);
        minTmpTip.setDimensions((int) Tools.fromDpToPx(58), (int) Tools.fromDpToPx(25));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            minTmpTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

            minTmpTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

            minTmpTip.setPivotX(Tools.fromDpToPx(65) / 2);
            minTmpTip.setPivotY(Tools.fromDpToPx(25));
        }
        chartView.setTooltips(minTmpTip);

        LineSet maxDataset = new LineSet(mWeekLabel, mMaxTmpValue);
        maxDataset.setColor(Color.parseColor("#04BDD5"))
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
        minDataset.setColor(Color.parseColor("#04BDD5"))
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

        int min = Utils.minValue(mMinTmpValue)[0]; int max = Utils.maxValue(mMaxTmpValue)[0];
        int minValue = Utils.minValue(mMinTmpValue)[1];
        int maxValue = Utils.maxValue(mMaxTmpValue)[1];
        chartView.setBorderSpacing(Tools.fromDpToPx(0))
                .setXLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.parseColor("#000000"))
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setLabelsColor(Color.WHITE)
                .setXAxis(false)
                .setYAxis(false)
                .setAxisBorderValues(minValue-5, maxValue+5);

        Runnable chartAction = () -> {
                maxTmpTip.prepare(chartView.getEntriesArea(0).get(max), mMaxTmpValue[max]);
                minTmpTip.prepare(chartView.getEntriesArea(1).get(min), mMinTmpValue[min]);
                chartView.showTooltip(maxTmpTip, true);
                chartView.showTooltip(minTmpTip, true);
        };

        chartView.show(new Animation().setEndAction(chartAction));
    }

    //风速km/h
    private void showWindChart(LineChartView chartView) {
        LineSet dataset = new LineSet(mWeekLabel, mWindValue);
        dataset.setColor(Color.parseColor("#FFFFFF"))
                .setSmooth(true)
                .setThickness(Tools.fromDpToPx(3))
                .setGradientFill(
                        new int[] {Color.parseColor("#04A79C"), Color.parseColor("#5E918A")}, null
                );
        chartView.addData(dataset);

        int maxWind = Utils.maxValue(mWindValue)[1];
        chartView.setBorderSpacing(1)
                .setAxisBorderValues(0, maxWind)
                .setAxisLabelsSpacing(Tools.fromDpToPx(5))
                .setXLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.parseColor("#000000"))
                .setYLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.WHITE)
                .setXAxis(false)
                .setYAxis(false)
                .setBorderSpacing(Tools.fromDpToPx(0));

        Animation animation = new Animation();
        chartView.show(animation);
    }

    //相对湿度%
    private void showHumChart(AddValueLinearChartView chartView) {
        LineSet dataset = new LineSet(mWeekLabel, mHumValue);
        dataset.setColor(Color.parseColor("#FFFFFF"))
                .setSmooth(true)
                .setThickness(Tools.fromDpToPx(3))
                .setGradientFill(
                        new int[] {Color.parseColor("#0DF7E9"), Color.parseColor("#07D6C7")}, null
                );
        chartView.addData(dataset);

        chartView.setBorderSpacing(1)
                .setAxisBorderValues(0, 100, 20)
                .setAxisLabelsSpacing(Tools.fromDpToPx(5))
                .setXLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.parseColor("#000000"))
                .setYLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.WHITE)
                .setXAxis(false)
                .setYAxis(false)
                .setBorderSpacing(Tools.fromDpToPx(0));



        Animation animation = new Animation();
        chartView.show(animation);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public static class chartViewHolder extends RecyclerView.ViewHolder{
        //图表布局
        TextView title;
        AddValueLinearChartView mChart;
        TextView unit;
        CardView cardView;

        public chartViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.table_title);
            mChart = (AddValueLinearChartView) itemView.findViewById(R.id.tmp_chart);
            unit = (TextView) itemView.findViewById(R.id.unit);
            cardView = (CardView) itemView.findViewById(R.id.table_item);
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

        ImageView nowIcon;
        TextView nowTxt;

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

            nowIcon = (ImageView) itemView.findViewById(R.id.now_weather_icon);
            nowTxt = (TextView) itemView.findViewById(R.id.now_weather_txt);
        }
    }

    public static class sunViewHolder extends RecyclerView.ViewHolder{
        //日出布局
        TextView sunTitle;
        SunRiseDownView sunView;

        public sunViewHolder(View itemView) {
            super(itemView);

            sunTitle = (TextView) itemView.findViewById(R.id.sun_title);
            sunView = (SunRiseDownView) itemView.findViewById(R.id.sun_view);
        }
    }
}
