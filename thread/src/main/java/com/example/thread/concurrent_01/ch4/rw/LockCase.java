package com.example.thread.concurrent_01.ch4.rw;

/**
 * @Description 使用Lock的范例
 * @Author TY
 * @Date 2021/1/14 20:19
 */
public class LockCase {

    private int age = 10000;

    private static class TestThread extends Thread {
        private LockCase lockCase;

        public TestThread(LockCase lockCase, String name) {
            super(name);
            this.lockCase = lockCase;
        }

        @Override
        public void run() {

            for (int i = 0; i < 10000; i++) {//递增10000


            }
        }


    }

    public void test() {

    }

    public void test2() {

    }

    public int getAge() {
        return age;
    }

    public static void main(String[] args) {
        LockCase lockCase = new LockCase();
        Thread endThread = new TestThread(lockCase, "endThread");
        endThread.start();
        for (int i = 0; i < 10000; i++) {
            lockCase.test2();
        }
        System.out.println(Thread.currentThread().getName() + " age =  " + lockCase.getAge());
    }
}
