package com.example.ldy.weiyuweather.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.db.chart.Tools;
import com.db.chart.model.ChartSet;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.view.LineChartView;

import java.util.ArrayList;

/**
 * Created by LDY on 2016/11/22.
 */
public class AddValueLinearChartView extends LineChartView {
    private Paint textPaint = new Paint();

    public AddValueLinearChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        textPaint.setColor(Color.parseColor("#FFFFFF"));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAlpha(255);
        textPaint.setTextSize(Utils.getSp(context, 12));
        textPaint.setStrokeWidth((float) 1.0);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public AddValueLinearChartView(Context context) {
        super(context);

        textPaint.setColor(Color.parseColor("#FFFFFF"));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAlpha(255);
        textPaint.setTextSize(Utils.getSp(context, 12));
        textPaint.setStrokeWidth((float) 1.0);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void onDrawChart(Canvas canvas, ArrayList<ChartSet> data) {
        super.onDrawChart(canvas, data);

        drawTextOnChart(canvas, data);
    }

    //在图表上绘制文字
    private void drawTextOnChart(Canvas mCanvas, ArrayList<ChartSet> mDataSet) {
        //一个土办法……
        if (mDataSet == null || mCanvas == null || mDataSet.size() > 1) return;

        float px; float py; float pValue;
        LineSet ls;
        Point point;

        ls = (LineSet) mDataSet.get(0);
        if (ls.isVisible()) {
            int begin = ls.getBegin();
            int end = ls.getEnd();

            for (int i = begin; i < end; i++) {
                point = (Point) ls.getEntry(i);
                px = point.getX();
                py = point.getY();
                pValue = point.getValue();

                mCanvas.drawText(
                        String.valueOf((int) pValue),
                        px - Tools.fromDpToPx(1),
                        py - Tools.fromDpToPx(8),
                        textPaint
                );
            }

        }
    }
}
