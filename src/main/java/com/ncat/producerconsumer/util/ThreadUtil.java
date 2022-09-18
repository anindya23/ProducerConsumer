package com.ncat.producerconsumer.util;


import java.util.List;

public class ThreadUtil {
    public static void waitForAllToComplete(List<Thread> threads) {
        for(Thread thread:threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sleep(long interval) {
        try {
            Thread.sleep(interval);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}