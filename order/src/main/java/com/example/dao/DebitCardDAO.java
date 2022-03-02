package com.example.dao;

import com.example.entity.DebitCardBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2019/9/5  11:23
 * @Version: 1.0
 * @Modified: By:
 */
public interface DebitCardDAO {

    /**
     * 根据用户id查询
     * @param
     * @return
     */
    DebitCardBO selectDebitCardByPrimaryKey(@Param("legalPersonCode") String legalPersonCode,@Param("cardNo") String cardNo);

    /**
     * 查询
     * @param cusNo
     * @return
     */
    List<DebitCardBO> selectDebitCardByCusNo(@Param("cusNo") String cusNo);

    /**
     * 查询
     * @param list
     * @return
     */
    List<DebitCardBO> selectDebitCardByMutipleContractId(List<String> list);

    /**
     * 合约集合中循环查询 卡片信息，in（合约编号）（不是卡片表的主键）
     * 采用多线程
     * 前闭后开
     *     取前5条数据
     *     select * from table_name limit 0,5
     *     查询第11到第15条数据
     *     select * from table_name limit 10,5
     *     limit关键字的用法：
     *     LIMIT [offset,] rows
     *     offset指定要返回的第一行的偏移量，rows第二个指定返回行的最大数目。初始行的偏移量是0(不是1)。
     *     select * from t_table limit #{index},#{num}
     *     public List<T> queryByThread(@Param(value = "index")int index, @Param(value = "num")int num);
     *
     * @return
     */
    List<DebitCardBO> selectByThread(@Param(value = "index")int index, @Param(value = "num")int num);


    /**
     *
     * @param list
     * @return
     */
    List<DebitCardBO> selectListByThread(List<String> list);

}

