package com.example.user.service;

import com.example.user.bean.EventAnalysis;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/2 19:24
 */
public interface DownloadCustomerGroupService {


    /**
     * 生成bin文件
     */
    void generatorBinInfo();

    /**
     * 加载bin文件
     */
    void loadBinInfo();

    /**
     * d白名单
     */
    void whiteListBinInfo();

    /**
     * 白名单重构方案1
     */
    void whiteListRefactorNo1();

    /**
     * 白名单重构方案2
     */
    void whiteListRefactorNo2();

    /**
     * 白名单重构方案3
     */
    void whiteListRefactorNo3();

    /**
     * 白名单重构方案4
     */
    void whiteListRefactorNo4();

    /**
     * 白名单重构方案5
     */
    void whiteListRefactorNo5();
}
