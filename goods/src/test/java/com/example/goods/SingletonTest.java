package com.example.goods;

/**
 * @ClassName:
 * @Description: 测试代码：我们在测试代码里面新建了10个线程，让这10个线程同时调用LazySingleton.getLazyInstance()方法
 * @Author: Bruce_T
 * @data: 2019/9/15  23:03
 * @Version: 1.0
 * @Modified: By:
 */
public class SingletonTest {

    public static void main(String[] args) {
        //创建十个线程调
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    LazySingleton.getLazyInstance();
                }
            }.start();
        }
    }
}

//结论：单例模式懒汉式在多线程的并发下也会出现问题
/**
 * 分析：多个线程同时访问上面的懒汉式单例，现在有两个线程A和B同时访问LazySingleton.getLazyInstance()方法。
 * 假设A先得到CPU的时间切片，A执行到if(lazyInstance==null)时，由于lazyInstance之前并没有实例化，
 * 所以lazyInstance==null为true,在还没有执行实例创建的时候
 * <p>
 * 此时CPU将执行时间分给了线程B，线程B执行到if(lazyInstance==null)时，由于lazyInstance之前并没有实例化，
 * 所以lazyInstance==null为true，
 * 线程B继续往下执行实例的创建过程，线程B创建完实例之后，返回。
 * <p>
 * 此时CPU将时间切片分给线程A，线程A接着开始执行实例的创建，实例创建完之后便返回。
 * 由此看线程A和线程B分别创建了一个实例（存在2个实例了），这就导致了单例的失效。
 * <p>
 * 解决办法：我们可以在getLazyInstance方法上加上synchronized使其同步，
 * 但是这样一来，会降低整个访问的速度，而且每次都要判断。
 * <p>
 * 那么有没有更好的方式来实现呢？我们可以考虑使用"双重检查加锁"的方式来实现，就可以既实现线程安全，又能够使性能不受到很大的影响
 */