package com.example.mylibrary.cyc2018;

/**
 * https://blog.csdn.net/qq_20952591/article/details/121316085 线程篇——线程的停止与中断
 */
public class InterruptExample2 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new MyThread2();
        thread.start();

        Thread.sleep(100);

        thread.interrupt();
        System.out.println(thread.isInterrupted());
        System.out.println("程序结束！");
    }

    private static class MyThread2 extends Thread {
        @Override
        public void run() {
            int i = 0;
            while (i < 1000) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("线程已经被interrupt了！我要退出了！");
                    break;
                }
                if (i % 100 == 0) {
                    System.out.println(i + "是100的倍数");
                }
                i++;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread end");
        }
    }


}
