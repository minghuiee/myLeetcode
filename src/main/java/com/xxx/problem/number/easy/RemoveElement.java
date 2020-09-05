package com.xxx.problem.number.easy;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class RemoveElement {
    /**
     * Given an array nums and a value val, remove all instances of that value in-place and return the new length.
     * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
     * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
     * <p>
     * Example 1:
     * Given nums = [3,2,2,3], val = 3,
     * Your function should return length = 2, with the first two elements of nums being 2.
     * It doesn't matter what you leave beyond the returned length.
     * <p>
     * Example 2:
     * Given nums = [0,1,2,2,3,0,4,2], val = 2,
     * Your function should return length = 5, with the first five elements of nums containing 0, 1, 3, 0, and 4.
     * Note that the order of those five elements can be arbitrary.
     * It doesn't matter what values are set beyond the returned length.
     *
     * example 1 (val = 3)
     * 3 -> (2) -> 2 -> 3
     * (2) -> 2 -> (2) -> 3
     * 2 -> (2) -> 2 -> 3
     * array[0] ~ array[1] -> [2,2]
     *
     * example 2 (val = 2)
     * (0) -> 1 -> 2 -> 2 -> 3 -> 0 -> 4 -> 2
     * (0) -> (1) -> 2 -> 2 -> 3 -> 0 -> 4 -> 2
     * 0 -> (1) -> 2 -> 2 -> (3) -> 0 -> 4 -> 2
     * 0 -> 1 -> (3) -> 2 -> 3 -> (0) -> 4 -> 2
     * 0 -> 1 -> 3 -> (0) -> 3 -> 0 -> (4) -> 2
     * 0 -> 1 -> 3 -> 0 -> (4) -> 0 -> 4 -> 2
     * array[0] ~ array[4] -> [0,1,3,0,4]
     *
     */
    public int removeElement(int[] nums, int val) {
        int i = 0;
        int n = nums.length;
        while (i < n) {
            if (nums[i] == val) {
                nums[i] = nums[n - 1];
                // reduce array size by one
                n--;
            } else {
                i++;
            }
        }
        return n;
    }

    /**
     * 要刪除指定array element，所以把不是此element向陣列num[0]集中，然後取出需要的部分，但陣列只能用一次
     * 假設nums必定有element
     */
    public int removeElementByMyResult(int[] nums, int val) {
//        if (nums.length == 0) return 0;
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (val == nums[i]) {
                continue;
            }
            nums[j] = nums[i];
            j++;
        }
        return j;
    }

    public static void main(String[] args) {
//        int[] array = new int[]{3,2,2,3};
        int[] array = new int[]{0, 1, 2, 2, 3, 0, 4, 2};
        int endPosition = new RemoveElement().removeElementByMyResult(array, 2);
        log.info("{}", Arrays.copyOfRange(array, 0, endPosition));
    }
}
