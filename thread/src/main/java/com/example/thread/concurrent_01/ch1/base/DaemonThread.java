package com.example.thread.concurrent_01.ch1.base;

/*守护线程的使用*/
public class DaemonThread {

    private static class DaemonThreadTest extends Thread {
        @Override
        public void run() {
//            super.run();
            int i = 10;
            try {
                while (!isInterrupted()) {
                    System.out.println("[" + Thread.currentThread().getName() + "] :I am extend Thread!");
                    i--;
                }
                System.out.println("[" + Thread.currentThread().getName() + "]:interrupt flag is " + isInterrupted());
            } finally {
                //守护线程中finally不一定起作用
                System.out.println("finally.................");
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        DaemonThreadTest daemonThreadTest = new DaemonThreadTest();
//        daemonThreadTest.setDaemon(true);
        daemonThreadTest.start();
        Thread.sleep(3000);
        daemonThreadTest.isInterrupted();
    }
}
