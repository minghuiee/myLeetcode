package com.xxx.problem.number.easy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongestCommonPrefix {
    /**
     * Write a function to find the longest common prefix string amongst an array of strings.
     * If there is no common prefix, return an empty string "".
     * Example 1:
     * Input: ["flower","flow","flight"]
     * Output: "fl"
     * <p>
     * Example 2:
     * Input: ["dog","racecar","car"]
     * Output: ""
     * Explanation: There is no common prefix among the input strings.
     * <p>
     * Note:
     * All given inputs are in lowercase letters a-z.
     * <p>
     * Algorithm type:
     * Recursive algorithms
     * Dynamic programming algorithm
     * Backtracking algorithm
     * Divide and conquer algorithm
     * Greedy algorithm
     * Brute Force algorithm
     * Randomized algorithm
     * <p>
     * Searching Algorithms type
     * Linear Search
     * Binary Search
     * Jump Search
     * Interpolation Search
     * Exponential Search
     * Sublist Search (Search a linked list in another list)
     * Fibonacci Search
     * The Ubiquitous Binary Search
     * Recursive program to linearly search an element in a given array
     * Recursive function to do substring search
     * Unbounded Binary Search Example (Find the point where a monotonically increasing function becomes positive first time)
     * <p>
     * 1.Horizontal scanning
     * 2.Vertical scanning
     * <p>
     * 由於是找最長前綴，因此限制以上的search algorithms並不適用
     * <p>
     * {leets,leetcode,leet,leeds}
     * 1. leets,leetcode -> leet
     * 2. leet,leet -> leet
     * 3. leet,leed -> lee
     * 公式:LCP(S1​…Sn​)=LCP(LCP(LCP(S1​,S2​),S3​),…Sn​)
     * time complexity:O(S)
     * space complexity:O(1),only used constant extra space
     */
    public String LCPByHorizontalScanning(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++)
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        return prefix;
    }

    /**
     * {leets,leetcode,leet,leeds}
     * 1. leets,leetcode,leet,leeds -> l
     * 2. leets,leetcode,leet,leeds -> le
     * 3. leets,leetcode,leet,leeds -> lee
     * 4. leets,leetcode,leet,leeds -> x
     * time complexity: O(S)
     * space complexity : O(1). only used constant extra space.
     */
    public String LCPByVerticalScanning(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }

    /**
     * Time Limit Exceeded
     */
    public String LCPByMyResult(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        int min = 0;
        int max = strs[0].length();
        int divideNumber;
        String s;
        while (true) {
            divideNumber = (min + max) / 2;
            s = strs[0].substring(0, divideNumber);
            for (int i = 1; i < strs.length; i++) {
                if (!strs[i].startsWith(s)) {
                    if(divideNumber == 1) return "";
                    if(min == divideNumber - 1) return strs[0].substring(0,divideNumber - 1);
                    if(min == max) return strs[0].substring(0,max - 1);
                    max = divideNumber;
                    break;
                }
                if (i == strs.length - 1) {
                    if(min == max) return strs[0].substring(0,max);
                    if(max == divideNumber + 1) {
                        min = max;
                    } else {
                        min = divideNumber;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        String[] strs = new String[]{"leetsabcde", "leetcode", "leet", "leeds"};
        String result = new LongestCommonPrefix().LCPByMyResult(strs);
        log.info(result);
    }
}
