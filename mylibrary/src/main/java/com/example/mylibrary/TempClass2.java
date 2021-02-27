package com.example.mylibrary;

import java.lang.ref.WeakReference;

/**
 * ThreadLocal 内部 Entry 类
 */
public class TempClass2  extends WeakReference<ThreadLocal<?>> {
    /** The value associated with this ThreadLocal. */
    Object value;

    public TempClass2(ThreadLocal<?> referent, Object v) {
        super(referent);
        value = v;
    }

}
