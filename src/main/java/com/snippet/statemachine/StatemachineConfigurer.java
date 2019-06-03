package com.snippet.statemachine;

import java.util.EnumSet;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

/**
 *
 * @author xulei
 */
@Configuration
@EnableStateMachineFactory
public class StatemachineConfigurer extends 
        EnumStateMachineConfigurerAdapter<TurnstileStates, TurnstileEvents> {

    @Override
    public void configure(StateMachineTransitionConfigurer<TurnstileStates, TurnstileEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(TurnstileStates.LOCKED)
                .event(TurnstileEvents.SCAN)
                .target(TurnstileStates.UNLOCKED)
                .action(turnstileUnlock())
                .and()
                .withExternal()
                .source(TurnstileStates.LOCKED)
                .event(TurnstileEvents.COIN)
                .target(TurnstileStates.UNLOCKED)
                .action(turnstileUnlock())
                .and()
                .withExternal()
                .source(TurnstileStates.UNLOCKED)
                .event(TurnstileEvents.PUSH)
                .target(TurnstileStates.LOCKED)
                .action(customerPassAndLock());
    }

    @Override
    public void configure(StateMachineStateConfigurer<TurnstileStates, TurnstileEvents> states) throws Exception {
        states.withStates()
                .initial(TurnstileStates.LOCKED)
                .states(EnumSet.allOf(TurnstileStates.class));
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<TurnstileStates, TurnstileEvents> config) throws Exception {
        config.withConfiguration().machineId("Turnstile Statemachine");
    }
    
    public StateMachine<TurnstileStates, TurnstileEvents> buildMachine() throws Exception {
        Builder<TurnstileStates, TurnstileEvents> builder = StateMachineBuilder.builder();
        builder.configureStates()
                .withStates()
                .initial(TurnstileStates.LOCKED)
                .states(EnumSet.allOf(TurnstileStates.class));
        
        builder.configureTransitions()
                .withExternal()
                .source(TurnstileStates.LOCKED)
                .event(TurnstileEvents.SCAN)
                .target(TurnstileStates.UNLOCKED)
                .action(turnstileUnlock())
                .and()
                .withExternal()
                .source(TurnstileStates.LOCKED)
                .event(TurnstileEvents.COIN)
                .target(TurnstileStates.UNLOCKED)
                .action(turnstileUnlock())
                .and()
                .withExternal()
                .source(TurnstileStates.UNLOCKED)
                .event(TurnstileEvents.PUSH)
                .target(TurnstileStates.LOCKED)
                .action(customerPassAndLock());
        
        builder.configureConfiguration()
                .withConfiguration()
                .machineId("Turnstile Statemachine");
        
        return builder.build();        
    }
    
    public Action<TurnstileStates, TurnstileEvents> turnstileUnlock() {
        return context -> System.out.println("Payment done, UNLOCK for visitor pass" );
    }
    
    public Action<TurnstileStates, TurnstileEvents> customerPassAndLock() {
        return context -> System.out.println("Visitor passed, LOCK for next visitor's payment" );
    }
}
