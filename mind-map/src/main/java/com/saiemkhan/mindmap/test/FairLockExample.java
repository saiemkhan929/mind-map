package com.saiemkhan.mindmap.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairLockExample {
    private static final Lock fairLock = new ReentrantLock(true); // Enable fairness

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " is waiting to acquire the lock.");
                    fairLock.lock();
                    System.out.println(Thread.currentThread().getName() + " has acquired the lock.");
                    // Simulate some work
                    Thread.sleep((long) (Math.random() * 5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + " has released the lock.");
                    fairLock.unlock();
                }
            });
            thread.start();
        }
    }
}
