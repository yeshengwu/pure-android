package com.example.mylibrary.testconcurrcybook;

public class TestThread implements Runnable {
    private final Object lock = new Object();
    private Thread ourThread;

    public void start() {
        ourThread = new Thread(this);
        ourThread.start();
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                System.out.println("TestThread run before wait");
                lock.wait();
                System.out.println("TestThread run after wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        try {
//            Thread.sleep(20 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void stop() {
        ourThread.interrupt();
    }
}
