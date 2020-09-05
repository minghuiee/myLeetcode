package com.xxx.problem.number.easy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchInsertPostion {
    /**
     * Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.
     * You may assume no duplicates in the array.
     * 已知陣列中無重複值
     * 已知陣列由小到大
     * <p>
     * Example 1:
     * Input: [1,3,5,6], 5
     * Output: 2
     * <p>
     * Example 2:
     * Input: [1,3,5,6], 2
     * Output: 1
     * <p>
     * Example 3:
     * Input: [1,3,5,6], 7
     * Output: 4
     * <p>
     * Example 4:
     * Input: [1,3,5,6], 0
     * Output: 0
     */
    public int searchInsert(int[] nums, int target) {
        int prev = nums[0];
        if (prev >= target) {
            return 0;
        }
        int i = 1;
        for (; i < nums.length; i++) {
            if (prev < target && target <= nums[i]) {
                return i;
            }
            prev = nums[i];
        }
        return i;
    }

    public static void main(String[] args) {
        int[] array = new int[]{1, 3, 5, 6, 9, 10, 15, 18, 19, 20, 21, 23, 25, 28, 31, 32};
        int i = new SearchInsertPostion().searchInsert(array, 22);
        log.info("{}", i);

//        final Runtime rt = Runtime.getRuntime();
//        while (true) {
//            System.gc();
//            System.out.println(rt.totalMemory() + " " + rt.freeMemory());
//        }
    }
}
