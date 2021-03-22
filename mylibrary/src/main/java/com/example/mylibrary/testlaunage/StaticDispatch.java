package com.example.mylibrary.testlaunage;

/**
 * 《深入理解jvm》 p247
 * 2021-3-20
 */
public class StaticDispatch {

    static abstract class Human {

    }

    static class Man extends Human {

    }

    static class Woman extends Human {

    }

    public void sayHello(Human guy){
        System.out.println("hello,guy!");
    }

    public void sayHello(Man guy){
        System.out.println("hello,gentleman!");
    }

    public void sayHello(Woman guy){
        System.out.println("hello,laydy!");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();

        StaticDispatch sr = new StaticDispatch();
        sr.sayHello(man);
        sr.sayHello(woman);
        // 输出结果：
        // hello,guy!
        // hello,guy!
    }
}
