package com.geek.foobar.java3y.camp.problem;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 曲元涛
 * @date 2020/4/4 11:05
 */
public class PrintABCUsingLockSupport implements PrintABC{

    private int times;
    private Thread thread1;
    private Thread thread2;

    public PrintABCUsingLockSupport(int times) {
        this.times = times;
    }

    @Override
    public void doPrint() {
        thread1 = new Thread(() -> {
            for (int i = 0; i < times; i++) {
                System.out.print("A");
                LockSupport.unpark(thread2);
                LockSupport.park();
            }
        });
        thread2 = new Thread(() -> {
            for (int i = 0; i < times; i++) {
                LockSupport.park();
                System.out.print("B");
                LockSupport.unpark(thread1);
            }
        });
        thread1.start();
        thread2.start();
    }

    public static void main(String[] args) throws Exception {
        PrintABCUsingLockSupport printABCUsingLockSupport = new PrintABCUsingLockSupport(5);
        printABCUsingLockSupport.doPrint();
    }
}
