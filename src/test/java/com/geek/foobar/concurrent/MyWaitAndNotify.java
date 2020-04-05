package com.geek.foobar.concurrent;

/**
 * @author 曲元涛
 * @date 2020/4/4 15:39
 */
public class MyWaitAndNotify {

    private final static Object waitAndNotify = new Object();

    public void waitAndNotify() {
        Thread waitThread = new Thread(() -> {
            synchronized (waitAndNotify) {
                System.out.println("i'm wait thread");
                try {
                    System.out.println("waiting.....");
                    waitAndNotify.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("notified.");
            }
        }, "waitThread");
        Thread notifyThread = new Thread(() -> {
            synchronized (waitAndNotify) {
                System.out.println("i'm notify thread");
                waitAndNotify.notifyAll();
                System.out.println("notify wait thread");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("after sleep 1 second");
            }
        }, "notifyThread");
        waitThread.start();
        try {
            Thread.sleep(20);// 确保 waitThread 在 notifyThread 之前执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyThread.start();
    }

    public static void main(String[] args) {
        MyWaitAndNotify myWaitAndNotify = new MyWaitAndNotify();
        myWaitAndNotify.waitAndNotify();
    }
}
