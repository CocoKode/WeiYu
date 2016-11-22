package com.example.ldy.weiyuweather.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ldy.weiyuweather.BroadCastReceiver.AutoUpdateReceiver;
import com.example.ldy.weiyuweather.NetWork.RetrofitSingleton;
import com.example.ldy.weiyuweather.Utils.SharedPreferenceUtil;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

/**
 * Created by LDY on 2016/11/16.
 */
public class AutoUpdateService extends Service{
    private static int mUpdateTime;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mUpdateTime = intent.getIntExtra("updateTime", 0);

        Log.d("autoupdateservice", "onstartcommand" + mUpdateTime);

        new Thread(new Runnable() {
            @Override
            public void run() {
                update();
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int time = mUpdateTime * 1000 * 60 * 60;
        long triggerTime = SystemClock.elapsedRealtime() + time;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void update() {
        String cityId = SharedPreferenceUtil.getInstance().getCityId();
        if (cityId == null) {
            return;
        }
        RetrofitSingleton.getInstance().fetchWeather(cityId)
                .subscribe(weather -> {
                });
    }
}
