package com.xxx.problem.number.easy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImplementStrStrMethod {
    /**
     * Implement strStr().
     * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
     * <p>
     * Example 1:
     * Input: haystack = "hello", needle = "ll"
     * Output: 2
     * <p>
     * Example 2:
     * Input: haystack = "aaaaa", needle = "bba"
     * Output: -1
     * <p>
     * Clarification:
     * What should we return when needle is an empty string? This is a great question to ask during an interview.
     * For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().
     * <p>
     * 假設輸入都不為null
     * 先比對到needle的第一個byte與haystack字串 然後才開始比對接下來的byte
     */
    public int strStr(String haystack, String needle) {
//        if(haystack == null || needle == null) return -1;
        byte[] bytes = haystack.getBytes();
        byte[] bytes2 = needle.getBytes();
        if (bytes2.length == 0) {
            return 0;
        }
        if (bytes.length < bytes2.length) {
            return -1;
        }
        int length = bytes.length - bytes2.length;
        byte first = bytes2[0];
        for (int i = 0; i <= length; i++) {
            if (bytes[i] != first) {
                while (++i <= length && bytes[i] != first) ;
            }
            if (i > length) {
                return -1;
            }
            int end = bytes2.length - 1;
            for (int j = 0, k = i; j < bytes2.length && bytes[k] == bytes2[j]; j++, k++) {
                if (j == end) return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int index = new ImplementStrStrMethod().strStr("hleello", "ll");
        log.info("{}", index);
    }
}
