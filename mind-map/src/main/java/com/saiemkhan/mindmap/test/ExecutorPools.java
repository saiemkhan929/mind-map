package com.saiemkhan.mindmap.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorPools {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Callable<Double>> allFutures= new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            long num = Math.round(Math.random() * 50);
            allFutures.add(()->fibonacci(num));
        }

        try {
            List<Future<Double>> futureList = executorService.invokeAll(allFutures);
            for (Future<Double> future : futureList) {
                System.out.println(future.get());
            }
        } catch (ExecutionException | InterruptedException | CancellationException e) {

        }

        executorService.shutdown();
        System.out.println(executorService.isShutdown());


    }


    public static double fibonacci(long n) {
        double result = 1;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
