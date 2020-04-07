package com.geek.foobar.threadtool;

import java.util.concurrent.Semaphore;

/**
 * 三个线程分别打印A，B，C，要求这三个线程一起运行，打印 n 次，输出形如“ABCABCABC….”的字符串。
 * @author 曲元涛
 * @date 2020/4/3 10:37
 */
public class PrintABCUsingSemaphore implements PrintABC{

    private int times;
    private Semaphore semaphoreA = new Semaphore(1);
    private Semaphore semaphoreB = new Semaphore(0);
    private Semaphore semaphoreC = new Semaphore(0);

    public PrintABCUsingSemaphore(int times) {
        this.times = times;
    }

    @Override
    public void doPrint() {
        new Thread(this::printA).start();
        new Thread(this::printB).start();
        new Thread(this::printC).start();
    }

    public void printA() {
        try {
            print("A", semaphoreA, semaphoreB);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printB() {
        try {
            print("B", semaphoreB, semaphoreC);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printC() {
        try {
            print("C", semaphoreC, semaphoreA);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void print(String name, Semaphore current, Semaphore next) throws InterruptedException{
        for (int i = 0; i < times; i++) {
            current.acquire();
            System.out.print(name);
            next.release();
        }
    }
}
