package com.example.daily;

import org.apache.zookeeper.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DailyApplicationTests {

    //zookeeper服务地址
    private static final String SERVER = "127.0.0.1:2181";

    //会话超时时间
    private final int SESSION_TIMEOUT = 30000;


    @Test
    public void contextLoads() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(SERVER, SESSION_TIMEOUT, null);
        System.out.println("zooKeeper==" + zooKeeper);
        System.out.println("zooKeeper.getState()==" + zooKeeper.getState());
        zooKeeper.create("/king", "value".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }


    //发令枪
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Test
    public void testSession2() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(SERVER, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                    System.out.println("已经获得了来链接！！！");
                }
            }
        });
        System.out.println("before---zooKeeper.getState()==" + zooKeeper.getState());
        //链接完成之前先等待
        countDownLatch.await();
        System.out.println("after---zooKeeper.getState()==" + zooKeeper.getState());

    }


    @Test
    public void testLamda() {
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("");
//        List<String> stringList = Arrays.asList("A","B","C");
//        Stream<String> distinct = stringList.stream().distinct();
////        System.out.println("first="+first);
//        System.out.println("distinct="+distinct);
//        ForkJoinPool forkJoinPool = new ForkJoinPool();

//        forkJoinPool.execute(forkJoinPool);

        CountDownLatch countDownLatch = new CountDownLatch(5);
        countDownLatch.countDown();
        System.out.println("countDownLatch==" + countDownLatch);
    }

}
