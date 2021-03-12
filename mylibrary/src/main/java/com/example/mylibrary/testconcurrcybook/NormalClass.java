package com.example.mylibrary.testconcurrcybook;

public class NormalClass {
    private final Object lock = new Object();

//     class InnerClass {
//         static int a; // Inner classes cannot have static declarations
//    }

    public void doSomethingWait() throws InterruptedException {
        System.out.println("doSomething. current thread = " + Thread.currentThread());
        synchronized (lock) {
//            try {
//                System.out.println("doSomething before wait");
//                lock.wait();
//                System.out.println("doSomething after wait");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("doSomething before wait");
//            lock.wait();
//            System.out.println("doSomething after wait");

            try {
                System.out.println("doSomething before wait");
                Thread.sleep(2 * 1000);
                System.out.println("doSomething after wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void doSomNotify() {
        System.out.println("doSomNotify. current thread = " + Thread.currentThread());
        synchronized (lock) {
            System.out.println("doSomNotify before notifyAll");
            lock.notifyAll();
            System.out.println("doSomNotify after notifyAll");
        }
    }

}
