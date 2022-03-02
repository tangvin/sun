//package com.example.hystrix.mapper;
//
//
//import com.example.hystrix.bean.Order;
//import com.example.order.bean.Order;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
//
///**
// * @ClassName:
// * @Description:
// * @Author: Bruce_T
// * @data: 2019/9/5  11:23
// * @Version: 1.0
// * @Modified: By:
// */
//@Mapper
//public interface OrderMapper {
//    /**根据id查询订单*/
//    @Select("SELECT * FROM T_ORDER WHERE ID = #{id}")
//    Order findById(@Param("id") Integer id);
//
//}
//
