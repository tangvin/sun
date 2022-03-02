package com.example.user.service;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/2 19:24
 */
public interface DownloadUserInfoService {


    /**
     * 生成bin文件
     */
    void generatorBinInfo();

    /**
     * 加载bin文件
     */
    void loadBinInfo();

    /**
     * downloadEpassDeviceWhiteList作业
     */
    void downloadEpassDeviceWhiteList();

}
