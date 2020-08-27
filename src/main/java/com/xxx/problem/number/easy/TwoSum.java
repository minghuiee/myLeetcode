package com.xxx.problem.number.easy;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TwoSum implements Runnable {

    /**
     * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
     * You may assume that each input would have exactly one solution, and you may not use the same element twice.
     * Example:
     * Given nums = [2, 7, 11, 15], target = 9,
     * Because nums[0] + nums[1] = 2 + 7 = 9,
     * return [0, 1].
     * Complexity Analysis: 如下程式時間複雜度為O(n^2)效率低
     * 可利用map將時間複雜度改為O(n)
     * Space complexity: ?
     * 若數值找不到可直接丟出exception可省略丟出null的麻煩
     * @param nums 分析數組
     * @param target 目標
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        for(int i = 0;i<nums.length - 1;i++){
            for(int j = i + 1;j < nums.length;j++){
                if(nums[i] == target - nums[j] ) {
                    return new int[]{i,j};
                }
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /**
     * Time complexity:O(1) ~ O(n)
     * Space complexity O(n)
     * @param nums 分析數組
     * @param target 目標
     * @return
     */
    public int[] twoSumByTwoPassHash(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[] { i, map.get(complement) };
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    /**
     * Time complexity:O(n)
     * Space complexity O(n)
     * @param nums 分析數組
     * @param target 目標
     * @return
     */
    public int[] twoSumByOnePassHash(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    //單執行緒，線程不安全
    public int[] twoSumMyResult(int[] nums,int target){
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                return new int[] { map.get(nums[i]), i };
            }
            map.put(target - nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main(String[] args) {
        log.info("{}",new TwoSum().twoSumMyResult(new int[]{2,7,11,13},24));

    }

    public void run() {

    }
}
