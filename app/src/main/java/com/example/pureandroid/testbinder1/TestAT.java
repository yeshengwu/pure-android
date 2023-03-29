package com.example.pureandroid.testbinder1;

import androidx.annotation.NonNull;

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


        /**
         * 测试 AIDL 生成类中，抽象类实例化时 抽象类构造器和父类构造器是否被调用。
         * 结论是 抽象类和父类都会被调用
         *
         * BinderEvan constructor
         * AbsBinderEvanStub constructor
         * stub instance = stub toString
         */
        AbsBinderEvanStub stub = new AbsBinderEvanStub() {

            @Override
            public void add(int a, int b) {
                System.out.println("stub instance IN add:  a = " + a + " b = " + b);
            }

            @NonNull
            @Override
            public String toString() {
                return "stub toString";
            }
        };
        System.out.println("stub instance = " + stub + " invoke add(1,2)");
        stub.add(1, 2);
        // Binder test end.
    }

    private void attach(boolean system, long startSeq) {
        System.out.println("IN attach");
    }

    class H extends TestHandler {

    }
}
