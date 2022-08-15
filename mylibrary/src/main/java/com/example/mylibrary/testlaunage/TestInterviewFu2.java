package com.example.mylibrary.testlaunage;

public class TestInterviewFu2 {
    int a = 0;

    public static void main(String[] args) {
        final TestInterviewFu2 demo = new TestInterviewFu2();
        /**
         * 2个线程执行完后 全局变量（a）的值是多少？
         * 价值感悟：1，如果说 对某个知识点掌握，怎么确定掌握呢？ 用一道相关知识点的经典面试题
         * 测测就知道了。（经典面试题:i++是线程安全的吗）
         * 2，如果 某人知道 某个知识点比如 i++ 是复合操作不安全的，知道这个结论和测试题目中
         * 能不能灵活运用这个结论来解决问题又是 更高的要求了，光知道却不会解决实际问题，依然不达标。
         * 3，面试过程中 自己要确认答案，不要面试官一质疑就开始怀疑自己的答案，这还是反应了知识点没有
         * 深刻理解，并加以解决实战中的问题。
         *
         * 回到本题， 打印 序列1（大部分可能从1开始乱序交替打印）
         * 序列2（可能从2开始乱序交替打印）
         * 那么 这个序列最小值会是几？ 为什么，说出出现这个结果的线程运行情况？
         * 答案：2这个情况： 线程1 开始运行 i++，i++不安全，i为刚好 ++时，2线程来运行则 为 i+1 为2.
         * 最大值是几？小于 或者 等于 20 因为 i++ 不安全，所以可能不能一直累加到20
         */
        // 复现验证: 开1000个线程交替 i++. 打印结果显示：最后一个值 a = 9998 明显不能到 10000。
        for (int i = 0;i<1000;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    demo.func1();
                }
            }).start();
        }
    }

    /**
     * synchronized 加上同步就能妥妥的 a到 10000。
     */
    public  void func1() {
        for (int i = 0; i < 10; i++) {
            a++;
            System.out.println(a + " ");
        }
    }

}
