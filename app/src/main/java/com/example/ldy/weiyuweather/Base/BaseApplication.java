package com.example.ldy.weiyuweather.Base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.orm.SugarContext;

/**
 * Created by LDY on 2016/10/7.
 */
public class BaseApplication extends Application {
    public static String cacheDir;
    private static Context mAppContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
        mAppContext = getApplicationContext();

        //若存在SD卡则缓存在SD卡上，否则缓存在手机内存里
        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }
    }

    private boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    public static Context getmAppContext() {
        return mAppContext;
    }
}
