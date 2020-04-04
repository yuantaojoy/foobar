package com.geek.foobar.concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 曲元涛
 * @date 2020/4/4 10:12
 */
public class MyLockSupport {

    public static void main(String[] args) {
        FIFOMutex fifoMutex = new FIFOMutex();
        new Thread(() -> MyLockSupport.printA(fifoMutex)).start();
        new Thread(() -> MyLockSupport.printB(fifoMutex)).start();
    }

    private static void printA(FIFOMutex fifoMutex) {
        MyLockSupport.print("A", fifoMutex);
    }

    private static void printB(FIFOMutex fifoMutex) {
        MyLockSupport.print("B", fifoMutex);
    }

    private static void print(String name, FIFOMutex fifoMutex) {
        fifoMutex.lock();
        for (int i = 0; i < 5; i++) {
            System.out.print(name);
        }
        fifoMutex.unlock();
    }

    static class FIFOMutex {
        private final AtomicBoolean locked = new AtomicBoolean(false);
        private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

        public void lock() {
            boolean wasInterrupted = false;
            Thread current = Thread.currentThread();
            waiters.add(current);

            while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
                LockSupport.park(this);
                if (Thread.interrupted()) {
                    wasInterrupted = true;
                }
            }

            waiters.remove();
            if (wasInterrupted) {
                current.interrupt();
            }
        }

        public void unlock() {
            locked.set(false);
            LockSupport.unpark(waiters.peek());
        }
    }
}
