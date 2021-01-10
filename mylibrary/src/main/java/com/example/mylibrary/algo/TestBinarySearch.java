package com.example.mylibrary.algo;

public class TestBinarySearch {

    private static int i = 1;
    private static volatile int a = 1;

    public static void main(String args[]) {
        i = 11;
        a = 111;
        System.out.println("result = a = " + a);

        int[] a = {1, 2, 3, 4, 5, 6};
//        int index = TestBinarySearch.commonBinarySearchEvan(a, 12);
        int index = TestBinarySearch.recursionBinarySearchEvan(a,  6,0,a.length - 1);
//        int index = TestBinarySearch.recursionBinarySearch(a, 4,0,a.length - 1);
        System.out.println("result index = " + index);
    }

    /**
     * 不使用递归的二分查找
     * title:commonBinarySearch
     *
     * @param arr
     * @param key
     * @return 关键字位置
     */
    public static int commonBinarySearchEvan(int[] arr, int key) {
        int length = arr.length;
        int low = 0;
        int middle;
        int high = length - 1;

        while (low <= high) {
            middle = (high + low) / 2;
            if (arr[middle] > key) {
                high = middle - 1;
            } else if (arr[middle] < key) {
                low = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }

    /**
     * 递归
     * @param arr
     * @param key
     * @param low
     * @param high
     * @return
     */
    public static int recursionBinarySearchEvan(int[] arr, int key, int low, int high) {
        if (low> high) {
            return -1;
        }

        int middle = (high + low) / 2;
        if (arr[middle] > key) {
            high = middle - 1;
            return recursionBinarySearchEvan(arr,key,low,high);
        } else if (arr[middle] < key) {
            low = middle + 1;
            return recursionBinarySearchEvan(arr,key,low,high);
        } else {
            return middle;
        }
    }

    /**
     * 不使用递归的二分查找
     * title:commonBinarySearch
     *
     * @param arr
     * @param key
     * @return 关键字位置
     */
    public static int commonBinarySearch(int[] arr, int key) {
        int low = 0;
        int high = arr.length - 1;
        int middle = 0;         //定义middle

        if (key < arr[low] || key > arr[high] || low > high) {
            return -1;
        }

        while (low <= high) {
            middle = (low + high) / 2;
            if (arr[middle] > key) {
                //比关键字大则关键字在左区域
                high = middle - 1;
            } else if (arr[middle] < key) {
                //比关键字小则关键字在右区域
                low = middle + 1;
            } else {
                return middle;
            }
        }

        return -1;      //最后仍然没有找到，则返回-1
    }

    /**
     * 使用递归的二分查找
     * title:recursionBinarySearch
     *
     * @param arr 有序数组
     * @param key 待查找关键字
     * @return 找到的位置
     */
    public static int recursionBinarySearch(int[] arr, int key, int low, int high) {

        if (key < arr[low] || key > arr[high] || low > high) {
            return -1;
        }

        int middle = (low + high) / 2;          //初始中间位置
        if (arr[middle] > key) {
            //比关键字大则关键字在左区域
            return recursionBinarySearch(arr, key, low, middle - 1);
        } else if (arr[middle] < key) {
            //比关键字小则关键字在右区域
            return recursionBinarySearch(arr, key, middle + 1, high);
        } else {
            return middle;
        }

    }
}
