package com.example.mylibrary.testlaunage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 经典的多线程面试题：
 * A 线程 打印A
 * B 线程 打印B
 * c 线程 打印C
 * 3个线程循环打印10次
 * 结果： ABC ABC ABC ....
 * https://juejin.cn/post/7149006608209543176 三个线程顺序打印ABC?我有十二种做法！
 * ----evan： 这十二种做法他妈牛逼了，还有图展示非常清晰，把并发基本都搞了一遍。可以关注这个博主
 */
public class TestInterviewPrintABC {
    final Lock lock = new ReentrantLock();
    final Condition conditionA = lock.newCondition();
    final Condition conditionB = lock.newCondition();
    final Condition conditionC = lock.newCondition();

    public static void main(String[] args) {
        TestInterviewPrintABC example = new TestInterviewPrintABC();

        new Thread(new TestInterviewPrintABC.RunA(example.lock, example.conditionC, example.conditionA)).start();
        new Thread(new TestInterviewPrintABC.RunB(example.lock, example.conditionA, example.conditionB)).start();
        new Thread(new TestInterviewPrintABC.RunC(example.lock, example.conditionB, example.conditionC)).start();

        // 先触发 conditionC signal 唤醒A线程开启A的打印。
        example.lock.lock();
        System.out.println("main lock after");
        try {
            System.out.println("main conditionC signal");
            example.conditionC.signal();
        } finally {
            example.lock.unlock();
            System.out.println("main unlock");
        }

    }

    static class RunA implements Runnable {
        Lock lock;
        Condition conditionC;
        Condition conditionA;

        /**
         * @param lock
         * @param conditionC await C
         * @param conditionA signal A 发送自己信号到 其他人
         */
        public RunA(Lock lock, Condition conditionC, Condition conditionA) {
            this.lock = lock;
            this.conditionC = conditionC;
            this.conditionA = conditionA;
        }

        @Override
        public void run() {
            lock.lock();
            System.out.println("A lock after");
            try {

                for (int i = 0; i < 10; i++) {
                    try {
                        System.out.println("A await");
                        conditionC.await();

                        System.out.println("A");

                        conditionA.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } finally {
                lock.unlock();
                System.out.println("A unlock");
            }

        }
    }

    static class RunB implements Runnable {
        Lock lock;
        Condition conditionA;
        Condition conditionB;

        public RunB(Lock lock, Condition conditionA, Condition conditionB) {
            this.lock = lock;
            this.conditionA = conditionA;
            this.conditionB = conditionB;
        }

        @Override
        public void run() {
            lock.lock();
            System.out.println("B lock after");
            try {

                for (int i = 0; i < 10; i++) {
                    try {
                        System.out.println("B await");
                        conditionA.await();

                        System.out.println("B");

                        conditionB.signal();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } finally {
                lock.unlock();
                System.out.println("B unlock");
            }

        }
    }

    static class RunC implements Runnable {
        Lock lock;
        Condition conditionB;
        Condition conditionC;

        public RunC(Lock lock, Condition conditionB, Condition conditionC) {
            this.lock = lock;
            this.conditionB = conditionB;
            this.conditionC = conditionC;
        }

        @Override
        public void run() {
            lock.lock();
            System.out.println("C lock after");
            try {

                for (int i = 0; i < 10; i++) {
                    try {
                        System.out.println("C await");
                        conditionB.await();

                        System.out.println("C");

                        conditionC.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } finally {
                lock.unlock();
                System.out.println("C unlock");
            }

        }
    }

}