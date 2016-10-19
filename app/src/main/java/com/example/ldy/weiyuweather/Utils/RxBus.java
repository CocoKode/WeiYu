package com.example.ldy.weiyuweather.Utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by LDY on 2016/10/16.
 */
public class RxBus {
    private final Subject<Object, Object> bus;

    //publishSubject订阅后立即发送数据；SerializedSubject保证发送数据的顺序
    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getDefault() {
        return RxBusHolder.sInstance;
    }

    private static class RxBusHolder {
        private static final RxBus sInstance = new RxBus();
    }

    //提供新事件
    public void post(Object o) {
        bus.onNext(o);
    }

    //http://www.jianshu.com/p/ca090f6e2fe2/
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
