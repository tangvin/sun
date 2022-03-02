package com.example.javaapi;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Description
 * @Author TY
 * @Date 2021/3/27 8:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestZkClientWatcher.class)
public class TestZkClientWatcher {

    /**
     * zookeeper地址
     */
    static final String CONNECT_ADDR = "127.0.0.1:2181";
    /**
     * session超时时间
     */
    static final int SESSION_OUTTIME = 10000;//ms


    /**
     * subscribeChildChanges方法 订阅子节点变化
     */
    @Test
    public void testZkClientWatcher1() throws Exception {

        ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), SESSION_OUTTIME);
        //对父节点添加监听子节点变化。
        zkc.subscribeChildChanges("/super", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("parentPath: " + parentPath);
                System.out.println("currentChilds: " + currentChilds);
            }
        });

//        Thread.sleep(3000);

//        zkc.createPersistent("/super");
//        Thread.sleep(1000);
//
//        zkc.createPersistent("/super"+"/"+"c1","c1 content");
//        Thread.sleep(1000);
//
//        zkc.createPersistent("/super" + "/" + "c2", "c2内容");
//        Thread.sleep(1000);


        zkc.delete("/super/c2");
        Thread.sleep(1000);

//        zkc.deleteRecursive("/super");
//        Thread.sleep(Integer.MAX_VALUE);
        zkc.close();

    }


    /**
     * subscribeDataChanges 订阅内容变化
     */
    @Test
    public void testZkClientWatcher2() throws InterruptedException {

        ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), SESSION_OUTTIME);

        zkc.createPersistent("/super", "1234");

        zkc.createPersistent("/super", new IZkDataListener() {
            @Override
            public void handleDataChange(String path, Object data) throws Exception {
                System.out.println("变更的节点为:" + path + ", 变更内容为:" + data);
            }

            @Override
            public void handleDataDeleted(String path) throws Exception {
                System.out.println("删除的节点为:" + path);
            }
        });

        Thread.sleep(3000);
        zkc.writeData("/super", "456", -1);
        Thread.sleep(1000);

//        zkc.delete("/super");
//        Thread.sleep(Integer.MAX_VALUE);
    }
}
