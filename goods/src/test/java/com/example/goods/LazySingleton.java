package com.example.goods;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2019/9/15  23:01
 * @Version: 1.0
 * @Modified: By:
 */
public class LazySingleton {
    //为了易于模拟多线程下，懒汉式出现的问题，我们在创建实例的构造函数里面使当前线程暂停了50毫秒
    private LazySingleton() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("生成LazySingleton实例一次！");
    }

    private static LazySingleton lazyInstance = null;

    public static LazySingleton getLazyInstance() {
        if (lazyInstance == null) {
            lazyInstance = new LazySingleton();
        }
        return lazyInstance;
    }
}
