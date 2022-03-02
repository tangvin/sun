package com.example.goods;

/**
 * @ClassName:
 * @Description: 在多线程并发下安全应用单例模式中的懒汉模式。
 * 这种方法在代码上可能就不怎么美观，我们可以优雅的使用一个内部类来维护单例类的实例
 * @Author: Bruce_T
 * @data: 2019/9/15  23:12
 * @Version: 1.0
 * @Modified: By:
 */
public class SecureLazySingleton {
    private SecureLazySingleton() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("生成LazySingleton实例一次！");
    }

    private static SecureLazySingleton lazyInstance = null;

    public static SecureLazySingleton getLazyInstance() {
        //先检查实例是否存在，如果不存在才进入下面的同步块
        if (lazyInstance == null) {
            //同步块，线程安全地创建实例
            synchronized (SecureLazySingleton.class) {
                //再次检查实例是否存在，如果不存在才真正地创建实例
                if (lazyInstance == null) {
                    lazyInstance = new SecureLazySingleton();
                }
            }
        }
        return lazyInstance;
    }
}
