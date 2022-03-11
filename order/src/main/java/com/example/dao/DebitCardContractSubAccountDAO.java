package com.example.dao;

import com.example.entity.DebitCardContractSubAccountBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: Bruce_T
 * @date: 2022/03/11   18:45
 * @version: 1.0
 * @modified:
 */
public interface DebitCardContractSubAccountDAO {

    /**
     *
     * @param depositNum
     * @return
     */
    List<DebitCardContractSubAccountBO> selectList(@Param("depositNum") String depositNum);


    List<DebitCardContractSubAccountBO> selectListByPage(@Param("depositNum") String depositNum,@Param("pageSize") int pageSize,@Param("pageNo")int pageNo);
}
