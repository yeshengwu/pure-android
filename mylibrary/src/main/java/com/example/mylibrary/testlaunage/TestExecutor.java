package com.example.mylibrary.testlaunage;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestExecutor {
    public static void main(String[] args) {
        // https://wenchao.ren/posts/threadpoolexecutor%E7%9B%B8%E5%85%B3/  理论上
        // 当workQueue已满，且maximumPoolSize > corePoolSize时，新提交任务会创建新线程执行任务。
        // 注意，新手容易犯的一个错是使用的是无界的workQueue，导致workQueue一直满不了，进而无法继续创建线程

        // 这里验证上面结论 LinkedBlockingQueue 有界还是无界时 能否用上最大线程数 maximumPoolSize 这个问题：
        // 如果 无法继续创建线程 导致执行效率降低一半。

        // 感悟：可能这样才算深入理解吧，用代码去验证，上面说 新手犯错，那后果是什么呢？没说，只能自己去验证，
        // 断点调试经验： ThreadPoolExecutor 这个类里的 execute 方法实现逻辑： As 调试发现不行，字节码不匹配。
        // 因为这个是 java sdk 跑的运行方式，点源码进去又是 android.jar 里的 ThreadPoolExecutor。
        // 解决： 另外开一个 Idea 专门 运行 java 项目的，那里可以断点调试。调试发现： 当 是无界队列时， a b 都没有走
        // 依然 任务 3 4 会被等待调度，不会执行 a 的拒绝或者 b的添加子线程。
        // 注意， c 这个地方就是 创建 新线程的， c 那里如果创建成功就返回 true, 这样 d 就不会走了，刚开始傻了没
        // 看 c 是干嘛的，以为只是判断语句，其实 是干了事情的（敲重点）， 差点 忽略导致理解不清楚整个流程。真他妈需要认真严谨（而不是忽略视而不见旧毛病）看代码才知道。
        // if (isRunning(c) && workQueue.offer(command)) {
        //     int recheck = ctl.get();
        //     if (! isRunning(recheck) && remove(command))
        //         reject(command);  // a
        //      else if (workerCountOf(recheck) == 0)
        //        addWorker(null, false); // b
        //    }
        //  else if (!addWorker(command, false)) // c
        //            reject(command);  // d
        // 这就叫 他妈的深刻理解。 2021-3-11

        // 4个任务。1 2 都是延迟5秒的任务。 3 4 秒出。
        // 如果 有界且 队列大小为2，不能是2以上，因为这里2个任务是耗时5秒的。会待在队列内触发 创建新线程。
        // 这样整个任务组运行下来日志： 19:15:16 到 19:15:21。
        // 3 Thread[pool-1-thread-2,5,main] finish time = Thu Mar 11 19:15:16 CST 2021
        //4 Thread[pool-1-thread-3,5,main] finish time = Thu Mar 11 19:15:16 CST 2021
        //1 Thread[pool-1-thread-1,5,main] start time = Thu Mar 11 19:15:16 CST 2021
        //2 Thread[pool-1-thread-2,5,main] start time = Thu Mar 11 19:15:16 CST 2021
        //1 Thread[pool-1-thread-1,5,main] finish time = Thu Mar 11 19:15:21 CST 2021
        //2 Thread[pool-1-thread-2,5,main] finish time = Thu Mar 11 19:15:21 CST 2021

        // 如果无界：日志：效率降低一半 19:18:21 到 19:18:31。相当于串行
        //1 Thread[pool-1-thread-1,5,main] start time = Thu Mar 11 19:18:21 CST 2021
        //1 Thread[pool-1-thread-1,5,main] finish time = Thu Mar 11 19:18:26 CST 2021
        //2 Thread[pool-1-thread-1,5,main] start time = Thu Mar 11 19:18:26 CST 2021
        //2 Thread[pool-1-thread-1,5,main] finish time = Thu Mar 11 19:18:31 CST 2021
        //3 Thread[pool-1-thread-1,5,main] finish time = Thu Mar 11 19:18:31 CST 2021
        //4 Thread[pool-1-thread-1,5,main] finish time = Thu Mar 11 19:18:31 CST 2021

//        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 3,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(1));
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(1)); // 这里 用无界 MAX_VALUE，或者大于 2的 比如128是一样的。队列满不了

        //1.实现一个自己的线程池工厂
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                //创建一个线程
                Thread t = new Thread(r);
                t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        System.out.println("线程工厂设置的exceptionHandler" + e.getMessage());
                    }
                });
                return t;
            }
        };
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(1),factory);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1),new DefaultThreadFactory(Thread.NORM_PRIORITY,"xxx"));

      //  如果 keepAliveTime = 0， allowCoreThreadTimeOut true 会抛异常
        // java.lang.IllegalArgumentException: Core threads must have nonzero keep alive times
        // 详情见 allowCoreThreadTimeOut 内部实现。
//        executor.allowCoreThreadTimeOut(true);

        // 问题2；keepAliveTime=0 表示的是 非核心 线程空闲退出。
        // https://blog.csdn.net/xuhangsong/article/details/89474989

        executor.execute(new Runnable() {
            @Override
            public void run() {

                System.out.println("1 " + Thread.currentThread() + " start time = " + (new Date().toString()));
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1 " + Thread.currentThread() + " finish time = " + (new Date().toString()));
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("2 " + Thread.currentThread() + " start time = " + (new Date().toString()));
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("2 " + Thread.currentThread() + " finish time = " + (new Date().toString()));
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                int i=1/0;
                System.out.println("3 " + Thread.currentThread() + " finish time = " + (new Date().toString()));

            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("4 " + Thread.currentThread() + " finish time = " + (new Date().toString()));
            }
        });
//        try {
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        executor.shutdown();
    }

    private static class DefaultThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);

        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private final int threadPriority;

        DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
            this.threadPriority = threadPriority;
            group = Thread.currentThread().getThreadGroup();
            namePrefix = threadNamePrefix + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    System.err.println("uncaughtException evan");
                }
            });
            if (t.isDaemon()) t.setDaemon(false);
            t.setPriority(threadPriority);
            return t;
        }
    }
}
