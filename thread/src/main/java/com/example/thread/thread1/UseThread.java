package com.example.thread.thread1;

public class UseThread extends Thread {

    @Override
    public void run() {
        super.run();
        System.out.println("extend Thread");
    }

    public static void main(String[] args) {
        Thread thread = new Thread();
        String name = thread.getName();
        System.out.println("name==" + name);
        Thread thread1 = Thread.currentThread();
        ClassLoader contextClassLoader = thread1.getContextClassLoader();
        ThreadLocal threadLocal = new ThreadLocal();

    }

}
