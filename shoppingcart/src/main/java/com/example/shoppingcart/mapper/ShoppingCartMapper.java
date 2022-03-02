package com.example.shoppingcart.mapper;


import com.example.shoppingcart.bean.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName:
 * @Description:
 * @Author: Bruce_T
 * @data: 2019/9/5  11:23
 * @Version: 1.0
 * @Modified: By:
 */
@Mapper
public interface ShoppingCartMapper {
    /**
     * 根据id查询订单
     */
    @Select("SELECT * FROM T_SHOPPINGCART WHERE ID = #{id}")
    ShoppingCart findById(@Param("id") Integer id);

}

