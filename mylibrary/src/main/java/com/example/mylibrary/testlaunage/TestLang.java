package com.example.mylibrary.testlaunage;


import java.io.IOException;
import java.sql.SQLException;

public class TestLang {

    public static void main(String[] args) {
        testBoolWithMethod(false);

        //  unchecked exception: RuntimeException 发生逻辑问题后开发者自己责任选择处理或者不处理， 编译器不强制
        testThrow();

        // checked exception: Java编译器强制要求处理
        try {
            testThrow3();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            testThrow4();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void testBoolWithMethod(boolean result) {
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

    private static void testThrow() throws ArithmeticException {
    }

    private static void testThrow3() throws IOException {
    }

    private static void testThrow4() throws SQLException {
    }

    public void testOverload(String xxx) {

    }

//    public int testOverload(String xxx) {
//        return 1;
//    }
}
