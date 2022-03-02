package com.example.thread.concurrent_01.ch1.base;

/*startAndRun 的区别*/
public class StartAndRun {

    private static class ThreadRun extends Thread {

        @Override
        public void run() {
            super.run();
            int i = 10;
            while (i > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("I am [" + Thread.currentThread().getName() + "]====i===" + i--);
            }
        }
    }

    public static void main(String[] args) {
        ThreadRun threadRun = new ThreadRun();
        threadRun.setName("HelloWorld");
        threadRun.run();
    }
}
