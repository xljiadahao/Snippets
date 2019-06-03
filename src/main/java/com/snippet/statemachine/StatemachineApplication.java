package com.snippet.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;

/**
 * Output:
 * --- User A insert a coin ---
 * Payment done, UNLOCK for visitor pass
 * LOG current state: UNLOCKED
 * --- User A is passing the turnstile ---
 * Visitor passed, LOCK for next visitor's payment
 * LOG current state: LOCKED
 * --- User B scan the ez-link card for MRT ---
 * Payment done, UNLOCK for visitor pass
 * LOG current state: UNLOCKED
 * --- User B is passing the turnstile ---
 * Visitor passed, LOCK for next visitor's payment
 * LOG current state: LOCKED
 * 
 * @author xulei
 */
public class StatemachineApplication {
    
    // @Autowired
    private static StateMachine<TurnstileStates, TurnstileEvents> stateMachine;
    
    static {
        try {
            stateMachine = new StatemachineConfigurer().buildMachine();
        } catch (Exception ex) {
            System.out.println("ERROR, build statemachine failed, " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        stateMachine.start();
        System.out.println("--- User A insert a coin ---");
        stateMachine.sendEvent(TurnstileEvents.COIN);
        System.out.println("LOG current state: " + stateMachine.getState().getId().name());
        System.out.println("--- User A is passing the turnstile ---");
        stateMachine.sendEvent(TurnstileEvents.PUSH);
        System.out.println("LOG current state: " + stateMachine.getState().getId().name());
        System.out.println("--- User B scan the ez-link card for MRT ---");
        stateMachine.sendEvent(TurnstileEvents.SCAN);
        System.out.println("LOG current state: " + stateMachine.getState().getId().name());
        System.out.println("--- User B is passing the turnstile ---");
        stateMachine.sendEvent(TurnstileEvents.PUSH);
        System.out.println("LOG current state: " + stateMachine.getState().getId().name());
        stateMachine.stop();
    }

}
