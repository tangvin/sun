package com.example.daily.entity;

import java.util.Date;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2019/9/16  16:27
 * @Version: 1.0
 * @Modified: By:
 */
public class Daily {
    private int dailyId;
    private String dailyContent;
    private String dailyTitle;
    private Date dailyDate;
    private String byPerson;
    private Date updateDate;

    public int getDailyId() {
        return dailyId;
    }

    public void setDailyId(int dailyId) {
        this.dailyId = dailyId;
    }

    public String getDailyContent() {
        return dailyContent;
    }

    public void setDailyContent(String dailyContent) {
        this.dailyContent = dailyContent;
    }

    public String getDailyTitle() {
        return dailyTitle;
    }

    public void setDailyTitle(String dailyTitle) {
        this.dailyTitle = dailyTitle;
    }

    public Date getDailyDate() {
        return dailyDate;
    }

    public void setDailyDate(Date dailyDate) {
        this.dailyDate = dailyDate;
    }

    public String getByPerson() {
        return byPerson;
    }

    public void setByPerson(String byPerson) {
        this.byPerson = byPerson;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
