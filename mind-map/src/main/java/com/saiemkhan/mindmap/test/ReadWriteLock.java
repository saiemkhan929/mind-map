package com.saiemkhan.mindmap.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {


    public static void main(String[] args) {
        Typer t = new Typer();

        Runnable writerTask = new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    t.writeCount();
                }
            }
        };

        Runnable readerTask = new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    t.readCount();
                }
            }
        };

        Thread writer = new Thread(writerTask);
        Thread reader1 = new Thread(readerTask);
        Thread reader2 = new Thread(readerTask);

        writer.setName("Writer");
        reader1.setName("Reader 1");
        reader2.setName("Reader 2");

        writer.start();
        reader1.start();
        reader2.start();


    }
}

class Typer {
    int count = 0;
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    Lock readLock = lock.readLock();
    Lock writeLock = lock.writeLock();

    void readCount(){
        readLock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + " reading value " + count);
        }finally {
            readLock.unlock();
        }

    }

    void writeCount(){
        writeLock.lock();
        try{
            count++;
            System.out.println(Thread.currentThread().getName() + " written value " + count);
        }finally {
            writeLock.unlock();
        }
    }

}