package com.example.ldy.weiyuweather.Utils;

import android.widget.Toast;

import com.example.ldy.weiyuweather.Base.BaseApplication;

/**
 * Created by LDY on 2016/10/15.
 * 用于显示toast
 */
public class ToastUtil {
    public static void showShort(String msg) {
        Toast.makeText(BaseApplication.getmAppContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
