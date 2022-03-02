package com.example.javaapi.common;

import org.apache.zookeeper.ZooKeeper;

/**
 * @Description
 * @Author TY
 * @Date 2021/3/27 8:51
 */
public class ZKCommonUtils {


    public static void releaseConnection(ZooKeeper zk) {
        if (zk != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
            }
        }
    }
}
