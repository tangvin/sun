package com.example.goods;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName:
 * @Description: 实际开发中的场景：
 * 为了程序的高效率使用多线程并发，然而是循环调用，可能导致创建线程数过多，考虑采用线程池管理，
 * 这时候创建线程池仍然是处于循环调用中，也可能导致多个线程池，这时候就考虑使用单例模式。
 * @Author: Bruce_T
 * @data: 2019/9/15  23:19
 * @Version: 1.0
 * @Modified: By:
 */
public class ThreadPoolFactoryUtil {
    private ExecutorService executorService;

    //在构造函数中创建线程池
    private ThreadPoolFactoryUtil() {
        //获取系统处理器个数，作为线程池数量
        int nThreads = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(nThreads);
    }

    //定义一个静态内部类，内部定义静态成员创建外部类实例
    private static class SingletonContainer {
        private static ThreadPoolFactoryUtil util = new ThreadPoolFactoryUtil();
    }

    //获取本类对象
    public static ThreadPoolFactoryUtil getUtil() {
        return SingletonContainer.util;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}


/**
 * 静态内部类的特点：
 * 1、静态内部类无需依赖于外部类，它可以独立于外部对象而存在。
 * 2、静态内部类，多个外部类的对象可以共享同一个内部类的对象。
 * 3、使用静态内部类的好处是加强了代码的封装性以及提高了代码的可读性。
 * 4、普通内部类不能声明static的方法和变量，注意这里说的是变量，
 * 常量（也就是final static修饰的属性）还是可以的，而静态内部类形似外部类，
 * 没有任何限制。可以直接被用外部类名+内部类名获得。
 */
