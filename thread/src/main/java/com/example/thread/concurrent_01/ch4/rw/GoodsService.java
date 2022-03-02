package com.example.thread.concurrent_01.ch4.rw;

/**
 * @Description 商品的服务的接口
 * @Author TY
 * @Date 2021/1/12 17:33
 */
public interface GoodsService {
    public GoodsInfo getNum();//获得商品的信息

    public void setNum(int number);//设置商品的数量
}
