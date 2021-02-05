package com.example.mylibrary.cyc2018;

/**
 * https://www.cnblogs.com/vipstone/p/13354552.html
 * 如何证明sleep不释放锁，而wait释放锁？
 */
public class WaitDemo {
    private final static Object locker = new Object();

    public static void main(String[] args) throws InterruptedException {
        final WaitDemo waitDemo = new WaitDemo();
        // 启动新线程，防止主线程被休眠
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("sleep start.");
                    waitDemo.doWait();
                    System.out.println("sleep end.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(200);
        waitDemo.doNotify();
    }

    /**
     * 执行 wait()
     */
    private void doWait() throws InterruptedException {
        synchronized (locker) {
            System.out.println("wait start.");
            locker.wait();
            System.out.println("wait end.");
        }
    }

    /**
     * 执行 notify()
     */
    private void doNotify() {
        synchronized (locker) {
            System.out.println("notify start.");
            locker.notify();
            System.out.println("notify end.");
        }
    }
}
