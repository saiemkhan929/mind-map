package com.saiemkhan.mindmap.test;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount extends Thread{
    private final Lock lock;

    public BankAccount(Lock lock) {
        this.lock = lock;

    }

    public void withdraw(int amount) {
        System.out.println(Thread.currentThread().getName()+" attempting to withdraw "+amount);

            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (MyThread.getBalance() >= amount) {

                    try {

                        Thread.sleep(5000);
                        System.out.println(Thread.currentThread().getName()+" proceeding with withdraw "+amount);
                        MyThread.setBalance(MyThread.getBalance() - amount);
                        System.out.println(Thread.currentThread().getName()+" withdrawn "+amount + ". Remaining balance " + MyThread.getBalance());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                    }



                }else{
                    System.out.println(Thread.currentThread().getName()+" insufficient balance ");
                }
            }




    @Override
    public void run() {
        this.withdraw(50);
    }
}
