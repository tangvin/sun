package com.example.goods.service;

import com.example.goods.bean.Goods;
import com.example.goods.dao.GoodsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GoodsService {
    private final Logger logger = LoggerFactory.getLogger(GoodsService.class);

    @Autowired
    private GoodsMapper goodsMapper;

    public Goods findById(Integer id) {
        logger.debug("param id : {}", id);
//		Goods u = this.goodsMapper.findById(id);
        Goods u = this.goodsMapper.selectGoodsById(id);
        logger.debug("result name : {}", u.getName());
        return u;
    }

    public List<Map<String, Object>> selectGoodsInfo(Integer id) {
        List<Map<String, Object>> list = this.goodsMapper.selectGoodsInfo(id);
        logger.debug("list.get(0)是 :", list.get(0));
        System.out.println("list.get(0)是 :" + list.get(0));
        return list;
    }

    public List<Map<String, Object>> selectGoodsInfoAll() {
        List<Map<String, Object>> list = this.goodsMapper.selectGoodsInfoAll();
        logger.debug("list.get(1)是 :", list.get(1));
        System.out.println("list.get(1)是 :" + list.get(1));
        return list;
    }


}
