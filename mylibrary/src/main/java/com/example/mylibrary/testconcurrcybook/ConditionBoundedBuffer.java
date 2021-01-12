package com.example.mylibrary.testconcurrcybook;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionBoundedBuffer<T> {
    protected final Lock lock = new ReentrantLock();
    // CONDITION PREDICATE: notFull (count < items.length)
    private final Condition notFull = lock.newCondition();
    // CONDITION PREDICATE: notEmpty (count > 0)
    private final Condition notEmpty = lock.newCondition();
    private static final int BUFFER_SIZE = 100;
    private final T[] items = (T[]) new Object[BUFFER_SIZE];
    private int tail, head, count;

    // BLOCKS-UNTIL: notFull
    public void put(T x) throws InterruptedException {
        System.out.println("put lock");
        lock.lock();
        try {
            while (count == items.length) {
                System.out.println("put full. await");
                notFull.await();
            }
            items[tail] = x;
            if (++tail == items.length)
                tail = 0;
            ++count;
            System.out.println("put now. notEmpty signal");
            notEmpty.signal();
        } finally {
            lock.unlock();
            System.out.println("put finally");
        }
    }

    // BLOCKS-UNTIL: notEmpty
    public T take() throws InterruptedException {
        System.out.println("take lock");
        lock.lock();
        try {
            while (count == 0) {
                System.out.println("take empty. await");
                notEmpty.await();
            }
            T x = items[head];
            items[head] = null;
            if (++head == items.length)
                head = 0;
            --count;
            System.out.println("take now. notFull signal");
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
            System.out.println("take finally");
        }
    }

    public static void main(String[] args) {
        final ConditionBoundedBuffer<String> boundedBuffer = new ConditionBoundedBuffer<String>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    try {
                        System.out.println("put item = " + (" i: " + i));
                        boundedBuffer.put("i : " + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String taked = boundedBuffer.take();
                        System.out.println("take = " + taked);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }
}
