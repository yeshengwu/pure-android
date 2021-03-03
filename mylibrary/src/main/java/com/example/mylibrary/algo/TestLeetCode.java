package com.example.mylibrary.algo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestLeetCode {
    public static void main(String[] args) {
//        int[] a = {3,4,5,1,2};
//        int[] a = {2,2,2,0,1};
//        int[] a = {3,3};
        int[] a = {0,4,3,0};
//        int minInArray = TestLeetCode.minArray(a);
//        int minInArray = TestLeetCode.minArray_2(a);
//        System.out.println("minInArray = " + minInArray);
//        int[] twoSumTables = TestLeetCode.twoSum_1(a,6);
        int[] twoSumTables = TestLeetCode.twoSum_2(a,0);
        System.out.println("twoSum = " + Arrays.toString(twoSumTables));
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



    /**
     * 万事开头难的 leetcode 第一题，
     * 暴力解法
     * https://leetcode-cn.com/problems/two-sum/
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum_1(int[] nums, int target) {
        int[] res = new int[2];
        for(int i = 0;i<nums.length;i++){
            for (int j = i+1;j<nums.length;j++){
                if (nums[i]+nums[j] == target) {
                    res[0] = i;
                    res[1] = j;
                    return res;
                }

            }
        }
        return res;
    }

    /**
     * 万事开头难的 leetcode 第一题，
     *  target -target hashMap 解法
     * https://leetcode-cn.com/problems/two-sum/
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum_2_evan(int[] nums, int target) {
        int[] res = new int[2];
        Map<Integer, Integer> targetLeftMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int left = target - nums[i];
            targetLeftMap.put(i, left);
            for (int j = i + 1; j < nums.length; j++) {
                if (targetLeftMap.containsValue(nums[j])) {
                    res[0] = i;
                    res[1] = j;
                    return res;
                }
            }
        }
        return res;
    }

    /**
     * 万事开头难的 leetcode 第一题，
     *  target -target hashMap 解法
     * https://leetcode-cn.com/problems/two-sum/
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum_2(int[] nums, int target) {
        int[] res = new int[2];
        Map<Integer, Integer> hashtable = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; ++i) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return res;
    }

}
