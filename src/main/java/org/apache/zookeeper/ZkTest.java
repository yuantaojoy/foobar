package org.apache.zookeeper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 曲元涛
 * @date 2020/4/15 09:24
 */
public class ZkTest implements Runnable{
    static int inventory = 1;
    private static final int NUM = 10;
    private static CountDownLatch cdl = new CountDownLatch(NUM);
    private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            10,
            100,
            1L,
            TimeUnit.HOURS,
            new LinkedBlockingQueue<>(100),
            new ZkThreadFactory("ZooKeeper"),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Override
    public void run() {

        try {
            cdl.await();
            System.out.println("选手已就位！");
            if (inventory > 0) {
                Thread.sleep(5);
                inventory--;
            }
            System.out.println(inventory);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOCK.writeLock().unlock();
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUM; i++) {
            threadPoolExecutor.execute(new ZkTest());
            cdl.countDown();
            System.out.println("选手" + i + "出发!");
        }
    }
}
