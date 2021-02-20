package com.example.mylibrary.gof.chain;

/**
 * https://www.jianshu.com/p/7fa31c57cbb5
 * Android的设计模式-责任链模式
 * <p>
 */
public class TestChain {
    public static void main(String[] args) {
        //创建不同的快递员对象
        Postman beijingPostman = new BeijingPostman();
        Postman shanghaiPostman = new ShanghaiPostman();
        Postman guangzhouPostman = new GuangzhouPostman();

        //创建下一个结点
        beijingPostman.nextPostman = shanghaiPostman;
        shanghaiPostman.nextPostman = guangzhouPostman;

        //处理不同地区的快递，都是从首结点北京快递员开始
        System.out.println("有一个上海快递需要派送:");
        beijingPostman.process("Shanghai");

        // 日志：
        //有一个上海快递需要派送:
        //IN Chain BeijingPostman address = Shanghai
        //IN Chain BeijingPostman nextPostman process.
        //IN Chain ShanghaiPostman address = Shanghai
        //派送到上海
        //IN Chain BeijingPostman nextPostman process res = Shanghai ShanghaiPostman has processed.

        System.out.println("有一个美国快递需要派送:");
        beijingPostman.process("America");

        // 日志
        //有一个美国快递需要派送:
        //IN Chain BeijingPostman address = America
        //IN Chain BeijingPostman nextPostman process.
        //IN Chain ShanghaiPostman address = America
        //IN Chain ShanghaiPostman nextPostman process.
        //IN Chain GuangzhouPostman address = America
        //IN Chain GuangzhouPostman nextPostman process res = null
        //IN Chain ShanghaiPostman nextPostman process res = null
        //IN Chain BeijingPostman nextPostman process res = null

        System.out.println("有一个广州快递需要派送:");
        beijingPostman.process("Guangzhou");

        // 日志：
       //有一个广州快递需要派送:
        //IN Chain BeijingPostman address = Guangzhou
        //IN Chain BeijingPostman nextPostman process.
        //IN Chain ShanghaiPostman address = Guangzhou
        //IN Chain ShanghaiPostman nextPostman process.
        //IN Chain GuangzhouPostman address = Guangzhou
        //派送到广州
        //IN Chain ShanghaiPostman nextPostman process res = Guangzhou GuangzhouPostman has processed.
        //IN Chain BeijingPostman nextPostman process res = Guangzhou GuangzhouPostman has processed.
    }
}
