package com.xxx.problem.common.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 1.start() 可以啟動一個新執行緒，run()不能
 * 2.start() 不能被重複呼叫，run()可以
 * 3.start()中的run程式碼，。
 * 直接呼叫run方法
 */
@Slf4j
public class RunnableAndThread implements Runnable {
    private int ticket = 10;

    public void run() {
        for (int i = 0; i < 20; i++) {
            if (this.ticket > 0) {
                log.info(Thread.currentThread().getName() + " sell ticket" + this.ticket--);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class RunnableTest {
    public static void main(String[] args) {
        RunnableAndThread thread = new RunnableAndThread();
        //三個限程共同賣10張票
        Thread t1 = new Thread(thread);
        Thread t2 = new Thread(thread);
        Thread t3 = new Thread(thread);
        t1.start();
        t2.start();
        t3.start();
    }
}
