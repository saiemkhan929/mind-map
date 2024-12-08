package com.saiemkhan.mindmap.test;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

class ThreadExtended extends Thread {
    public ThreadExtended(Runnable runnable) {
        super(runnable);
    }
    @Getter @Setter
    private boolean hasAlreadyRead = false;
}

@Getter
@Setter
class SharedResource {
    private int counter = 0;
    private ArrayList<ThreadExtended> consumers= new ArrayList<>();

    void setConsumer(ThreadExtended thread) {
        consumers.add(thread);
    }

    boolean shouldProduce() {
        for (ThreadExtended consumer : consumers) {
            if(!consumer.isHasAlreadyRead()) return false;
        }

        return true;
    }

    boolean hasCurrentThreadAlreadyRead(Thread thread) {
        for (ThreadExtended consumer : consumers) {
            System.out.println(consumer.equals(thread));
            if(consumer.equals(thread)){
                return consumer.isHasAlreadyRead();
            }
        }
        return false;
    }

    void setReadingStatusToTrue(Thread thread) {
        for (ThreadExtended consumer : consumers) {
            if(consumer.equals(thread)){
                consumer.setHasAlreadyRead(true);
            }
        }
    }

    void resetReadingStatus() {
        for (ThreadExtended consumer : consumers) {
            consumer.setHasAlreadyRead(false);
        }
    }
}

class Producer{
    private final SharedResource sharedResource;

    public Producer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void produce(int i) {

        synchronized(sharedResource) {
            while (!sharedResource.shouldProduce()){
                try {
                    sharedResource.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(Thread.currentThread().getName()+" produced: "+i);
            sharedResource.setCounter(i);
            sharedResource.resetReadingStatus();
            sharedResource.notifyAll();
        }
    }
}

class Consumer{
    private final SharedResource sharedResource;

    public Consumer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    public void consume(){
        synchronized (sharedResource) {
            while (!sharedResource.hasCurrentThreadAlreadyRead(Thread.currentThread())){
                try {
                    sharedResource.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(Thread.currentThread().getName()+" consumed: " + sharedResource.getCounter());
            sharedResource.setReadingStatusToTrue(Thread.currentThread());
            sharedResource.notifyAll();
        }
    }
}



public class ThreadCommunication {

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        Producer producer = new Producer(sharedResource);
        Consumer consumer = new Consumer(sharedResource);

        Thread producerThread = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                producer.produce(i);
            }
        });

        Runnable consumerRunner = ()->{
            for (int i = 0; i < 10; i++) {
                consumer.consume();
            }
        };

        ThreadExtended consumerThread1 = new ThreadExtended(consumerRunner);
        ThreadExtended consumerThread2 = new ThreadExtended(consumerRunner);

        producerThread.setName("Producer");
        consumerThread1.setName("Consumer 1");
        consumerThread1.setName("Consumer 2");

        // set threads in shared memory
        sharedResource.setConsumer(consumerThread1);
        sharedResource.setConsumer(consumerThread2);

        consumerThread1.start();
        consumerThread2.start();
        producerThread.start();
    }
}
