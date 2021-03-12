package com.example.mylibrary.gof.single;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * https://github.com/allentofight/easy-cs/blob/main/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F/%E6%88%91%E7%94%A8DCL%E5%86%99%E5%87%BA%E4%BA%86%E5%8D%95%E4%BE%8B%E6%A8%A1%E5%BC%8F%EF%BC%8C%E7%BB%93%E6%9E%9C%E9%98%BF%E9%87%8C%E9%9D%A2%E8%AF%95%E5%AE%98%E4%B8%8D%E6%BB%A1%E6%84%8F%EF%BC%81.md
 * http://www.cyc2018.xyz/%E5%85%B6%E5%AE%83/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%20%20-%20%E5%8D%95%E4%BE%8B.html#implementation
 *
 * 反射攻击 序列化攻击
 * 最安全的方式是 enum方式，链接中给了原因.
 * TODO 暂时没找到 newInstace那个 enum判断
 */
public class TestSingleton {
    public static void main(String[] args) {
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 3,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>());
        ExecutorService executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
//                    SingleTonDcl instance = SingleTonDcl.getInstance();
                    SingletonEnum instance = SingletonEnum.getInstance();
                    System.out.println("instance = " + instance);
                }
            });
        }

        // 反射获取实例测试
        try {
            SingletonEnum[] enumConstants = SingletonEnum.class.getEnumConstants();
            for (SingletonEnum enumConstant : enumConstants) {
                System.out.println(enumConstant.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }
}
