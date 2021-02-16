package com.example.mylibrary.algo;

public class TestLeetCode {
    public static void main(String[] args) {
//        int[] a = {3,4,5,1,2};
//        int[] a = {2,2,2,0,1};
        int[] a = {4,3,4,4,4};
//        int minInArray = TestLeetCode.minArray(a);
        int minInArray = TestLeetCode.minArray_2(a);
        System.out.println("minInArray = " + minInArray);
    }

    /**
     * 链接：https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。
     * 例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。  
     *
     * 输入：[3,4,5,1,2]
     * 输出：1
     *
     * 输入：[2,2,2,0,1]
     * 输出：0
     *
     * @param numbers
     * @return
     *
     */
    /**
     * 暴力解法：遍历数组，但是找到了比前一位小的数字，就是最小值
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     */
    public static int minArray(int[] numbers) {
//        for (int i = 0;i<numbers.length;i++) {
//            if (numbers[i]<=numbers[i+1]){
//
//            } else {
//                System.out.println("break i = " + i);
//                return numbers[i+1];
//            }
//        }
//
//        return -1;


        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < numbers[i - 1]) {
                return numbers[i];
            }
        }
        return numbers[0];
    }

    /**
     * 二分查找
     *
     * @param numbers
     * @return
     */
    public static int minArray_2(int[] numbers) {
        int length = numbers.length;
        int left = 0;
        int middle;
        int right = length - 1;

        while (left < right) {
//            middle = (right + left) / 2;
            middle = left + (right - left) / 2;
            if (numbers[middle] > numbers[right]) {
                left = middle + 1;
            } else if (numbers[middle] < numbers[right]) {
                right = middle;
            }
            else {
                right--; // 去重
            }
        }
        // left = right,所以返回  numbers[left] numbers[right] 都可以
        return numbers[right];
    }
}
