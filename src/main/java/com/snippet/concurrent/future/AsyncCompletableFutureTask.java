package com.snippet.concurrent.future;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * why jdk8 CompletableFuture? 
 * Complex future tasks pipeline. IO tasks. Default thread pool ForkJoinPool, based CPU cores.
 * CPU密集任务: thread pool size based on CPU cores,
 * IO密集任务: thread pool size based on CPU cores * CPU利用率 * (1 + thread waiting time/thread occupied CPU time).
 * So in summary, ForkJoinPool cannot get the best performance in general, recommend to use customized thread pool.
 * 
 * a. whenComplete
 * b. thenApply
 * c. thenCompose
 * d. thenCombine
 * e. thenAccept
 * f. allOf (similar to CountDownLatch)
 * g. anyOf
 * 
 * @author xulei
 *
 */
public class AsyncCompletableFutureTask {

    public static void main(String[] args) {
        // 1. simple example
        System.out.println("------------CompletableFuture simple example-----------");
        // CompletableFuture<String> futureAsyncTask = new CompletableFuture<>();
        CompletableFuture<String> futureAsyncTask = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("do something");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // simulate exception in the assigned thread
            // int a = 1/0;
            return "ok";
        });
        System.out.println(futureAsyncTask.getNow("not_found"));
        System.out.println(futureAsyncTask.join());
        
        
        // 2. example use case
        System.out.println("------------CompletableFuture use case-----------");
        List<String> visitList = Arrays.asList("Lasha", "Linzhi", "Yangzhuoyongcuo", "Yaluzangbujiang");
        List<CompletableFuture<String>> futureAsyncTaskList = visitList.stream()
                .map((String visit) -> CompletableFuture.supplyAsync(() -> {
                    Random r = new Random();
                    int time = (r.nextInt(5) + 1) * 1000;
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return String.format("visit %s for %d hours, async task %s", visit, time/1000, Thread.currentThread().getName());}))
                .collect(Collectors.toList());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        futureAsyncTaskList.stream().map((CompletableFuture<String> task) -> task.getNow("not_ready")).forEach((String plan) -> System.out.println(plan));

        
        // 3. executors
        System.out.println("------------Executors use case-----------");
        ExecutorService es = Executors.newCachedThreadPool();
        List<Future<String>> futureList = visitList.stream()
                .map((String visit) -> es.submit(() -> {
                    Random r = new Random();
                    int time = (r.nextInt(5) + 1) * 1000;
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return String.format("visit %s for %d hours, async task %s", visit, time/1000, Thread.currentThread().getName());}))
                .collect(Collectors.toList());
        futureList.stream().map((Future<String> task) -> {
            try {
                return task.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }).forEach((String plan) -> System.out.println(plan));
        
        // 4. CompletableFuture advanced features
        System.out.println("------------CompletableFuture advanced features-----------");
        CompletableFuture<String> futureWhenCompleteTask0 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("do something futureWhenCompleteTask0");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Singapore";
        });
        CompletableFuture<String> futureWhenCompleteTask1 = futureWhenCompleteTask0.whenComplete((String result, Throwable e) -> {
            try {
                System.out.println("do something futureWhenCompleteTask1");
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println(String.format("now: %s -> future: China", result));
        });
        System.out.println("futureWhenCompleteTask1 final result: " + futureWhenCompleteTask1.join());
        CompletableFuture<String> futureWhenCompleteTask2 = futureWhenCompleteTask0.thenApply((String result) -> {
            try {
                System.out.println("do something futureWhenCompleteTask2");
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            System.out.println(String.format("now: %s -> future: China", result));
            return String.format("now: %s -> future: China", result);
        });
        System.out.println("futureWhenCompleteTask2 final result: " + futureWhenCompleteTask2.join());
        CompletableFuture<String> futureWhenCompleteTask3 = futureWhenCompleteTask0.thenCompose((String result) -> 
            CompletableFuture.supplyAsync(() -> result + " -> Chengdu"));
        System.out.println("futureWhenCompleteTask3 final result: " + futureWhenCompleteTask3.join());
        CompletableFuture<String> futureWhenCompleteTask4 = futureWhenCompleteTask2.thenCombine(
                CompletableFuture.supplyAsync(() -> "-Chengdu"), (String t2, String t4) -> t2 + t4);
        System.out.println("futureWhenCompleteTask4 final result: " + futureWhenCompleteTask4.join());
        // thenAccept, allOf (similar to CountDownLatch), anyOf
        
        System.exit(0);
    }

}
