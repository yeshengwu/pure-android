package com.example.mylibrary.algo;

import java.util.Arrays;

/**
 * Created by shidu on 19/4/8.
 * https://github.com/wangzheng0822/algo/blob/master/java/11_sorts/Sorts.java
 */

public class Sorts {

    // 选择排序，a表示数组，n表示数组大小
    public static void selectionSort(int[] a, int n) {
        if (n <= 1) return;

        for (int i = 0; i < n - 1; ++i) {
            // 查找最小值
            int minIndex = i;
            for (int j = i + 1; j < n; ++j) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }

            // 交换
            int tmp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = tmp;
        }
    }

    /**
     * 排序题技巧：
     * 1，记住常用的真实排序数据，然后手动根据相关算法纸上运行一遍，如 {4, 5, 6, 3, 1, 2}
     * 2，排序运行时从大到小走一遍，再换成从小到大再走一遍，加深理解。
     * 3，抄一遍具体某个算法，再默写一遍后跑一下看是否能正确得到结果，再跟原来答案对比看哪里写漏了。
     * 4，纸上画排序过程图，使用 三角形标识外层循环i，向上箭头标识内层循环j （部分有用）
     *
     * @param args
     */
    public static void main(String[] args) {
//        int[] a = {4, 3, 6, 2};
        int[] a = {4, 5, 6, 3, 1, 2};
        Sorts.insertionSort(a, a.length);
        Sorts.selectionSort(a, a.length);
        Sorts.bubbleSortEvan(a);
        System.out.println(Arrays.toString(a));
    }

    // 插入排序，a表示数组，n表示数组大小
    public static void insertionSort(int[] a, int n) {
        if (n <= 1) return;  //4,5,6,1,3,2

        for (int i = 1; i < n; ++i) {
            int value = a[i];
            int j = i - 1;
            // 查找要插入的位置并移动数据
            for (; j >= 0; --j) {
                if (a[j] > value) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            System.out.println("j = " + j);
            a[j + 1] = value;
        }
    }


    public static void bubbleSortEvan(int[] a) {
        // 从大到小： 实际执行发现：第一趟 冒出1，第二趟冒出 5. 不是冒泡算法。
        // 那这是什么算法？
//        for (int i = 0; i < a.length; i++) {
//            for (int j = 0; j < a.length; j++) {
//                if (a[i] > a[j]) {
//                    int temp = a[i];
//                    a[i] = a[j];
//                    a[j] = temp;
//                }
//            }
//        }

        // 从小到大 冒泡
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

}
