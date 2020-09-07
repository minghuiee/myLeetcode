package com.xxx.problem.number.easy;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 * <p>
 * Symbol       Value
 * I                  1
 * V                 5
 * X                 10
 * L                 50
 * C                 100
 * D                 500
 * M                1000
 * <p>
 * For example, two is written as II in Roman numeral, just two one's added together. Twelve is written as, XII, which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.
 * <p>
 * Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:
 * <p>
 * I can be placed before V (5) and X (10) to make 4 and 9.
 * X can be placed before L (50) and C (100) to make 40 and 90.
 * C can be placed before D (500) and M (1000) to make 400 and 900.
 * <p>
 * Given a roman numeral, convert it to an integer. Input is guaranteed to be within the range from 1 to 3999.
 * <p>
 * Example 1:
 * Input: "III"
 * Output: 3
 * <p>
 * Example 2:
 * Input: "IV"
 * Output: 4
 * <p>
 * Example 3:
 * Input: "IX"
 * Output: 9
 * <p>
 * Example 4:
 * Input: "LVIII"
 * Output: 58
 * Explanation: L = 50, V= 5, III = 3.
 * <p>
 * Example 5:
 * Input: "MCMXCIV"
 * Output: 1994
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 */
@Slf4j
public class RomanNumeralToInteger {

    /**
     * result:
     * Benchmark                                                              (c)                 Mode  Cnt    Score                   Error               Units
     * RomanNumeralToInteger.MyState2.romanToInt1   MDCXCV    thrpt    20      28703345.224 ±  244189.252    ops/s
     * RomanNumeralToInteger.MyState2.romanToInt1   MCMXCIV  thrpt    20      26027252.242 ±  625143.362    ops/s
     * RomanNumeralToInteger.MyState2.romanToInt1   LVIII            thrpt    20      32537054.111 ±  1676159.372  ops/s
     * RomanNumeralToInteger.MyState2.romanToInt2   MDCXCV    thrpt    20      29630741.021 ±  3152888.603  ops/s
     * RomanNumeralToInteger.MyState2.romanToInt2   MCMXCIV  thrpt    20      31949880.041 ±  360527.195    ops/s
     * RomanNumeralToInteger.MyState2.romanToInt2   LVIII            thrpt    20      39329672.282 ±  431355.537    ops/s
     */
    @State(Scope.Thread)
    @BenchmarkMode(Mode.Throughput) //測試模式
    @Warmup(iterations = 3) //遇熱輪數
    @Measurement(iterations = 20, timeUnit = TimeUnit.MICROSECONDS) //測試輪數
    @Threads(1) //線程數
    @Fork(1) //進程數
    public static class RomanNumeralToIntegerState {
        @Param({"MDCXCV", "MCMXCIV", "LVIII"})
        public String c;

        @Benchmark
        public void romanToInt1(RomanNumeralToIntegerState state, Blackhole blackhole) {
            int result = new RomanNumeralToInteger().romanToIntByMyResult(state.c);
            blackhole.consume(result);
        }

        @Benchmark
        public void romanToInt2(RomanNumeralToIntegerState state, Blackhole blackhole) {
            int result = new RomanNumeralToInteger().romanToIntByMyResult2(state.c);
            blackhole.consume(result);
        }
    }

    /**
     * 假設必定為羅馬文字格式，並輸入字串不為空
     * memory:39.5~39.6MB
     * speed:slow
     */
    public int romanToIntByMyResult(String s) {
//        if (s == null) return 0;
        int accumulate = 0;
//        int repeat = 0;
        char current = s.charAt(0);
        char prev = current;
        char pprev = prev;
        for (int i = 1; i < s.length(); i++) {
            current = s.charAt(i);
//            if(prev == current) repeat++;
//            else repeat = 0;
//            if(repeat == 3) continue;
            if (noNeedCalc(pprev, prev)) {
                pprev = prev;
                prev = current;
                continue;
            }
            accumulate += analysis(prev, current);
            pprev = prev;
            prev = current;
        }
        if (noNeedCalc(pprev, prev)) {
            return accumulate;
        }
        return accumulate + analysis(current);
    }

    /**
     * memory:42~42.5MB
     * speed:fast
     */
    public int romanToIntByMyResult2(String s) {
        int accumulate = 0;
        char current = s.charAt(0);
        char prev = current;
        int length = s.length();
        for (int i = 1; i < length; i++) {
            current = s.charAt(i);
            accumulate += analysis(prev, current);
            if (noNeedCalc(prev, current)) {
                i++;
                if (i == length) {
                    return accumulate;
                }
                if (i == length - 1) {
                    return accumulate + analysis(s.charAt(i));
                }
                prev = s.charAt(i);
                continue;
            }
            prev = current;
        }
        return accumulate + analysis(current);
    }

