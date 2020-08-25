package com.xxx.problem.common.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class Thread1 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                log.info("does it sleep?");
                Thread.sleep(1000);
                log.info("it's sleeping.");
            } catch (InterruptedException e) {
                log.info("[run {}] {}", Thread.currentThread().getName(), ExceptionUtils.getStackTrace(e));
            }
        });
        thread.start();
    }
}