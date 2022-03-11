package com.example.dao;

import com.example.entity.DebitCardLifeCycleBO;

import java.util.List;

/**
 * @description:
 * @author: Bruce_T
 * @date: 2022/03/11   18:45
 * @version: 1.0
 * @modified:
 */
public interface DebitCardLifeCycleDAO {

    List<DebitCardLifeCycleBO> selectList(List list);
}
