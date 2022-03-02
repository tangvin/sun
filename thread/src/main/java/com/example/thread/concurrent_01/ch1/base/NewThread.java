package com.example.thread.concurrent_01.ch1.base;

/**
 * 线程启动方式
 */
public class NewThread {


    /*继承Thread类*/
    private static class UseThread extends Thread {
        @Override
        public void run() {
            super.run();
            //DO MY WORK
            System.out.println("I AM EXTEND THREAD");
        }
    }


    /*实现Runnable接口*/
    private static class UseRunnable implements Runnable {

        @Override
        public void run() {
            //do my work
            System.out.println("实现Runnable接口了");
        }
    }


    public static void main(String[] args) {
        UseThread useThread = new UseThread();
        useThread.start();


        UseRunnable useRunnable = new UseRunnable();
        Thread thread = new Thread(useRunnable);
        thread.start();
    }
}
