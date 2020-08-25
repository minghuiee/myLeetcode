package com.xxx.problem.common.example;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunnableAndThread2 implements Runnable {
    private int ticket = 10;

    public void run() {
        log.info("123");
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

class RunnableTest2 {
    public static void main(String[] args) {
        RunnableAndThread thread = new RunnableAndThread();
        RunnableAndThread thread2 = new RunnableAndThread();
        RunnableAndThread thread3 = new RunnableAndThread();
        //三個限程各自賣10張票
        Thread t1 = new Thread(thread);
        Thread t2 = new Thread(thread2);
        Thread t3 = new Thread(thread3);
        t1.start();
        t2.start();
        t3.start();
    }
}
