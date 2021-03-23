package com.example.mylibrary.testlaunage;

/**
 * 从牛客准备的，非自己面的题目
 */
public class TestInterviewFutu {
    public static void main(String[] args) {
        String sub = "a";
        String big = "babcab";
        System.out.println(subStrIndexOf(sub, big));
    }

    public static int subStrIndexOf(String sub, String big) {
//        if (TextUtils.isEmpty(sub)) {
//            return -1;
//        }
//        if (TextUtils.isEmpty(big)) {
//            return -1;
//        }
        if (sub == null || sub.length() == 0) {
            return -1;
        }
        if (big == null || big.length() == 0) {
            return -1;
        }

        return big.indexOf(sub);
    }
}

