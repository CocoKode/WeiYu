package com.example.ldy.weiyuweather.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telecom.CallScreeningService;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldy.weiyuweather.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by LDY on 2016/9/29.
 */
public class Utils {
    public static void setupItem(final View view, final LibraryObject libraryObject) {
        final TextView txt = (TextView) view.findViewById(R.id.txt_item);
        txt.setText(libraryObject.getTitle());

        final ImageView img = (ImageView) view.findViewById(R.id.img_item);
        img.setImageResource(libraryObject.getRes());
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
        private String mTitle;
        private int mRes;

        public LibraryObject(final int res, final String title) {
            mRes = res;
            mTitle = title;
        }

        public String getTitle() {
            return mTitle;
        }
        public int getRes() {
            return mRes;
        }
        public void setTitle(final String title) {
            mTitle = title;
        }
        public void setRes(final int res) {
            mRes = res;
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
}
