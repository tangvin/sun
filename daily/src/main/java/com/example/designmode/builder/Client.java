package com.example.designmode.builder;

/**
 * @Description
 * @Author TY
 * @Date 2021/2/27 21:08
 */
public class Client {

    public static void main(String[] args) {
        Builder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        Product product = director.construct();
        product.show();
//        System.gc();
//        Runtime.getRuntime().gc();
    }
}
