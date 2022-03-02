package com.example.thread.concurrent_01.ch1.threadlocal;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalOOM {
    private static final int TASK_LOOP_SIZE = 500;

    final static ThreadPoolExecutor poolExecutor
            = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

    static class LocalVariable {
        /*5M大小的数组*/
        private byte[] a = new byte[1024 * 1024 * 5];
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < TASK_LOOP_SIZE; ++i) {
            poolExecutor.execute(new Runnable() {
                public void run() {
                    new LocalVariable();
                    System.out.println("use local varaible");
                }
            });

            Thread.sleep(100);
        }
        System.out.println("pool execute over");
    }
}
