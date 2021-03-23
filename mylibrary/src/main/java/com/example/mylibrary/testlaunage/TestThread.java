package com.example.mylibrary.testlaunage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestThread {
    private static Lock lockA;
    private static Lock lockB;

    public static void main(String[] args) {
        System.out.println("TestThread main");
        lockA = new ReentrantLock();
        lockB = new ReentrantLock();
        new Thread(new RunnableA(lockA, lockB),"threadTestA").start();
        new Thread(new RunnableB(lockA, lockB),"threadTestB").start();
    }
    static class RunnableA implements Runnable {
        private Lock lockA;
        private Lock lockB;

        RunnableA(Lock lockA, Lock lockB) {
            this.lockA = lockA;
            this.lockB = lockB;
            this.lockA.lock();
        }

        @Override
        public void run() {
            this.lockB.lock();
        }
    }

    static class RunnableB implements Runnable {
        private Lock lockA;
        private Lock lockB;

        RunnableB(Lock lockA, Lock lockB) {
            this.lockA = lockA;
            this.lockB = lockB;
            this.lockB.lock();
        }

        @Override
        public void run() {
            this.lockA.lock();
        }
    }



}
