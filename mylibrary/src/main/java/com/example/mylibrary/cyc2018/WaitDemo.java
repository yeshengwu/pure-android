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
                    waitDemo.doWait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 验证 sleep 不会释放锁， sleep api 已经说了。 The thread does not lose ownership of any monitors
        // log验证： sleep 先拿锁就顾自己，后面 doNotify 拿不到锁只能等着。
        // sleep start.
        //sleep end.
        //notify start.
        //notify end.
/*        new Thread(new Runnable() {
            @Override
            public void run() {

                // sleep前先拿锁看后面会不会释放。
                synchronized (locker) {

                    try {
                        System.out.println("sleep start.");
                        Thread.sleep(1000);
                        System.out.println("sleep end.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();*/

        Thread.sleep(200);// 此行本身没有意义，是为了确保 wait() 先执行再执行 notify()
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
