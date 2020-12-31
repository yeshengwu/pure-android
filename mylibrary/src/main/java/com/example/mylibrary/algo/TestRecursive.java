package com.example.mylibrary.algo;

public class TestRecursive {

    public static void main(String args[]) {
        int result = TestRecursive.f(6);
        System.out.println("result = " + result);
    }

    private static int f(int n) {
        System.out.println("IN f. n = " + n);
        if (n <= 2) {
            System.out.println("IN f. n <= 2 return n");
            return n;
        }

        System.out.println("IN f. n >2. invoke sub before " + (n-1));
        int childReturn = f(n-1);
        int result = n * childReturn;
        System.out.println("IN f. n >2. invoke sub after n = " + n+" return result = "+childReturn + " compute result = "+result);
        return result;
    }
}
