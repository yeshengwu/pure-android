package com.example.mylibrary;

import com.example.mylibrary.testbinder.AbsBinderEvanStub;
import com.example.mylibrary.testconcurrcybook.Father;
import com.example.mylibrary.testconcurrcybook.TestImpl;
import com.example.mylibrary.testconcurrcybook.ThisEscape;

import androidx.annotation.NonNull;

public class TestEntry {

    public static void main(String[] args) {

        /**
         * 并发编程实战p47 发布逸出 ： 使内部的可变状态逸出
         */
        Father father = new Father();
        System.out.println(father);

        String[] fatherStats = father.getStates();
        fatherStats[0] = "xxxxx";
        System.out.println(father);

        //Father{states=[a, b]}
        //Father{states=[xxxxx, b]}

        ThisEscape thisEscape = new ThisEscape(father);

        /**
         * end
         */

        int x = 1;
        int b = 2;
        x = b;
        System.out.println("x1 = " + x);
        b = 3;
        System.out.println("x2 = " + x);

        String sa = "sa";
        String sb = "sb";
        sa = sb;
        System.out.println("sa1 = " + sa);
        sb = "sb2";
        System.out.println("sa2 = " + sa);

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


//        TestThread testThread = new TestThread();
//        testThread.start();

//        try {
//            Thread.sleep(5*1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        testThread.stop();

//        CountDownLatch

        final TestImpl test = new TestImpl();
        Thread AA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    test.doSomethingWait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A");
        AA.start();

        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Thread BB = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                test.doSomNotify();
//            }
//        },"B");
//        BB.start();

        AA.interrupt();

        TestEntry entry = new TestEntry();

    }

}
