package com.example.ldy.weiyuweather.Base;

import rx.Subscriber;

/**
 * Created by LDY on 2016/10/17.
 * 抽离onCompleted和onError方法，只注重onNext
 */
public abstract class SimpleSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
