package com.example.user.bean;

import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/2 19:11
 */
public class EventAnalysis {
    private Long id;
    private String pageSn;
    private String pageName;
    private String areaSn;
    private String areaName;
    private String buttonSn;
    private String buttonName;
    private String buttonSeq;
    private String pageNo;
    private String areaNo;
    private String buttonNo;
    private String publishTime;
    private String serialNo;
    List<EventAnalysis> childList;

    public List<EventAnalysis> getChildList() {
        return childList;
    }

    public void setChildList(List<EventAnalysis> childList) {
        this.childList = childList.isEmpty() ? null : Collections.synchronizedList(childList);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPageSn() {
        return pageSn;
    }

    public void setPageSn(String pageSn) {
        this.pageSn = pageSn;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getAreaSn() {
        return areaSn;
    }

    public void setAreaSn(String areaSn) {
        this.areaSn = areaSn;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getButtonSn() {
        return buttonSn;
    }

    public void setButtonSn(String buttonSn) {
        this.buttonSn = buttonSn;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonSeq() {
        return buttonSeq;
    }

    public void setButtonSeq(String buttonSeq) {
        this.buttonSeq = buttonSeq;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public String getButtonNo() {
        return buttonNo;
    }

    public void setButtonNo(String buttonNo) {
        this.buttonNo = buttonNo;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
