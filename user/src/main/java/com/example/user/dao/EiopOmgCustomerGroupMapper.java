package com.example.user.dao;

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
public interface EiopOmgCustomerGroupMapper {

    /**
     * 查询客群数据
     * @param cgStatus 客群状态
     * @param cgCreateWay   客群创建方式
     * @param saveContent  关联内容
     * @return ty
     */
    List<Map<String,Object>> selectCustGroupInfo(@Param("cgStatus") String cgStatus, @Param("cgCreateWay") String cgCreateWay, @Param("saveContent") String saveContent);

    /**
     *
     * @param map
     * @return
     */
    List<EiopOmgCustomerGroup> selectByPage(Map map);

    /**
     * * 更新客群状态
     * @param id
     * @param status
     * @param version
     * @return 1- 成功  0-失败
     */
    int updateOptimismLock(@Param("id") Long id,@Param("cgStatus") String cgStatus, @Param("version") String version);

    /**
     * 更新客群数量
     * @param paramMap
     * @return
     */
    int updateCustomerCount(Map paramMap);

    /**
     * 更新状态为校验失败
     * @param id
     * @return
     */
    int updateStatusValidateFail(Long id);

    /**
     * 报错客群数量
     * @param paramMap
     * @return
     */
    int saveCustomerAmount(Map paramMap);
}
