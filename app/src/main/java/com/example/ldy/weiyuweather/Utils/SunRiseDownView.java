package com.example.ldy.weiyuweather.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.ldy.weiyuweather.R;

import java.util.Calendar;

/**
 * Created by LDY on 2016/10/26.
 */
public class SunRiseDownView extends View{
    private int width, height;
    private float progress = 0.0f;
    private Paint paint = new Paint();
    private RectF rectF;
    private DashPathEffect dashPathEffect;
    private Path path = new Path();
    private Context mContext;

    private int center;
    private int radius;

    private String sunRiseTime;
    private String sunDownTime;

    public SunRiseDownView(Context context) {
        super(context);
        mContext = context;
    }

    public SunRiseDownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public SunRiseDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = Utils.getScreenWidth(mContext);
        height = getFitSize(550);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

         center = width / 2;
         radius = height - getFitSize(120);
        //左上右下
        rectF = new RectF(
                center - radius,
                center - radius,
                center + radius,
                center + radius
        );

        dashPathEffect = new DashPathEffect(new float[]{25, 25}, 5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //无锯齿
        paint.setAntiAlias(true);
        //画笔颜色
        paint.setColor(getResources().getColor(R.color.dash_paint_color));
        //线条宽度
        paint.setStrokeWidth((float) 5.0);
        //设置为空心
        paint.setStyle(Paint.Style.STROKE);
        //设置透明度
        paint.setAlpha(160);

        canvas.translate(0, (height - radius - (center - radius)));
        //绘制轨迹
        int angel = 3;

        paint.setPathEffect(dashPathEffect);
        canvas.drawArc(rectF, 180, 180, false, paint);
        paint.setPathEffect(null);

        //绘制太阳
        paint.setStrokeWidth((float) 4.0);
        paint.setAlpha(255);
        Double pointX, pointY;
        angel = 90 - (int) (180 * progress);
        if (angel < 0) {
            angel = 180 - (int) (180 * progress);
            pointX = center + radius * Math.cos((angel / 180f) * Math.PI);
            pointY = height - radius * Math.sin((angel / 180f) * Math.PI) - (height - radius - (center - radius));
        } else {
            pointX = center - radius * Math.sin((angel / 180f) * Math.PI);
            pointY = height - radius * Math.cos((angel / 180f) * Math.PI) - (height - radius - (center - radius));
        }
        canvas.drawCircle(pointX.floatValue(), pointY.floatValue(), getFitSize(30), paint);

        //绘制阴影
        path.arcTo(rectF, 180, 180 * progress);
        path.lineTo(pointX.floatValue(), pointY.floatValue() + radius);
        paint.setAlpha(60);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);

        //绘制文字
        paint.setColor(getResources().getColor(R.color.txt_paint_color));
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(255);
        paint.setTextSize(Utils.getSp(mContext, 12));
        paint.setStrokeWidth((float) 1.0);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(
                String.format(getResources().getString(R.string.sunrise_txt), sunRiseTime),
                center - radius + getFitSize(125),
                height - (height - radius - (center - radius)) - getFitSize(15),
                paint
        );
        canvas.drawText(
                String.format(getResources().getString(R.string.sunset_txt), sunDownTime),
                center + radius - getFitSize(125),
                height - (height - radius - (center - radius)) - getFitSize(15),
                paint
        );
    }

    public void setSunRiseDownTime(String sunRise, String sunDown) {
        sunRiseTime = sunRise;
        sunDownTime = sunDown;

        int sunRiseHour = Integer.parseInt(sunRiseTime.split(":")[0]);
        int sunRiseMinute = Integer.parseInt(sunRiseTime.split(":")[1]);
        int sunDownHour = Integer.parseInt(sunDownTime.split(":")[0]);
        int sunDownMinute = Integer.parseInt(sunDownTime.split(":")[1]);
        Calendar calendar = Calendar.getInstance();
        //获取当前时间
        long now = calendar.getTimeInMillis();
        //重置时间为日出时间
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, sunRiseHour);
        calendar.set(Calendar.MINUTE, sunRiseMinute);
        //获取日出时间
        long start = calendar.getTimeInMillis();
        //重置为日落时间
        calendar.set(Calendar.HOUR_OF_DAY, sunDownHour);
        calendar.set(Calendar.MINUTE, sunDownMinute);
        //获取日落时间
        long end = calendar.getTimeInMillis();

        if (now > end)
            progress = 1;
        else if (now < start)
            progress = 0;
        else
            progress = (now - start) * 1.00f / (end - start);

        this.invalidate();
    }

    private int getFitSize(int orgSize) {
        return orgSize * width / 1080;
    }
}
