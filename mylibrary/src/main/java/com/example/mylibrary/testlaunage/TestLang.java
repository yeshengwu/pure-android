package com.example.mylibrary.testlaunage;

public class TestLang {

    public static void main(String[] args) {
        testBoolWithMethod(false);
    }

    public static void testBoolWithMethod(boolean result){
        if (result && onTouchEvent()) {  // 如果 result 为 false. 那么 onTouchEvent() 这个方法不会执行。
            System.out.println("IN &&");
        } else {
            System.out.println("else &&");
        }
    }

    private static boolean onTouchEvent() {
        System.out.println("IN onTouchEvent");
        return false;
    }
}
