package com.snippet.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Players run against different threads, 
 * even there are two static variables, 
 * they will not be shared cross different threads.
 *
 * @author xulei
 */
public class Poker {
    
    public static ThreadLocal<Integer> card1 = new ThreadLocal<>();
    public static ThreadLocal<Integer> card2 = new ThreadLocal<>();
    
    public static Map<String, Integer> sharedResult = new HashMap<>();
    
    public static String getWinner() {
        String winner = null;
        Integer winnerScore = null;
        for (Entry<String, Integer> entry : sharedResult.entrySet()) {
            // those score equal cases, by lucky the one who comes first wins
            if (winnerScore == null || entry.getValue() > winnerScore) {
                winner = entry.getKey();
                winnerScore = entry.getValue();
            }
        }
        return winner;
    }
    
}
