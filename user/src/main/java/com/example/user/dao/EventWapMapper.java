package com.example.user.dao;

import com.example.user.bean.EbankEvent;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author TY
 * @Date 2021/7/25 9:47
 */
public interface EventWapMapper {


    //操作控件数据
    EbankEvent selectEvent(EbankEvent ebankEvent);
    void insertEvent(EbankEvent ebankEvent);
    void updateEvent(EbankEvent ebankEvent);

    //操作页面数据
    Map  selectPage(EbankEvent ebankEvent);
    void insertPage(EbankEvent ebankEvent);
    void updatePage(EbankEvent ebankEvent);

}
