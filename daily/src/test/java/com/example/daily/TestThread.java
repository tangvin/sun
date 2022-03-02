package com.example.daily;

public class TestThread extends Thread {

    @Override
    public void run() {
        Thread thread = new Thread();
        synchronized (this) {
            String name = thread.getName();
            System.out.println(name);
        }
        thread.start();
    }

    public static void main(String[] args) {
        TestThread testThread = new TestThread();
        testThread.run();
    }
}
