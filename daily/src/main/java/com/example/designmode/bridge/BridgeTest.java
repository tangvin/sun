package com.example.designmode.bridge;

import java.util.StringJoiner;

/**
 * @Description
 * @Author TY
 * @Date 2021/2/28 13:32
 */
public class BridgeTest {
    public static void main(String[] args) {
        Implementor imple = new ConcreteImplementorA();
        Abstraction abs = new RefinedAbstraction(imple);
        abs.Operation();
    }
}
