package com.saiemkhan.mindmap.test;


public class DeadLock {
    public static void main(String[] args) {
        Pen pen = new Pen();
        Paper paper = new Paper();

        Thread task1 = new Thread() {
            @Override
            public void run() {
                pen.writeWithPenAndPaper(paper);
            }
        };

        Thread task2 = new Thread() {
            @Override
            public void run() {
                paper.writeWithPenAndPaper(pen);
            }
        };

        task1.start();
        task2.start();
    }



}

class Pen{
    void writeWithPenAndPaper(Paper p){
        synchronized (p){
            System.out.println("Pen is active");
            System.out.println("Pen is trying to acquire paper");
            p.finishWriting();

        }
    }

    synchronized void finishWriting(){
        System.out.println("finished using Pen");
    }
}

class Paper{
    synchronized void writeWithPenAndPaper(Pen p){
        System.out.println("Paper is active");
        System.out.println("Paper is trying to acquire pen");
        p.finishWriting();
    }

    synchronized void finishWriting(){
        System.out.println("finished using Paper");
    }
}