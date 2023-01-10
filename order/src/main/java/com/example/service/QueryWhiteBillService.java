package com.example.service;

import com.example.vo.OverdueBillVO;

import java.util.List;

/**
 * @description:
 * @author: Bruce_T
 * @date: 2023/01/08   19:55
 * @version: 1.0
 * @modified:
 */
public interface QueryWhiteBillService {

    List<OverdueBillVO> queryOverdueBillList(String userId, String billType);
}
