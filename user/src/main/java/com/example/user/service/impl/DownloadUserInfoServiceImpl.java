package com.example.user.service.impl;

import com.example.user.batch.rz.*;
import com.example.user.service.DownloadCustomerGroupService;
import com.example.user.service.DownloadUserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author TY
 * @description
 * @date 2021/10/20 10:53
 */
@Service
public class DownloadUserInfoServiceImpl implements DownloadUserInfoService {

    @Resource
    private DownloadUserInfoSetNoFile downloadUserInfoSetNoFile;
    @Resource
    private LoadUserInfoFromGtp loadUserInfoFromGtp;
    @Resource
    private DownloadEpassDeviceWhiteList downloadEpassDeviceWhiteList;

    @Override
    public void generatorBinInfo(){
        downloadUserInfoSetNoFile.mainProcess();
    }


    @Override
    public void loadBinInfo() {
        loadUserInfoFromGtp.mainProcess();
    }

    @Override
    public void downloadEpassDeviceWhiteList() {
        downloadEpassDeviceWhiteList.mainProcess();
    }

}
