package com.example.mylibrary.algo;

public class TestBinarySearch {

    private static  int i = 1;
    private static volatile int a = 1;

    public static void main(String args[]) {
        i = 11;
        a = 111;
        System.out.println("result = a = "+a);

        TestBinarySearch.binarySearch();
    }

    private static void binarySearch() {

    }
}
