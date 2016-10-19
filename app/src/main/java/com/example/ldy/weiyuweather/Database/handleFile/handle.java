package com.example.ldy.weiyuweather.Database.handleFile;

import android.content.Context;
import android.util.Log;

import com.example.ldy.weiyuweather.Database.Bean.CityId;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LDY on 2016/10/7.
 * 使用RxJava将文件中的部分数据存入数据库中
 */
public class handle {
    private static final String FILE_NAME = "CityId";
    private static final String DB_NAME = "city_id";

    private static void handleWithRx (final Context context) {
        File file = context.getDatabasePath(DB_NAME);
        if (file.exists()) {
            Log.d("handle", "数据库已存在");
        }

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    BufferedReader reader = null;
                    InputStream in = context.getAssets().open(FILE_NAME);
                    reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        String[] cityInfo = line.trim().split(",");
                        if (cityInfo != null && cityInfo.length > 0) {
                            String cityID = cityInfo[0];
                            String citySpell = cityInfo[1];
                            String cityName = cityInfo[2];

                            //依次存入数据库
                            CityId city = new CityId(cityID, citySpell, cityName);
                            city.save();
                        }
                    }
                    if (reader != null)
                        reader.close();
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("handle", "传输完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                    }
                });
    }
}
