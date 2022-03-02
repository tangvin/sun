package com.example.designmode.builder;

/**
 * @Description 具体建造者：实现了抽象建造者接口
 * @Author TY
 * @Date 2021/2/27 21:04
 */
public class ConcreteBuilder extends Builder {
    @Override
    public void buildPartA() {
        product.setPartA("建造 PartA");
    }

    @Override
    public void buildPartB() {
        product.setPartA("建造 PartB");
    }

    @Override
    public void buildPartC() {
        product.setPartA("建造 PartC");
    }
}
