package com.snippet.test.concurrent;

import com.snippet.concurrent.Latch;
import com.snippet.concurrent.Player;
import com.snippet.concurrent.Poker;
import org.apache.commons.lang.ArrayUtils;
import static org.junit.Assert.assertTrue;
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
     * Player [2,Paris] get into the game room. Delay 0 seconds... Poker: 2 and 2
     * Player [4,Suwandy] get into the game room. Delay 1 seconds... Poker: 4 and 1
     * Player [3,Pingxia] get into the game room. Delay 2 seconds... Poker: 4 and 4
     * Player [1,Xu Lei] get into the game room. Delay 8 seconds... Poker: 5 and 5
     * All of the 4 players are here. Game starts.
     * Current main thread, card1: null, card2: null
     * Winner: Xu Lei
     */
    @Test
    public void test4Players() throws InterruptedException {
        System.out.println("Game created, waiting for players...");
        new Player(latch, "Xu Lei").start();
        new Player(latch, "Paris").start();
        new Player(latch, "Pingxia").start();
        new Player(latch, "Suwandy").start();
        latch.await();
        System.out.println("All of the " + Poker.sharedResult.size() + " players are here. Game starts.");
        assertTrue("The number of result entry should be 4.", Poker.sharedResult.size() == 4);
        System.out.println("Current main thread, card1: " + Poker.card1.get() + ", card2: " + Poker.card2.get());
        assertTrue("ThreadLocal variable card1 in main thread should be null.", Poker.card1.get() == null);
        assertTrue("ThreadLocal variable card2 in main thread should be null.", Poker.card2.get() == null);
        System.out.println("Winner: " + Poker.getWinner());
        assertTrue("Winner should be one of the players.", 
                ArrayUtils.contains(new String[]{"Xu Lei", "Paris", "Pingxia", "Suwandy"}, Poker.getWinner()));
    }
    
}
