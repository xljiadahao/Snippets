package com.snippet.concurrent;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * Simulate m runners, run n times, the final winner is the one who win most of the n times.
 * CyclicBarrier await is implemented with ReentrantLock and Condition, queue implicitly ordered.
 * 
 * CyclicBarrier vs CountDownLatch
 * a. in general, threads groups are waiting for each other, use CountDownLatch;
 * within a group of threads, each thread wait for each other, use CyclicBarrier.
 * b. CountDownLatch is counting --, while CyclicBarrier is counting ++.
 * c. CountDownLatch can be used once when the count reached 0 and cannot be reset, 
 * while CyclicBarrier can be reset each time, used multiple times.
 * 
 * 
 * @author xulei
 *
 */
public class RaceBarrier {

    private int currentTime = 0;
    private int maximumScore = 0;
    private boolean isOngoing = true;
    private Map<String, Integer> scoreboard = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        new RaceBarrier().race(3,4);
    }

    private  void race(int mRunner, int nTimes) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(mRunner, () -> {
            System.out.println("CyclicBarrier executed, round " + ++currentTime);
            isOngoing = true;
            if (currentTime >= nTimes) {
                scoreboard.entrySet().stream().sorted((Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) 
                        -> o1.getValue() == o2.getValue() ? 0 : (o1.getValue() > o2.getValue() ? -1 : 1))
                    .forEach((Map.Entry<String, Integer> runner) -> {
                        if (runner.getValue() >= maximumScore) {
                            maximumScore = runner.getValue();
                            System.out.println("Announce Final Winner: " + runner.getKey() + ", winning times: " + runner.getValue());
                        } 
                    });
            }
        });
        for (int i = 0; i < mRunner; i++) {
            new Thread(()->{
                scoreboard.put(Thread.currentThread().getName(), 0);
                while (currentTime < nTimes) {
                    try {
                        Thread.sleep(new Random().nextInt(5) * 1000);
                        System.out.println(Thread.currentThread().getName() + " reached barrier.");
                        updateWinnerStatus(Thread.currentThread().getName());
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }, "runner" + i).start(); 
        }
    }

    // pessimism lock
    private  synchronized void updateWinnerStatus(String runner) {
        if (isOngoing) {
            scoreboard.put(runner, scoreboard.get(runner) + 1);
            System.out.println("winner: " + runner);
            isOngoing = false;
        }
    }

}
