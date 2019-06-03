package com.snippet.statemachine;

import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

/**
 *
 * @author xulei
 */
@WithStateMachine(id = "Turnstile Statemachine")
public class StatemachineMinitor {
    
    @OnTransition
    public void anyTransition() {
        System.out.println("--- StatemachineMinitor - OnTransition ---");
    }
    
    @OnTransition(target = "UNLOCKED")
    public void toUnlocked() {
        System.out.println("--- StatemachineMinitor - toUnlocked - LOCKED to UNLOCKED ---");
    }
    
    @OnStateChanged(source = "UNLOCKED")
    public void fromUnlocked() {
        System.out.println("--- StatemachineMinitor - fromUnlocked - UNLOCKED to LOCKED ---");
    }
    
}
