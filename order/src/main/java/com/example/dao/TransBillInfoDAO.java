package com.example.dao;

import com.example.entity.DebitCardBO;
import com.example.entity.po.TransBillInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2023/1/8  11:23
 * @Version: 1.0
 * @Modified: By:
 */
public interface TransBillInfoDAO {

    /**
     * 根据账期编号查询按月逾期账单列表
     * @param
     * @return {@link List<Map<String,Object>>}
     */
    @MapKey("")
    List<Map<String,Object>> selectOverdueBillGroupByBillNum(@Param("userId") String userId, @Param("billType") String billType);


    /**
     * 查询逾期账单明细集合数据
     * @param
     * @return
     */
    List<TransBillInfo> selectOverdueBillDetail(@Param("userId") String userId, @Param("billType") String billType,@Param("billNum")String billNum,@Param("repayState")String repayState);



}

