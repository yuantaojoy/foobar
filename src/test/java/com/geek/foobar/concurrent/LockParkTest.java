package com.geek.foobar.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 曲元涛
 * @date 2020/4/4 16:39
 */
public class LockParkTest {

    private static volatile boolean flag = true;

    /**
     * park和unpark的基本使用
     *
     * @author 曲元涛
     * @date 2020/4/4 17:05
     */
    private void test1() {
        Thread thread = new Thread(() -> {
            while (flag) {

            }
            System.out.println("before first park");
            LockSupport.park(this);
            System.out.println("after first park");
            LockSupport.park(this);
            System.out.println("after second park");
        });
        thread.start();
        flag = false;
        sleep(20);
        System.out.println("before first unpark");
        LockSupport.unpark(thread);
        System.out.println("before second unpark");
        LockSupport.unpark(thread);
    }

    /**
     * 连续两次调用unpark当permit已经可用再unpark时，unpark的效果并不会叠加
     * 即不会让第二次的park成功拿到permit
     *
     * @author 曲元涛
     * @date 2020/4/4 17:07
     */
    private void test2() {
        Thread thread = new Thread(() -> {
            while (flag) {

            }
            System.out.println("before first park");
            LockSupport.park(this);
            System.out.println("after first park");
            LockSupport.park(this);
            System.out.println("after second park");
        });
        thread.start();
        flag = false;
        LockSupport.unpark(thread);
        // sleep(200);
        LockSupport.unpark(thread);
    }

    /**
     * Thread.interrupted()
     *
     * @author 曲元涛
     * @date 2020/4/4 17:07
     */
    private void test3() {
        Thread thread = new Thread(() -> {
            System.out.println("before first park");
            LockSupport.park();
            System.out.println("after first park");
            LockSupport.park();
            System.out.println("after second park");
            System.out.println("isInterrupted is " + Thread.interrupted());
            System.out.println("isInterrupted is " + Thread.interrupted());
            LockSupport.park();
            System.out.println("after third park");
        });
        thread.start();
        sleep(200);
        thread.interrupt();
    }

    public static void main(String[] args) {
        LockParkTest lockParkTest = new LockParkTest();
        // lockParkTest.test1();
        // lockParkTest.test2();
        lockParkTest.test3();
    }

    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
