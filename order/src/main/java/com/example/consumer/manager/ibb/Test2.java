package com.example.consumer.manager.ibb;

import com.example.request.OneRequest;

/**
 * @className:
 * @description:
 * @author: Bruce_T
 * @data: 2022/02/24   23:11
 * @version: 1.0
 * @modified:
 */
public class Test2 implements StrategyMode{
    @Override
    public OneRequest doSubmit() {
        return new OneRequest();
    }
}
