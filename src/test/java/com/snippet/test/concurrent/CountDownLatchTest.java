package com.snippet.test.concurrent;

import com.snippet.concurrent.Latch;
import com.snippet.concurrent.Player;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author xulei
 */
public class CountDownLatchTest {
    
    private Latch latch;
    
    @Before
    public void setup() {
        latch = new Latch(4);
    }
    
    /**
     * Test Sample Result
     * 
     * Game created, waiting for players...
     * Player [4,Suwandy] get into the game room. Delay 4 seconds...
     * Player [3,Pingxia] get into the game room. Delay 5 seconds...
     * Player [1,Xu Lei] get into the game room. Delay 9 seconds...
     * Player [2,Paris] get into the game room. Delay 9 seconds...
     * All of the players here, game starts.
     */
    @Test
    public void test4Players() throws InterruptedException {
        System.out.println("Game created, waiting for players...");
        new Player(latch, "Xu Lei").start();
        new Player(latch, "Paris").start();
        new Player(latch, "Pingxia").start();
        new Player(latch, "Suwandy").start();
        latch.await();
        System.out.println("All of the players here, game starts.");
    }
    
}
