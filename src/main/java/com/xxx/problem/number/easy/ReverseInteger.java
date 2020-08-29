package com.xxx.problem.number.easy;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ReverseInteger {
    /**
     * Given a 32-bit signed integer, reverse digits of an integer.
     * <p>
     * Example 1:
     * Input: 123
     * Output: 321
     * <p>
     * Example 2:
     * Input: -123
     * Output: -321
     * <p>
     * Example 3:
     * Input: 120
     * Output: 21
     * <p>
     * Note:
     * Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−2 ^ 31,  2 ^ 31 − 1].
     * For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.
     */
    //數顛倒後會超過−2 ^ 31或2 ^ 31 − 1
    public int reverse(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }

    public int reverseByMyResult1(int x) {
        StringBuilder sb = new StringBuilder(String.valueOf(x)).reverse();
        String reverse;
        if (x < 0) {
            reverse = sb.deleteCharAt(sb.length() - 1).insert(0, "-").toString();
        } else {
            reverse = sb.toString();
        }
        try {
            return Integer.parseInt(reverse);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public int reverseByMyResult2(int x) {
        int reverse = 0;
        while (x > 9 || x < -9) {
            reverse = reverse * 10 + x % 10;
            x /= 10;
        }
        if (Math.abs(reverse) > Integer.MAX_VALUE / 10) return 0;
        //不會有這個情況
        //if (Math.abs(reverse) == Integer.MAX_VALUE / 10 && x > 7) return 0;
        return reverse * 10 + x;
    }

    /**
     * 對類別註解類型
     * \@State:表明benchmark 註解的範圍
     * Scope.Thread:每個線程為獨立
     * Scope.Group:同組的所有線程為共用
     * Scope.Benchmark:所有線程為共用
     * ex:@State(Scope.Thread)
     * <p>
     * 對方法註解類型
     * \@BenchmarkMode:基準測試類型
     * Mode.Throughput 一秒可執行 整體吞吐量
     * Mode.AverageTime 每次調用平均時間
     * Mode.SampleTime 輸出最後隨機取樣的時間
     * Mode.SingleShotTime 只運行一次，測試冷啟動，通常warmup=0
     * Mode.All 包含所有模式
     * ex:@BenchmarkMode(Mode.AverageTime)
     * \@Warmup:遇熱輪數(冷啟動 必須設定為0)
     * ex:@Warmup(iterations = ?)
     * 需要遇熱原因，JVM有JIT(just in time 及時編譯編譯器)，若函數變調用多次，則JVM會將其編譯成為機械碼從而提升速度
     * \@Measurement 測試參數
     * iterations:測試輪數
     * time:每輪時長
     * timeUnit:時間單位
     * ex:@Measurement(iterations=?,time=?,timeUnit=?)
     * \@Threads:測試線程(以cpu*2=線程數，為程序執行的最小單位)
     * ex:@Threads(24)
     * \@Fork:進程數(一個進程由一個線程或多個線程組成)
     * ex:@Fork(12)
     * \@OutputTimeUnit:測試結果的時間類型
     * 一般只用java.util.concurrent -> seconds,milliseconds,microseconds,nanoseconds
     * ex:@OutputTimeUnit(TimeUnit.milliseconds)
     * \@Benchmark:此方法需進行Benchmark動作對象，不需要參數
     * \@Setup:測試前的準備工作，如初始化屬性，不需要參數
     * \@TearDown:測試後的準備工作，如資源回收、關閉線程池、關閉資料庫等，不需要參數
     * <p>
     * 對屬性註解類型:
     * \@Param:測試一個函數有不同參數情況，參數為字串形態
     * ex:@Param({"1200","2400","3700"})
     * <p>
     * result:
     * Benchmark                                                              Mode  Cnt    Score                   Error            Units
     * ReverseInteger.ReverseIntegerState.intToString1     thrpt    20     11160206.695 ±  305356.721  ops/s
     * ReverseInteger.ReverseIntegerState.intToString2     thrpt    20     11355874.349 ±  171451.976  ops/s
     * ReverseInteger.ReverseIntegerState.intToString3     thrpt    20     905107.039     ±  20146.709    ops/s
     * ReverseInteger.ReverseIntegerState.intToString4     thrpt    20     11572038.098 ±  161649.352  ops/s
     * ReverseInteger.ReverseIntegerState.charToString1  thrpt    20     15258475.505 ±  184038.417  ops/s
     * ReverseInteger.ReverseIntegerState.charToString2  thrpt    20     15123458.558 ±  226263.252  ops/s
     * ReverseInteger.ReverseIntegerState.charToString3  thrpt    20     15494289.846 ±  199773.529  ops/s
     * 4 > 2 > 1 > 3 , 7 > 5 > 6
     * but 4,7 is a little bit faster due to a special optimization in HotSpot JVM,so choose 1,2 or 5,6
     */
    //測函式速度
    @State(Scope.Thread)
    @BenchmarkMode(Mode.Throughput) //測試模式
    @Warmup(iterations = 3) //遇熱輪數
    @Measurement(iterations = 20, timeUnit = TimeUnit.MICROSECONDS) //測試輪數
    @Threads(1) //線程數
    @Fork(1) //進程數
    public static class ReverseIntegerState {
        public int i;
        public char c;

        @Setup(Level.Invocation)
        public void doSetup() {
            //固定7位數
            i = 1_000_000 + ThreadLocalRandom.current().nextInt(9_000_000);
            c = 'A';
        }

        @Benchmark
        public void intToString1(ReverseIntegerState state, Blackhole blackhole) {
            String s = Integer.toString(state.i);
            blackhole.consume(s);
        }

        @Benchmark
        public void intToString2(ReverseIntegerState state, Blackhole blackhole) {
            String s = String.valueOf(state.i);
            blackhole.consume(s);
        }

        @Benchmark
        public void intToString3(ReverseIntegerState state, Blackhole blackhole) {
            String s = String.format("%d", state.i);
            blackhole.consume(s);
        }

        @Benchmark
        public void intToString4(ReverseIntegerState state, Blackhole blackhole) {
            String s = "" + state.i;
            blackhole.consume(s);
        }

        @Benchmark
        public void charToString1(ReverseIntegerState state, Blackhole blackhole) {
            String s = Character.toString(state.c);
            blackhole.consume(s);
        }

        @Benchmark
        public void charToString2(ReverseIntegerState state, Blackhole blackhole) {
            String s = String.valueOf(state.c);
            blackhole.consume(s);
        }

        @Benchmark
        public void charToString3(ReverseIntegerState state, Blackhole blackhole) {
            String s = "" + state.c;
            blackhole.consume(s);
        }
    }

    public static void main(String[] args) throws RunnerException {
        int x = new ReverseInteger().reverseByMyResult2(-123456789);
        log.info("{}", x);
//        Options options = new OptionsBuilder()
//                .include(ReverseIntegerState.class.getSimpleName())
////                .output("D:/my_all_demo/log/Benchmark.log") //如果輸出文件就不輸出於console
////                .shouldFailOnError(true) //隨機錯誤
//                .shouldDoGC(true) //是否在測試中，啟動JVM垃圾回收機制
////                .jvmArgs("-server") //若有JVM 參數可以加入
//                .build();
//        new Runner(options).run();
    }
}