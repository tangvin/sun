package com.example.user.dao;

import com.example.user.bean.EiopOmgCustomerGroup;
import com.example.user.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface UserMapper {

    /**
     * 查询
     * @param map
     * @return
     */
    List<User> selectByPage(Map map);

    /**
     * 根据用户id查询
     * @param id
     * @return
     */
    User selectByUserId(@Param("id") Integer id);

    /**
     * 新增用户信息map
     * @param paraMap
     * @return
     */
    int insertUser(Map paraMap);

    /**
     * 实体新增
     * @param user
     * @return
     */
    int insertUserEntity(User user);

    /**
     * 批量插入
     * @param userList
     * @return
     */
    int insertBatch(List<User> userList);
}

