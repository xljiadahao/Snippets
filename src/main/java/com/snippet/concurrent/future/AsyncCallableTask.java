package com.snippet.concurrent.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class AsyncCallableTask implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("calling downstream services..." + Thread.currentThread().getName());
        int count = 0;
        while(count < 10) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("calling downstream services interrupted, seconds: " + count);
                return "interrupted result, seconds: " + count;
            }
            Thread.sleep(1000);
            count++;
            System.out.println("calling downstream services counting: " + count + ". " + Thread.currentThread().getName());
        }
        return "getting result from downstream services, seconds: " + count;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        AsyncCallableTask task = new AsyncCallableTask();
        FutureTask<String> futureTask = new FutureTask<>(task);
        new Thread(futureTask).start();
        boolean isDone = futureTask.isDone();
        while(!isDone) {
            System.out.println("isDone: " + isDone + ", waiting 4 seconds for the downstream service call, do something else. "
                    + Thread.currentThread().getName());
            Thread.sleep(4000);
            isDone = futureTask.isDone();
            Random r = new Random();
            int randomTimeout = r.nextInt(10);
            boolean isTimeout = randomTimeout > 8 ? true : false;
            if (isTimeout) {
                System.out.println("timeout " + randomTimeout + ", cancel job.");
                futureTask.cancel(true);
                break;
            }
        }
        if (futureTask.isCancelled()) {
            System.out.println("cancelled, isDone: " + futureTask.isDone() + ". " + Thread.currentThread().getName());
        } else {
            System.out.println("isDone: " + futureTask.isDone() + ", result: " + futureTask.get() + ". " + Thread.currentThread().getName());
        }
    }

}
