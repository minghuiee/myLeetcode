package com.xxx.problem.number.easy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Palindrome {
    /**
     * Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.
     * Example 1:
     * Input: 121
     * Output: true
     *
     * Example 2:
     * Input: -121
     * Output: false
     * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
     *
     * Example 3:
     * Input: 10
     * Output: false
     * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
     *
     */
    public boolean isPalindrome(int x) {
        if(x < 0) return false;
        int y = x;
        int reverse = 0;
        while (x > 9) {
            reverse = reverse * 10 + x % 10;
            x /= 10;
        }
        if (reverse > Integer.MAX_VALUE / 10) return false;
        return reverse * 10 + x == y;
    }

    public static void main(String[] args) {
        new Palindrome().isPalindrome(123404321);
    }
}
