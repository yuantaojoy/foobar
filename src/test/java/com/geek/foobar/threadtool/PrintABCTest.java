package com.geek.foobar.threadtool;

/**
 * @author 曲元涛
 * @date 2020/4/3 10:48
 */
public class PrintABCTest {

    public static void main(String[] args) throws Exception {
        PrintABC printABC = new PrintABCUsingSemaphore(2);
        printABC.doPrint();
        printABC = new PrintABCUsingLock(2);
        printABC.doPrint();
    }
}
