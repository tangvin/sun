package com.example.goods;

/**
 * @ClassName:单例模式
 * @Description:懒汉式单例 应用刚启动的时候，并不创建实例，当外部调用该类的实例或者该类实例方法的时候，
 * 才创建该类的实例。（时间换空间）
 * 优点：
 * 实例在被使用的时候才被创建，可以节省系统资源，体现了延迟加载的思想。
 * 缺点：由于系统刚启动时且未被外部调用时，实例没有创建；
 * 如果一时间有多个线程同时调用LazySingleton.getLazyInstance()方法很有可能会产生多个实例。
 * @Author: Bruce_T
 * @data: 2019/9/15  22:50
 * @Version: 1.0
 * @Modified: By:
 */
public class SingletonClass {
    //私有构造函数，保证类不能通过new创建
    private SingletonClass() {
    }

    private static SingletonClass instance = null;

    public static SingletonClass getInstance() {
        if (instance == null) {
            //创建本类对象
            instance = new SingletonClass();
        }
        return instance;
    }
}
