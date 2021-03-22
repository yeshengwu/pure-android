package com.example.mylibrary.testlaunage;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * copy form ThreadLocal doc.
 */
public class ThreadId {

    // 如果 static 报错抛出异常，那么这个类就无法工作了，
//    static {
//        try {
//            int i = 1/0;
//        } catch (Exception e) {
//             throw new Error(e);
//        }
//    }

    // Atomic integer containing the next thread ID to be assigned
    private static final AtomicInteger nextId = new AtomicInteger(0);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Integer> threadId =
            new ThreadLocal<Integer>() {
                @Override protected Integer initialValue() {
                    return nextId.getAndIncrement();
                }
            };

    // Returns the current thread's unique ID, assigning it if necessary
    public static int get() {
        return threadId.get();
    }
}
