package com.example.mylibrary.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 刷题技巧：
 * 核心思想：（动手做而不是思考懒惰就看答案，否则记不住不理解还是浪费时间而已）
 * 一题多解，多题一解
 * 过程：
 * 1，做一题先切题4法(覃超法)
 * 2：先暴力解一把（代码跑成功确保理解了题意）。
 * 3，优化解法时，实在想不出，先看题解的思路，不要马上看答案，不要马上看答案。（先根据思路，想当于面试官提示了思路了，
 * 那么继续跟着思路写代码或者思考，这样跟着思路动手写比看答案效率高理解得劳）
 * 4，实在写不出来，或者写了一半，再看答案一部分（注意，是一部分是为了提示思路），有了提示又继续写。而不是全部答案搬来。
 * 2021-3-22
 */
public class TestLeetCode {
    public static void main(String[] args) {
//        int[] a = {3,4,5,1,2};
//        int[] a = {2,2,2,0,1};
//        int[] a = {3,3};
        int[] a = {0, 4, 3, 0};
//        int minInArray = TestLeetCode.minArray(a);
//        int minInArray = TestLeetCode.minArray_2(a);
//        System.out.println("minInArray = " + minInArray);
//        int[] twoSumTables = TestLeetCode.twoSum_1(a,6);
        int[] twoSumTables = TestLeetCode.twoSum_2(a, 5);
        System.out.println("twoSum = " + Arrays.toString(twoSumTables));

//        int[] inter_1 = {1, 6, 8};
//        int[] inter_2 = {5, 7, 8};
//        int[] inter_1 = {1, 6, 8,9,10,12,15};
//        int[] inter_2 = {5, 7, 8,10,13};
//        int[] inter_1 = {1, 6, 8,9};
//        int[] inter_2 = {5, 7, 8,10,13};
//        int[] inter_1 = {1, 2, 2, 1};
//        int[] inter_2 = {2, 2};
        int[] inter_1 = {1, 2, 2, 1};
        int[] inter_2 = {1, 2};
//        Integer[] intersection = TestLeetCode.intersection_1(inter_1, inter_2);
        int[] intersection = TestLeetCode.intersection_4(inter_1, inter_2);
        System.out.println("intersection = " + Arrays.toString(intersection));

//        int[] merge_1 = {1, 3, 5, 7, 0, 0, 0};
//        int[] merge_2 = {2, 4, 6};
        int[] merge_1 = {1, 2, 3, 0, 0, 0};
        int[] merge_2 = {2, 5, 6};
//        int[] merge_1 = {1, 0, 0, 0};
//        int[] merge_2 = {};
        int[] merge = TestLeetCode.merge(merge_1, 3, merge_2, merge_2.length);
        System.out.println("merge = " + Arrays.toString(merge));
        System.out.println("merge result nums_1 = " + Arrays.toString(merge_1));

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
            } else {
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
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum_1(int[] nums, int target) {
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
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
     * target -target hashMap 解法
     * https://leetcode-cn.com/problems/two-sum/
     *
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
     * target -target hashMap 解法
     * https://leetcode-cn.com/problems/two-sum/
     *
     * evan通俗解释： 找剩下部分，就像找女朋友。
     * 有个女朋友池，如果张三来了，看看女朋友池子里有没有符合他要求的女朋友，有就配对了，没有就把自己放入池子等待
     * 别人来配对他。
     *
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

    /**
     * https://leetcode-cn.com/problems/intersection-of-two-arrays/
     * 349
     * 暴力
     * 时间复杂度： O(n*m)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static Integer[] intersection_1(int[] nums1, int[] nums2) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums1.length; ++i) {
            for (int j = 0; j < nums2.length; ++j) {
                if (nums2[j] == nums1[i] && !result.contains(nums1[i])) {
                    result.add(nums1[i]);
                }
            }
        }
        Integer[] strings = new Integer[result.size()];
        result.toArray(strings);
        return strings;
    }

    /**
     * 使用哈希集合存储元素,在 O(1) 的时间内判断一个元素是否在集合中
     * O(m+n)O(m+n)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static Integer[] intersection_2(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();

        return null;
    }

    /**
     * 如果两个数组是有序的，则可以使用双指针的方法得到两个数组的交集
     * 思想一致：双指针
     * 时间6ms
     * <p>
     * 时间复杂度分析：
     * 空间复杂度分析：
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersection_3(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> result = new ArrayList<>();
        int p = 0;
        int q = 0;
        while (p < nums1.length && q < nums2.length) {
            if (nums1[p] < nums2[q]) {
                p++;
            } else if (nums1[p] > nums2[q]) {
                q++;
            } else {
                if (!result.contains(nums1[p])) {
                    result.add(nums1[p]);
                }
                p++;
                q++;
            }
        }

        int[] strings = new int[result.size()];
        for (int i = 0; i < result.size(); ++i) {
            Integer item = result.get(i);
            strings[i] = item;
        }
        return strings;
    }

    /**
     * 思想一致：双指针
     * 官方： 时间1ms
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersection_4(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int length1 = nums1.length, length2 = nums2.length;
        int[] intersection = new int[length1 + length2];
        int index = 0, index1 = 0, index2 = 0;
        while (index1 < length1 && index2 < length2) {
            int num1 = nums1[index1], num2 = nums2[index2];
            if (num1 == num2) {
                // 保证加入元素的唯一性
                if (index == 0 || num1 != intersection[index - 1]) {
                    intersection[index++] = num1;
                }
                index1++;
                index2++;
            } else if (num1 < num2) {
                index1++;
            } else {
                index2++;
            }
        }

        return Arrays.copyOfRange(intersection, 0, index);
    }

    /**
     * https://leetcode-cn.com/problems/merge-sorted-array/solution/he-bing-liang-ge-you-xu-shu-zu-by-leetcode/
     * 88. 合并两个有序数组
     * 解法二： 双指针 / 从前往后
     * <p>
     * 示例 1：
     * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * 输出：[1,2,2,3,5,6]
     * <p>
     * 示例 2：
     * 输入：nums1 = [1], m = 1, nums2 = [], n = 0
     * 输出：[1]
     *
     * @param nums1
     * @param m     它是 nums1数组 非0 元素的个数
     * @param nums2
     * @param n
     * @return
     */
    // 1 3 5 7 0 0 0
    // 2 4 6

    // 1, 2, 3, 0, 0, 0
    // 2, 5, 6
    public static int[] merge(int[] nums1, int m, int[] nums2, int n) {
        // copy nums1 中的除0 外的真正干货。
        int[] nums1_copy = new int[m];
        for (int x = 0; x < nums1_copy.length; x++) {
            nums1_copy[x] = nums1[x];
        }

        int length1 = m, length2 = n;
        List<Integer> result = new ArrayList<>();
        int index1 = 0, index2 = 0;
        while (index1 < length1 && index2 < length2) {
            int num1 = nums1_copy[index1], num2 = nums2[index2];
            if (num1 == num2) {
                result.add(num2);
                index2++;
            } else if (num1 < num2) {
                result.add(num1); // 小的加入
                index1++;
            } else {
                result.add(num2);// 小的加入
                index2++;
            }
        }

        // if there are still elements to add
        // 剩下部分拿下
        for (int i = index1; i < length1; i++) {
            result.add(nums1[i]);
        }
        // 剩下部分拿下
        for (int i = index2; i < length2; i++) {
            result.add(nums2[i]);
        }

        // 输出写入 nums1.
        for (int i = 0; i < result.size(); ++i) {
            Integer item = result.get(i);
            nums1[i] = item;
            System.out.println("nums1 item = " + item);
        }

        // 测试程序，输出给外部。
        int[] strings = new int[result.size()];
        for (int i = 0; i < result.size(); ++i) {
            Integer item = result.get(i);
            strings[i] = item;
        }
        return strings;
    }

}
