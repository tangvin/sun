package com.example.user.api;

import com.example.user.service.DownloadCustomerGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/2 19:59
 */
@RequestMapping("/downloadCustomer")
@RestController
public class CustomerGroupController {

    private final Logger logger = LoggerFactory.getLogger(CustomerGroupController.class);

    @Autowired
    private DownloadCustomerGroupService downloadCustomerGroupService;


    @RequestMapping("/sendbin")
    public void generateBinInfo() {
        downloadCustomerGroupService.generatorBinInfo();
    }


    @GetMapping("/loadbin")
    public void loadBinInfo() {
        downloadCustomerGroupService.loadBinInfo();
    }

    @RequestMapping("/whiteListBinInfo")
    public void whiteListBinInfo() {
        downloadCustomerGroupService.whiteListBinInfo();
    }

    @RequestMapping("/whiteListRefactorNo1")
    public void whiteListRefactorNo1() {
        downloadCustomerGroupService.whiteListRefactorNo1();
    }

    @RequestMapping("/whiteListRefactorNo2")
    public void whiteListRefactorNo2() {
        downloadCustomerGroupService.whiteListRefactorNo2();
    }

    @RequestMapping("/whiteListRefactorNo3")
    public void whiteListRefactorNo3() {
        downloadCustomerGroupService.whiteListRefactorNo3();
    }

    @RequestMapping("/whiteListRefactorNo4")
    public void whiteListRefactorNo4() {
        downloadCustomerGroupService.whiteListRefactorNo4();
    }

    @RequestMapping("/whiteListRefactorNo5")
    public void whiteListRefactorNo5() {
        downloadCustomerGroupService.whiteListRefactorNo5();
    }
}
