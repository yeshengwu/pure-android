package com.example.mylibrary;

public class InterruptExample {

    public static void main(String[] args) throws InterruptedException {
        Thread thread2 = new MyThread2();
        thread2.start();

        Thread.sleep(2*1000);

        thread2.interrupt();
    }

    // http://www.cyc2018.xyz/Java/Java%20%E5%B9%B6%E5%8F%91.html#yield  中断

    private static class MyThread2 extends Thread {
        @Override
        public void run() {
//            while (!interrupted()) {
            while (!Thread.currentThread().isInterrupted()) {
                // ..
                System.out.println("Thread run");

                try {
                    Thread.sleep(4*1000);
                } catch (InterruptedException e) {
                    System.out.println("Thread sleep InterruptedException");
                    e.printStackTrace();
                    // sleep 被打断后，根据 interrupt() api，调用interrupt 中断  某个 由于调用 sleep wait 等陷入阻塞的线程B，
                    // 那么 B interrupted() 状态被清掉从而返回 false, while 循环继续执行，线程不退出

                    System.out.println("Thread sleep InterruptedException then interrupt currentThread");
                    // 不加这句， while 循环一直走，线程不退出，加了后线程退出。
                    Thread.currentThread().interrupt();
                }
            }
//            System.out.println("Thread run. static 1 interrupted() = "+interrupted());
//            System.out.println("Thread run. static 2 interrupted() = "+interrupted());


//            while (true){
//                System.out.println("Thread run");
//                System.out.println("Thread run. static 1 interrupted() = "+interrupted());
//                System.out.println("Thread run. static 2 interrupted() = "+interrupted());
//            }
            System.out.println("Thread end");
        }
    }


}
