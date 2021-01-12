package com.example.mylibrary.testconcurrcybook;

/**
 * https://jcip.net/listings/SleepyBoundedBuffer.java
 * @param <V>
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    int SLEEP_GRANULARITY = 60;

    public SleepyBoundedBuffer() {
        this(100);
    }

    public SleepyBoundedBuffer(int size) {
        super(size);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            // evan p256 页脚解释：不释放锁，这将导致其他线程无法修改 状态（缓存为空/满）
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }
}
