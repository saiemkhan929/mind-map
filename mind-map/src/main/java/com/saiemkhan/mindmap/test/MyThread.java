package com.saiemkhan.mindmap.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyThread{
    private static final Lock lock = new ReentrantLock();
    static int balance = 50;

    public static int getBalance() {
        return balance;
    }

    public static void setBalance(int balance) {
        MyThread.balance = balance;
    }

    public static void main(String[] args) throws InterruptedException {



        Thread thread1 = new BankAccount(lock);
        Thread thread2 = new BankAccount(lock);
        thread1.start();
        thread2.start();
        Thread.sleep(1000);
        thread1.interrupt();

    }
}
