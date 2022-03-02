package com.example.user.dao;

import com.example.user.bean.EiopEpassDeviceWhiteList;
import com.example.user.bean.EiopOmgCustomerGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description
 * @author TY
 * @date 2021/7/2 19:09
 */
@Mapper
public interface EiopEpassDeviceWhiteListMapper {

    /**
     * 查询
     * @param map
     * @return
     */
    List<EiopEpassDeviceWhiteList> selectEpassDeviceWhiteList(Map map);



}
