package com.example.mylibrary.cyc2018;

import java.util.Vector;

/**
 * 深入理解jvm 书 测试代码 第12篇：线程安全与锁优化
 * https://blog.csdn.net/pcwl1206/article/details/84674792
 */
public class InsideVM {
    private final static Vector<Integer> vector = new Vector<>();

    public static void main(String[] args) throws Throwable {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }

            Thread removeThread = new Thread(new Runnable() {
                public void run() {
                    synchronized (vector) {
                        for (int i = 0; i < vector.size(); i++) {
                            vector.remove(i);
                        }
                    }
                }
            });

            Thread printThread = new Thread(new Runnable() {
                public void run() {
                    synchronized (vector) {
                        for (int i = 0; i < vector.size(); i++) {
                            System.out.println(vector.get(i));
                        }
                    }
                }
            });

            removeThread.start();
            printThread.start();
            // 不要同时产生过多的线程，否则会导致操作系统假死 ---evan 这个真有用吗？
            while (Thread.activeCount() > 20) ;
        }
    }
}