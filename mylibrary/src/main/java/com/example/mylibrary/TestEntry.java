package com.example.mylibrary;

import com.example.mylibrary.testbinder.AbsBinderEvanStub;
import com.example.mylibrary.testconcurrcybook.Father;
import com.example.mylibrary.testconcurrcybook.NormalClass;
import com.example.mylibrary.testconcurrcybook.ThisEscape;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

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

        final NormalClass test = new NormalClass();
        Thread aaThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    test.doSomethingWait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "aaThread");
        aaThread.start();

        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Thread bbThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                test.doSomNotify();
//            }
//        },"bbThread");
//        bbThread.start();

        aaThread.interrupt();

        TestEntry entry = new TestEntry();

        int tableSizeFor5 = tableSizeFor(5);
        int tableSizeFor6 = tableSizeFor(6);
        int tableSizeFor8 = tableSizeFor(8);
        int tableSizeFor9 = tableSizeFor(9);
        System.out.println("tableSizeFor5 = " + tableSizeFor5 + " 6 = " + tableSizeFor6 + " 8 = " + tableSizeFor8 + " 9 = " + tableSizeFor9);
        // 结论：找到 initialCapacity 返回大于输入参数且最近的2的整数次幂的数，日志如下：
        // tableSizeFor5 = 8 6 = 8 8 = 8 9 = 16
        // HashMap 中， this.threshold = tableSizeFor(initialCapacity);

        HashMap<String, String> testHashMap = new HashMap<>();
        String putReturnA = testHashMap.put("a", "axx");
        System.out.println("putReturnA = " + putReturnA);
        String putReturnB = testHashMap.put("b", "bxx");
        System.out.println("putReturnB = " + putReturnB);
        String putReturnA2 = testHashMap.put("a", "axx2");
        System.out.println("putReturnA2 = " + putReturnA2);
        // 结论： put 返回值是 这个 key 之前映射的值，日志如下：
        //putReturnA = null
        //putReturnB = null
        //putReturnA2 = axx

        testHashMap.put(null,"value of null key");
        System.out.println("testHashMap = " + testHashMap);
        String valueOfNullKey = testHashMap.get(null);
        System.out.println("valueOfNullKey = " + valueOfNullKey);

        /**
         * 结论： k-- --k区别： k-- 先拿值，再-1.while 会走
         *  --k， -1后 while 不走了。
         */
        int k = 1;
        System.out.println("k init =  " + k);
        while (k-- > 0) {
            System.out.println("k--. IN while k = " + k);
        }
        int i = 1;
        System.out.println("i init =  " + i);
        while (--i > 0) {
            System.out.println("--i. IN while i = " + i);
        }


        TestEntry.testWitchCallback(new Callback() {
            @Override
            public void onDataBack() {
                System.out.println("testWitchCallback onDataBack");
            }
        });
        System.out.println("testWitchCallback nextLine");

        if (entry.getXX() instanceof String) {
            String xxx = (String) entry.getXX();
            System.out.println("xxx = "+xxx);
        } else {
            System.out.println(" else xxx ");
        }

        // 我们知道目前a和b是两个不同的对象，他们在内存中存放的地址是不同的，
        // System.identityHashCode方法是java根据对象在内存中的地址算出来的一个数值，不同的地址算出来的结果是不一样的。因此这里打印出的结果不一样。
        String aaa = new String("hhh");
        String bbb = new String("hhh");
        // String类中已经重新写了hashCode()方法，也就是说，String类中hashcode，已经不是根据对象在内存中的地址计算出来的
        // 日志如下：
        // aaa identityHashCode 1173230247
        //bbb identityHashCode 856419764
        //aaa hashCode 103272
        //bbb hashCode 103272
        System.out.println("aaa identityHashCode "+ System.identityHashCode(aaa));
        System.out.println("bbb identityHashCode "+ System.identityHashCode(bbb));
        System.out.println("aaa hashCode "+ aaa.hashCode());
        System.out.println("bbb hashCode "+ bbb.hashCode());
        System.out.println("aaa Objects.hashCode "+  Objects.hashCode(aaa)); // 内部调用 o.hashCode()
        System.out.println("bbb Objects.hashCode "+  Objects.hashCode(bbb));

        TempClass tempClass = new TempClass("name",20);
        System.out.println("tempClass hashCode "+ tempClass.hashCode());

        // tables len = 10
        //tables = [null, fuck, null, null, null, null, null, null, null, null]
        String[] tables = new String[10];
        tables[1] = "fuck";
        System.out.println("tables len = "+ tables.length);
        System.out.println("tables = "+ Arrays.toString(tables));
    }

    private Object getXX() {
        return new Father();
    }

    interface Callback {
        void onDataBack();
    }

    private static void testWitchCallback(final Callback callback) {
        if (callback != null) {
            callback.onDataBack();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (callback != null) {
//                    callback.onDataBack();
//                }
//            }
//        }).start();
    }

    /**
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

}
