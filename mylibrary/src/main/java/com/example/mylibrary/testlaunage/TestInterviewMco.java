package com.example.mylibrary.testlaunage;

import java.util.Arrays;

/**
 * https://blog.csdn.net/u014745069/article/details/82655339
 * java  类加载 顺序
 */
public class TestInterviewMco {

    public static void main(String[] args) {
        A bb = new A();
        try {
            Thread.sleep(1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("wait 2s");
        bb = new B();

//        A bb = new B();

        Integer x1 =  Integer.valueOf(12);
        Integer x2 =  Integer.valueOf(12);
        System.out.println("Integer == Integer2 "+(x1 == x2));

        String sss = "hell0";
        String inter = sss.intern();
        System.out.println("inter ==  "+inter+ " inter == sss "+(sss == inter));

        // String 两种情况下 String 调用 change() 都是不变的，还是输出  abc
//        String ddd = new String("abc");
        String ddd = "abc";

        char[] chars = new char[]{'a','b','c'};
        change(ddd,chars);
        System.out.println("str = "+ddd+ " chars = "+ Arrays.toString(chars));

        // 对象情况下  调用 change() 是变的
        Person aPersion = new Person("oldName");
        System.out.println("before change: aPersion = "+aPersion);
        change_obj(aPersion);
        System.out.println("after change: aPersion = "+aPersion);
    }

    static void change(String ddd, char[] chars){
        ddd = "gbc";
        chars[0] = 'g';
    }

    static void change_obj(Person ddd){
        ddd.name = "newName";
    }

    static class A {

        static {
            System.out.println("static a");
        }

        public A(){
           System.out.println("a");
        }
    }

    static class B extends A {
        static {
            System.out.println("static b");
        }

        /**
         * java 子类 构造器 父类
         *
         * https://www.cnblogs.com/chenpi/p/5486096.html
         * 当一个类继承了某个类时，在子类的构造方法里，super()必须先被调用；如果你没有写，编译器会自动调用super()方法，即调用了父类的构造方法；
         * 这并不是创建了两个对象，其实只有一个子类Sub对象；之所以需要调用父类的构造方法是因为在父类中，可能存在私有属性需要在其构造方法内初始化；
         */
        public B(){
            System.out.println("b");
        }

    }

    static class Person {
        public String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
