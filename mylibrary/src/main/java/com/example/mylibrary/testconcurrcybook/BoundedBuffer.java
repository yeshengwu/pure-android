package com.example.mylibrary.testconcurrcybook;

/**
 * https://jcip.net/listings/BoundedBuffer.java
 * @param <V>
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
    // 条件谓词
    // CONDITION PREDICATE: not-full (!isFull())
    // CONDITION PREDICATE: not-empty (!isEmpty())

    public BoundedBuffer() {
        this(100);
    }

    public BoundedBuffer(int size) {
        super(size);
    }

    // BLOCKS-UNTIL: not-full
    public void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPut(v);
        notifyAll();
    }

    // BLOCKS-UNTIL: not-empty
    public synchronized V take() throws InterruptedException {
        while (isEmpty())
            wait();
        V v = doTake();
        notifyAll();
        return v;
    }

}
