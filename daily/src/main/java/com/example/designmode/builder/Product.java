package com.example.designmode.builder;

/**
 * @Description 产品角色：包含多个组成部件的复杂对象
 * @Author TY
 * @Date 2021/2/27 21:02
 */
public class Product {
    private String partA;
    private String partB;
    private String partC;

    public void setPartA(String partA) {
        this.partA = partA;
    }

    public void setPartB(String partB) {
        this.partB = partB;
    }

    public void setPartC(String partC) {
        this.partC = partC;
    }


    public void show() {
        //显示产品的特性
        System.out.println("hello 这是Product的方法");
    }
}
