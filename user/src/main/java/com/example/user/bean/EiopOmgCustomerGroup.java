package com.example.user.bean;

import java.util.Date;

/**
 * @Description
 * @Author TY
 * @Date 2021/9/16 19:57
 */
public class EiopOmgCustomerGroup {
    private Long id;
    private String cgNumber;
    private String cgName;
    private String cgStatus;
    private String cgFileType;
    private String version;
    private String cgCreateWay;
    private String userId;
    private Date createTime;
    private Date servicingTime;
    private String cgCustFlag;
    private String cgInvalidDate;
    private String fileId;
    private String saveContent;
    private String bak6;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCgNumber() {
        return cgNumber;
    }

    public void setCgNumber(String cgNumber) {
        this.cgNumber = cgNumber;
    }

    public String getCgName() {
        return cgName;
    }

    public void setCgName(String cgName) {
        this.cgName = cgName;
    }

    public String getCgStatus() {
        return cgStatus;
    }

    public void setCgStatus(String cgStatus) {
        this.cgStatus = cgStatus;
    }

    public String getCgFileType() {
        return cgFileType;
    }

    public void setCgFileType(String cgFileType) {
        this.cgFileType = cgFileType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCgCreateWay() {
        return cgCreateWay;
    }

    public void setCgCreateWay(String cgCreateWay) {
        this.cgCreateWay = cgCreateWay;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getServicingTime() {
        return servicingTime;
    }

    public void setServicingTime(Date servicingTime) {
        this.servicingTime = servicingTime;
    }

    public String getCgCustFlag() {
        return cgCustFlag;
    }

    public void setCgCustFlag(String cgCustFlag) {
        this.cgCustFlag = cgCustFlag;
    }

    public String getCgInvalidDate() {
        return cgInvalidDate;
    }

    public void setCgInvalidDate(String cgInvalidDate) {
        this.cgInvalidDate = cgInvalidDate;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getSaveContent() {
        return saveContent;
    }

    public void setSaveContent(String saveContent) {
        this.saveContent = saveContent;
    }

    public String getBak6() {
        return bak6;
    }

    public void setBak6(String bak6) {
        this.bak6 = bak6;
    }
}
