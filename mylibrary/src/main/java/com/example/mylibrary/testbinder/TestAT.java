package com.example.mylibrary.testbinder;

/**
 * test ActivityThread:  取名 TestAT 是为了搜索类时不和 ActivityThread 混一起。
 * q
 */
public class TestAT {

    private H mH = new H();

    public static void main(String[] args) {
        // 第一步：执行这里
        System.out.println("prepareMainLooper");

        // 第二步：初始化 TestAT 对象. 这里才会执行 H 的构造，H 的父类 Handler 构造方法就会执行，
        // 这个时候 Handler 内部已经 Looper 在上一步被初始化了，所以不会抛异常：
        //          throw new RuntimeException(
        //                "Can't create handler inside thread " + Thread.currentThread()
        //                        + " that has not called Looper.prepare()");。
        TestAT activityThread = new TestAT();
        activityThread.attach(false,0);
    }

    private void attach(boolean system, long startSeq) {
        System.out.println("IN attach");
    }

    class H extends TestHandler {

    }
}
