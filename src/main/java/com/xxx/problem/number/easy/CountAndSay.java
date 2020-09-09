package com.xxx.problem.number.easy;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * The count-and-say sequence is the sequence of integers with the first five terms as following:
 * <p>
 * 1.     1
 * 2.     11
 * 3.     21
 * 4.     1211
 * 5.     111221
 * <p>
 * 1 is read off as "one 1" or 11.
 * 11 is read off as "two 1s" or 21.
 * 21 is read off as "one 2, then one 1" or 1211.
 * <p>
 * Given an integer n where 1 ≤ n ≤ 30, generate the nth term of the count-and-say sequence. You can do so recursively, in other words from the previous member read off the digits, counting the number of digits in groups of the same digit.
 * <p>
 * Note: Each term of the sequence of integers will be represented as a string.
 * <p>
 * n會到30，int,long不足以處理
 * leecode無法使用BigDecimal與BigInteger類別
 */
@Slf4j
public class CountAndSay {

    /**
     * 失敗,int無法處理數字大的 1 < n <= 30
    */
    private int countAndSayAlgorithm2(int number, int n) {
        if (1 < n) {
            int numNumber = 0;
            int prev = number % 10;
            int remainder = 0;
            int multiplier = 1;
            int count = 0;
            while (number > 0) {
                remainder = number % 10;
                number = number / 10;
                if (prev == remainder) {
                    count++;
                } else {
                    numNumber = numNumber + (count * 10 + prev) * multiplier;
                    multiplier *= 100;
                    count = 1;
                }
                prev = remainder;
            }
            number = numNumber + (count * 10 + remainder) * multiplier;
            return countAndSayAlgorithm2(number, n - 1);
        } else {
            return number;
        }
    }

    /**
     * recursive loop (在java中 盡量不使用)
    */
    private String countAndSayAlgorithm3(String result, int n) {
        if (1 < n) {
            char prev;
            char c;
            StringBuilder sb = new StringBuilder();
            prev = result.charAt(0);
            int count = 0;
            for (int i = 0; i < result.length(); i++) {
                c = result.charAt(i);
                if (prev == c) {
                    count++;
                } else {
                    sb.append(count).append(prev);
                    prev = c;
                    count = 1;
                }
            }
            result = sb.append(count).append(prev).toString();
            return countAndSayAlgorithm3(result, n - 1);
        } else {
            return result;
        }
    }

    /**
     * a loop (目前java之中 此方法速度較優)
     */
    public String countAndSayAlgorithm4(int n) {
        String result = "1";
        char prev;
        char c;
        while (1 < n) {
            StringBuilder sb = new StringBuilder();
            prev = result.charAt(0);
            int count = 0;
            for (int i = 0; i < result.length(); i++) {
                c = result.charAt(i);
                if (prev == c) {
                    count++;
                } else {
                    sb.append(count).append(prev);
                    prev = c;
                    count = 1;
                }
            }
            result = sb.append(count).append(prev).toString();
            n--;
        }
        return result;
    }

    public String countAndSay5(int n) {
        String num = "1";
        while (n > 1) {
            num = countAndSayAlgorithm5(num);
            n--;
        }
        return num;
    }

    /**
     * 將宣告放在迴圈內 速度較為慢
     */
    private String countAndSayAlgorithm5(String n) {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        char prevChar = n.charAt(0);
        for (int i = 0; i < n.length(); i++) {
            if (n.charAt(i) == prevChar) {
                count++;
            } else {
                sb.append(count);
                sb.append(prevChar);
                prevChar = n.charAt(i);
                count = 1;
            }
        }
        sb.append(count);
        sb.append(prevChar);
        return sb.toString();
    }

    /**
     * Benchmark                                                            Mode  Cnt   Score                  Error             Units
     * CountAndSay.CountAndSayState.countAndSay2  thrpt    20    5818334.356 ±   112464.598  ops/s
     * CountAndSay.CountAndSayState.countAndSay3  thrpt    20    2116909.542 ±   11994.105    ops/s
     * CountAndSay.CountAndSayState.countAndSay4  thrpt    20    2369816.837 ±   36893.422    ops/s
     * CountAndSay.CountAndSayState.countAndSay5  thrpt    20    1934769.135 ±   6818.865      ops/s
     *
     * 在java中，recursive loop速度較慢
     * leetcode server 顯示 recursive loop 速度快佔記憶體少
     * 由此可知遞歸在某個程式語言(java不適用)速度為最快 記憶體最少 因此leetcode盡量使用遞歸方法
     *
     */
    @State(Scope.Thread)
    @BenchmarkMode(Mode.Throughput) //測試模式
    @Warmup(iterations = 3) //遇熱輪數
    @Measurement(iterations = 20, timeUnit = TimeUnit.MICROSECONDS) //測試輪數
    @Threads(1) //線程數
    @Fork(1) //進程數
    public static class CountAndSayState {
        CountAndSay countAndSay = new CountAndSay();
        int n;

        @Setup(Level.Invocation)
        public void doSetup() {
            n = 50;
        }

        @Benchmark
        public void countAndSay2(CountAndSayState state, Blackhole blackhole) {
            String s = String.valueOf(countAndSay.countAndSayAlgorithm2( 1, state.n));
            blackhole.consume(s);
        }

        @Benchmark
        public void countAndSay3(CountAndSayState state, Blackhole blackhole) {
            StringBuilder sb = new StringBuilder();
            String s = countAndSay.countAndSayAlgorithm3("1", state.n);
            blackhole.consume(s);
        }

        @Benchmark
        public void countAndSay4(CountAndSayState state, Blackhole blackhole) {
            String s = countAndSay.countAndSayAlgorithm4(state.n);
            blackhole.consume(s);
        }

        @Benchmark
        public void countAndSay5(CountAndSayState state, Blackhole blackhole) {
            String s = countAndSay.countAndSay5(state.n);
            blackhole.consume(s);
        }
    }

    public static void main(String[] args) throws RunnerException {
        String s3 = new CountAndSay().countAndSayAlgorithm3("1",7);
        String s2 = String.valueOf(new CountAndSay().countAndSayAlgorithm2(1,7));
        String s4 = new CountAndSay().countAndSayAlgorithm4(7);
        String s5 = new CountAndSay().countAndSay5(7);
        log.info(s2);
        log.info(s3);
        log.info(s4);
        log.info(s5);
        Options options = new OptionsBuilder()
                .include(CountAndSayState.class.getSimpleName())
                .shouldDoGC(true)
                .build();
        new Runner(options).run();
    }
}
