package com.snippet.rxjava;

import java.util.Map;

/**
 *
 * @author xulei
 */
public class RXWordAccumulator {
    
    private final String[] source;
    private final RXAlgorithm rxAlgorithm;
    
    public RXWordAccumulator(String[] source, RXAlgorithm rxAlgorithm) {
        this.source = source;
        this.rxAlgorithm = rxAlgorithm;
    }
    
    public Map<String, Integer> wordAccumulate() {
        Map<String, Integer> counters = rxAlgorithm.applyAlgorithm(source);
        System.out.println("-------WORD COUNT, result of accumulator-------");
        counters.keySet().stream().forEach((key) -> {
            System.out.println(key + ": " + counters.get(key));
        });
        return counters;
    }

}