    /**
     * memory:39.3~39.4MB
     * speed:fast
     */
    public int romanToIntByMyResult3(String s) {
        int accumulate = 0;
        if (s.contains("IV")) {
            s = replaceOnce(s, "IV");
            accumulate += 4;
        }
        if (s.contains("IX")) {
            s = replaceOnce(s, "IX");
            accumulate += 9;
        }
        if (s.contains("XL")) {
            s = replaceOnce(s, "XL");
            accumulate += 40;
        }
        if (s.contains("XC")) {
            s = replaceOnce(s, "XC");
            accumulate += 90;
        }
        if (s.contains("CD")) {
            s = replaceOnce(s, "CD");
            accumulate += 400;
        }
        if (s.contains("CM")) {
            s = replaceOnce(s, "CM");
            accumulate += 900;
        }
//        for (int i = 0; i < s.length(); i++) {
//            accumulate += analysis(s.charAt(i));
//        }
        //memory:39.8MB
        //spped:slow
        //leedcode無此函式:ArrayUtils.toObject(charArray)
//        accumulate += Arrays.stream(charArrayToCharacterArray(s.toCharArray())).map(this::analysis).reduce(0, Integer::sum);
        //memory:39.6MB
        //spped:fast
        for (char c : s.toCharArray()) {
            accumulate += analysis(c);
        }
        //memory:40.2MB
        //spped:slow
//        accumulate += s.chars().map(c -> analysis((char) c)).sum();
        return accumulate;
    }

    //羅馬數字組合 searchString只能是IV,IX,XL,XC,CD,CM
    //假設text,searchString都不為空
    //假設為單線程
    private String replaceOnce(String text, String searchString) {
        int length = text.length();
//        if (text == null || text.length() == 0 || searchString == null || searchString.length() == 0) {
//            return text;
//        }
        int index = text.indexOf(searchString);
        if (index == -1) {
            return text;
        }
        return new StringBuilder(length)
                .append(text, 0, index)
                .append(text, index + 2, length)
                .toString();
    }

    //假設array不為空
    private Character[] charArrayToCharacterArray(char[] array) {
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    public boolean noNeedCalc(char pprev, char prev) {
        return (pprev == 'I' && (prev == 'V' || prev == 'X')) ||
                (pprev == 'X' && (prev == 'L' || prev == 'C')) ||
                (pprev == 'C' && (prev == 'D' || prev == 'M'));
    }

    public int romanToInt3(String s) {
        int res = 0;
        int length = s.length();
        int current = analysis(s.charAt(0));
        int next = 0;
        for (int i = 0; i < length; i++) {
            if(i + 1 < length) {
                next = analysis(s.charAt(i + 1));
            }
            if (current < next) {
                res = res - current;
            } else {
                res = res + current;
            }
            current = next;
        }
        return res;
    }

    public int analysis(char current) {
        switch (current) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }

    public int analysis(char prev, char current) {
        switch (prev) {
            case 'I':
                return I(current);
            case 'V':
                return V(current);
            case 'X':
                return X(current);
            case 'L':
                return L(current);
            case 'C':
                return C(current);
            case 'D':
                return D(current);
            case 'M':
                return M(current);
            default:
                return 0;
        }
    }

    public int I(char current) {
        switch (current) {
            case 'V':
                return 4;
            case 'X':
                return 9;
            case 'I':
            default:
                return 1;
        }
    }

    public int V(char current) {
        if (current == 'I') {
            return 5;
        }
        if (current == 'V') {
            return 0;
        }
        return 5;
    }

    public int X(char current) {
        switch (current) {
            case 'L':
                return 40;
            case 'C':
                return 90;
            case 'I':
            case 'V':
            case 'X':
            default:
                return 10;
        }
    }

    public int L(char current) {
        switch (current) {
            case 'L':
                return 0;
            case 'I':
            case 'V':
            case 'X':
            default:
                return 50;
        }
    }

    public int C(char current) {
        switch (current) {
            case 'D':
                return 400;
            case 'M':
                return 900;
            case 'I':
            case 'V':
            case 'X':
            case 'L':
            case 'C':
            default:
                return 100;
        }
    }

    public int D(char current) {
        switch (current) {
            case 'D':
                return 0;
            case 'I':
            case 'V':
            case 'X':
            case 'L':
            case 'C':
            default:
                return 500;
        }
    }

    public int M(char current) {
        switch (current) {
            case 'I':
            case 'V':
            case 'X':
            case 'L':
            case 'C':
            case 'D':
            case 'M':
            default:
                return 1000;
        }
    }

    public static void main(String[] args) throws RunnerException {
        //roman numeral is within the range from 1 to 3999
        //MDCXCV,MCMXCIV,LVIII
//        int numeral = new RomanNumeralToInteger().romanToIntByMyResult3("MCMXCIV");
        int numeral2 =new RomanNumeralToInteger().romanToInt3("MCMXCIV");
//        log.info("{}", numeral);
        log.info("{}", numeral2);

//                Options options = new OptionsBuilder()
//                .include(RomanNumeralToIntegerState.class.getSimpleName())
////                .output("D:/my_all_demo/log/Benchmark.log") //如果輸出文件就不輸出於console
////                .shouldFailOnError(true) //隨機錯誤
//                .shouldDoGC(true) //是否在測試中，啟動JVM垃圾回收機制
////                .jvmArgs("-server") //若有JVM 參數可以加入
//                .build();
//        new Runner(options).run();
    }
}
