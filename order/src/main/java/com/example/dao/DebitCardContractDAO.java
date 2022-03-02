package com.example.dao;

import com.example.entity.DebitCardContractBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className:
 * @description:
 * @author: Bruce_T
 * @data: 2019/9/5  11:23
 * @version: 1.0
 * @modified: By:
 */
public interface DebitCardContractDAO {

    /**
     * 查询
     * @param list
     * @return
     */
    List<DebitCardContractBO> selectByMutipleContractNo(List list);

    /**
     * 根据用户id查询
     * @param contractNo
     * @return
     */
    List<DebitCardContractBO> selectByContractNo(@Param("contractNo") String contractNo);


}

