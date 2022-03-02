package com.example.user.service.impl;

import com.example.user.batch.rz.*;
import com.example.user.service.DownloadCustomerGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author TY
 * @description
 * @date 2021/9/20 10:53
 */
@Service
public class DownloadCustomerGroupServiceImpl implements DownloadCustomerGroupService {

    @Resource
    private DownloadEiopOmgCustomerGroupSetNoFile downloadEiopOmgCustomerGroupSetNoFile;
    @Resource
    private LoadIfdsCustomerGroupFromGtp loadIfdsCustomerGroupFromGtp;
    @Resource
    private DownloadEiopOmgCustomerWhiteListSetNoFile downloadEiopOmgCustomerWhiteListSetNoFile;
    @Resource
    DownloadWhiteListRefactorNo2 downloadWhiteListRefactorNo2;
    @Resource
    DownloadWhiteListRefactorNo1 downloadWhiteListRefactorNo1;
    @Resource
    DownloadWhiteListRefactorNo3 downloadWhiteListRefactorNo3;
    @Resource
    DownloadWhiteListRefactorNo4 downloadWhiteListRefactorNo4;
    @Resource
    DownloadWhiteListRefactorNo5 downloadWhiteListRefactorNo5;

    @Override
    public void generatorBinInfo(){
//        DownloadEiopOmgCustomerGroupSetNoFile setNoFile = new DownloadEiopOmgCustomerGroupSetNoFile();
        downloadEiopOmgCustomerGroupSetNoFile.mainProcess();
    }

    @Override
    public void loadBinInfo() {
        loadIfdsCustomerGroupFromGtp.mainProcess();
    }

    @Override
    public void whiteListBinInfo() {
        downloadEiopOmgCustomerWhiteListSetNoFile.mainProcess();
    }

    @Override
    public void whiteListRefactorNo1(){
        downloadWhiteListRefactorNo1.mainProcess();
    }

    @Override
    public void whiteListRefactorNo2(){
        downloadWhiteListRefactorNo2.mainProcess();
    }

    @Override
    public void whiteListRefactorNo3(){
        downloadWhiteListRefactorNo3.mainProcess();
    }

    @Override
    public void whiteListRefactorNo4(){
        downloadWhiteListRefactorNo4.mainProcess();
    }

    @Override
    public void whiteListRefactorNo5(){
        downloadWhiteListRefactorNo5.mainProcess();
    }

}
