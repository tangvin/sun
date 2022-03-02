//package com.example.user.mapper;
//
//import com.example.user.bean.User;
//import org.apache.ibatis.annotations.Insert;
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
//public interface UserMapper {
//    /**
//     * 根据id查询用户
//     */
//    @Select("SELECT * FROM T_USER WHERE ID = #{id}")
//    User findById(@Param("id") Integer id);
//
//    /**
//     * 新增用户
//     */
//    @Insert("INSERT INTO T_USER(NAME, AGE, ADDRESS, PHONE) VALUES(#{name}, #{age}, #{address}, #{phone})")
//    int insert(@Param("name") String name, @Param("age") Integer age, @Param("address") String address, @Param("phone") String phone);
//}
//
