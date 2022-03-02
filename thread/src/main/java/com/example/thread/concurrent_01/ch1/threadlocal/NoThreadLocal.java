package com.example.thread.concurrent_01.ch1.threadlocal;

public class NoThreadLocal {
    static Integer count = new Integer(1);


    public static class TestTask implements Runnable {
        int id;

        public TestTask(int i) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":start");
            count = count + id;
            System.out.println(Thread.currentThread().getName() + ":" + count);
        }
    }


    /*运行3个线程*/
    public void startThreadArray() {
        Thread[] threads = new Thread[3];
        for (int i = 0; i < threads.length; i++) {
//            Thread thread = threads[i];
            threads[i] = new Thread(new TestTask(i));
        }
        for (int i = 0; i < threads.length; i++) {
//            Thread thread = threads[i];
            threads[i].start();
        }
    }

    public static void main(String[] args) {
        NoThreadLocal noThreadLocal = new NoThreadLocal();
        noThreadLocal.startThreadArray();
    }

}
