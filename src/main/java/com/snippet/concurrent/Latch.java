package com.snippet.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Usage: There is the sequence between multiple groups of threads.
 * 
 * Example: One account has multiple parties; 
 * based on account to party relationship, 
 * load all the parties of the account concurrently as the first group of threads;
 * afterwards, once it has all the party id, 
 * load all the parties info concurrently as the second group of threads;
 * Finally, we can get all the info related to the account for evaluation.
 * 
 * @author xulei
 */
public class Latch {
    
    public static int count = 1;
    private final CountDownLatch latch;
    
    public Latch (int count) {
        this.latch = new CountDownLatch(count);
    }
    
    public void countDown() {
        latch.countDown();
    }
    
    public void await() throws InterruptedException {
        latch.await();
    }
    
}
