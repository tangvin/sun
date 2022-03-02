package com.example.user.bean;

import java.util.Date;

/**
 * @Description
 * @Author TY
 * @Date 2021/9/16 19:57
 */
public class EiopEpassDeviceWhiteList {
    private Long id;
    private String tellerNo;
    private String status;
    private String approveTellerNo;
    private String fileOaasKey;
    private String createTime;
    private String lastUpdateTime;
    private String fileType;
    private String bak;

    private String fileContentNumber;
    private String fileContentChannel;
    private String fileContentDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTellerNo() {
        return tellerNo;
    }

    public void setTellerNo(String tellerNo) {
        this.tellerNo = tellerNo;
    }

    public String getApproveTellerNo() {
        return approveTellerNo;
    }

    public void setApproveTellerNo(String approveTellerNo) {
        this.approveTellerNo = approveTellerNo;
    }

    public String getFileOaasKey() {
        return fileOaasKey;
    }

    public void setFileOaasKey(String fileOaasKey) {
        this.fileOaasKey = fileOaasKey;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    public String getFileContentNumber() {
        return fileContentNumber;
    }

    public void setFileContentNumber(String fileContentNumber) {
        this.fileContentNumber = fileContentNumber;
    }

    public String getFileContentChannel() {
        return fileContentChannel;
    }

    public void setFileContentChannel(String fileContentChannel) {
        this.fileContentChannel = fileContentChannel;
    }

    public String getFileContentDate() {
        return fileContentDate;
    }

    public void setFileContentDate(String fileContentDate) {
        this.fileContentDate = fileContentDate;
    }
}
