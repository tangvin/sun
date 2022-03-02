package com.example.goods.dao;


import com.example.goods.bean.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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

@Repository
public interface GoodsMapper {
    /**
     * 根据id查询订单
     */
//    @Select("SELECT * FROM T_GOODS WHERE ID = #{id}")
//    Goods findById(@Param("id") Integer id);


    Goods findById(@Param("id") Integer id);

    public Goods selectGoodsById(Integer id);


    public List<Map<String, Object>> selectGoodsInfo(Integer id);


    public List<Map<String, Object>> selectGoodsInfoAll();


//    List<Goods> selectAllGoods();

}

