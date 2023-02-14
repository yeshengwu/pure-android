package com.example.pureandroid.livebus;


import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public final class LiveDataBus {

    private final Map<String, BusLiveEvent<Object>> bus;

    private LiveDataBus() {
        this.bus = new HashMap();
    }

    public static class SingletonHolder {
        private static final LiveDataBus DEFAULT_BUS = new LiveDataBus();

        private SingletonHolder() {
        }
    }

    // 第一步： 单例持有 map 后返回 bus。
    public static LiveDataBus get() {
        return SingletonHolder.DEFAULT_BUS;
    }

    // 第四步 对外提供 注册观察者 和 持有 Observer来发送事件 api ---
//    public <T> void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
//        get().bus.get("event1").observe(owner, (Observer<? super Object>) observer);
//    }
//
//    public <T> void observeForever(@NonNull Observer<? super T> observer) {
//        get().bus.get("event1").observeForever((Observer<? super Object>) observer);
//    }

    // 这个是 注册观察者接口
    public synchronized <T> Observable<T> with(String key, Class<T> type) {
        if (!this.bus.containsKey(key)) {
            this.bus.put(key, new BusLiveEvent<>());
        }
        return (Observable<T>) this.bus.get(key);
    }

//    public <T> Observable<T> with(String eventKey, Class<T> clazz) {
//        BusLiveEvent<T> objectBusLiveEvent = (BusLiveEvent<T>) get().bus.get(eventKey);
//        return objectBusLiveEvent;
//    }

    // 这个是发送事件接口，当然如果作为注册接口只能注册 Object 范型的观察者。
    public Observable<Object> with(String key) {
        return with(key, Object.class);
    }

    public void remove(String key) {
        remove(key, null, null);
    }

    public void remove(String key, LifecycleOwner owner, Observer observer) {
        BusLiveEvent eventType;
        /*if (this.bus.containsKey(key) && (eventType = this.bus.get(key)) != null) {
            if (owner != null) {
                eventType.removeObservers(owner);
            }
            if (observer != null) {
                eventType.removeObserver(observer);
            }
            if (!eventType.hasObservers()) {
                this.bus.remove(key);
            }
        }*/
    }

    // 第二步：定义主题

    /**
     * 这个接口作用是什么？没有会怎样？
     * 观察者模式---主题 注册观察者， 类似 RecyclerView 中 Adapter AdapterDataObservable extends Observable<AdapterDataObserver>
     * <p>
     * 不是单纯 注册观察者，还有 setValue() 这样的行为该行为内部事件发生会通知观察者。--也符合主题 责任： 1，注册观察者 2，由外部来触发事件的发生。
     *
     * @param <T>
     */
    public interface Observable<T> {
        void observe(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer<T> observer);

        void observeForever(@NonNull Observer<T> observer);

        void observeSticky(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer<T> observer);

        void observeStickyForever(@NonNull Observer<T> observer);

        void postValue(T t);

        void removeObserver(@NonNull Observer<T> observer);

        void removeObservers(@NonNull LifecycleOwner lifecycleOwner);

        void setValue(T t);
    }

    // 第三步 根据观察者模式 重写LiveData 实现主题
    // 报错：要求BusLiveEvent 实现 Observable<T>中所有接口。
//    public static class BusLiveEvent<T> extends LiveData<T> implements Observable<T> {
//
//
//    }
    // 改为 LiveEvent<T> 来实现 Observable<T>中所有接口，这就是 LiveEvent要拷贝 LiveData代码再改吧改吧。
    public static class BusLiveEvent<T> extends LiveEvent<T> implements Observable<T> {
        @Override
        public Lifecycle.State observerActiveLevel() {
            return super.observerActiveLevel();
        }
    }
}
