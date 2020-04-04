package com.geek.foobar.java3y.camp.problem;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 曲元涛
 * @date 2020/4/3 10:57
 */
public class PrintABCUsingLock implements PrintABC{

    private int times;
    private int state;
    private Lock lock = new ReentrantLock();

    public PrintABCUsingLock(int times) {
        this.times = times;
    }

    @Override
    public void doPrint() throws Exception {
        new Thread(this::printA).start();
        new Thread(this::printB).start();
        new Thread(this::printC).start();
    }

    public void printA() {
        this.print("A", 0);
    }

    public void printB() {
        this.print("B", 1);
    }

    public void printC() {
        this.print("C", 2);
    }

    private void print(String name, int targetState) {
        for (int i = 0; i < times;) {
            lock.lock();
            if (state % 3 == targetState) {
                System.out.print(name);
                i++;
                state++;
            }
            lock.unlock();
        }
    }
}
