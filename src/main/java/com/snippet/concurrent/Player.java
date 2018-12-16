package com.snippet.concurrent;

import java.util.Random;
/**
 *
 * @author xulei
 */
public class Player extends Thread {
    
    private Latch latch;
    private String name;
    private final int id;
    
    public Player(Latch latch, String name) {
        this.latch = latch;
        this.name = name;
        id = Latch.count++;
    }
    
    @Override
    public void run() {
        Random r = new Random();
        int delay = r.nextInt(10);
        try {
            Thread.sleep(Long.valueOf(String.valueOf(delay*1000)));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Poker.card1.set(r.nextInt(10));
        Poker.card2.set(r.nextInt(10));
        System.out.println("Player [" + id + "," + name + "] get into the game room. "
                + "Delay " + delay + " seconds... "
                + "Poker: " + Poker.card1.get() + " and " + Poker.card2.get());
        Poker.sharedResult.put(name, Poker.card1.get()*Poker.card2.get());
        latch.countDown();
        Poker.card1.remove();
        Poker.card2.remove();
    } 
    
}
