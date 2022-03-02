package com.example.goods;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2019/9/15  23:17
 * @Version: 1.0
 * @Modified: By:
 */
public class GracefulSingleton {
    private GracefulSingleton() {
        System.out.println("创建GracefulSingleton实例一次！");
    }

    //类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
    private static class SingletonHoder {
        //静态初始化器，由JVM来保证线程安全
        private static GracefulSingleton instance = new GracefulSingleton();
    }

    public static GracefulSingleton getInstance() {
        return SingletonHoder.instance;
    }
}
