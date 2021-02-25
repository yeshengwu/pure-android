package com.example.mylibrary.testlaunage;

import androidx.annotation.Nullable;

public class ThreadLocalTest {
    // 新建一个 ThreadLocal 对象
    static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
        @Nullable
        @Override
        protected Integer initialValue() {
            return 2;
        }
    };

    public static void startTest() {
        // 新建 5 个子线程，run 方法中调用新建的 ThreadLocal 对象 value 的 get/set 方法来获取/设置对应值
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                public void run() {
                    // 当当前线程中 value 值不大于 5 时候继续循环
                    while (value.get() <= 5) {
                        System.out.println(Thread.currentThread().getName() + " 的 value 值：" + value.get());
                        // 当前线程的 value 自增一
                        value.set(value.get() + 1);
                    }
                }

                ;
            }, "线程 " + (i + 1)).start();
        }
    }

    public static void main(String[] args) {
        ThreadLocalTest.startTest();
    }
}
