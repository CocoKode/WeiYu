package com.example.ldy.weiyuweather.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telecom.CallScreeningService;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ldy.weiyuweather.R;
import com.thbs.skycons.library.CloudRainView;
import com.thbs.skycons.library.SkyconView;
import com.thbs.skycons.library.SunView;
import com.thbs.skycons.library.WindView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by LDY on 2016/9/29.
 */
public class Utils {
    public static void setupItem(final View view, final LibraryObject libraryObject, Context context) {
        final TextView tmp = (TextView) view.findViewById(R.id.tmp_item);
        tmp.setText(libraryObject.getTmp() + "°");

        final TextView info = (TextView) view.findViewById(R.id.weather_item);
        String weather = libraryObject.getmInfo();
        info.setText(weather);

        final TextView date = (TextView) view.findViewById(R.id.txt_item);
        date.setText(libraryObject.getDate());

        final ImageView img = (ImageView) view.findViewById(R.id.img_item);
        img.setImageResource(libraryObject.getRes());

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.card_relativeLayout);
        SkyconView icon = null;
        switch (weather) {
            case "晴":
                icon = new SunView(context);
                break;
            default:
                icon = new CloudRainView(context);
                break;
        }
        int len = dip2px(context, 150); int distence = dip2px(context, 200);
        RelativeLayout.LayoutParams relLayoutParams = new RelativeLayout.LayoutParams(len, len);
        relLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        relLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        relLayoutParams.topMargin = distence;
        icon.setLayoutParams(relLayoutParams);
        relativeLayout.addView(icon);
    }

    //天气情况
    public static void setupDetailItem(final View view, final DetailLibraryObject detailLibraryObject) {
        final TextView txt = (TextView) view.findViewById(R.id.detail_txt);
        txt.setText(detailLibraryObject.getTitle());

        final TextView content = (TextView) view.findViewById(R.id.detail_content);
        content.setText(detailLibraryObject.getContent());

        final ImageView img = (ImageView) view.findViewById(R.id.detail_img);
        img.setImageResource(detailLibraryObject.getRes());
    }
    public static class LibraryObject {
        private String mDate;
        private int mRes;
        private String mInfo;
        private String mTmp;

        public LibraryObject(final int res, final String date, final String info, String tmp) {
            mRes = res;
            mDate = date;
            mInfo = info;
            mTmp = tmp;
        }

        public String getDate() {
            return mDate;
        }
        public int getRes() {
            return mRes;
        }
        public void setDate(final String date) {
            mDate = date;
        }
        public void setRes(final int res) {
            mRes = res;
        }
        public void setmInfo(final String info) {
            mInfo = info;
        }
        public String getmInfo() {
            return mInfo;
        }
        public void setTmp(final String tmp) {
            mTmp = tmp;
        }
        public String getTmp() {
            return mTmp;
        }
    }
    //详细情况
    public static class DetailLibraryObject {
        private String mTitle;
        private int mRes;
        private String mContent;

        public DetailLibraryObject(final int res, final String title, final String content) {
            mRes = res;
            mTitle = title;
            mContent = content;
        }

        public String getTitle() {
            return mTitle;
        }
        public int getRes() {
            return mRes;
        }
        public String getContent() {
            return mContent;
        }

        public void setTitle(final String title) {
            mTitle = title;
        }
        public void setRes(final int res) {
            mRes = res;
        }
        public void setContent(final String content) {
            mContent = content;
        }
    }


    //判断是否有网络
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (networkInfo != null)
                return networkInfo.isAvailable();

        }
        return false;
    }

    //判断日期
    public static String dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        String week = "";
        dayForWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayForWeek) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    //转换单位
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
