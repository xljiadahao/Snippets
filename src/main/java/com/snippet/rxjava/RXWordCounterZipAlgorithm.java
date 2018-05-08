package com.snippet.rxjava;

import java.util.HashMap;
import java.util.Map;
import rx.Observable;
import rx.observers.TestSubscriber;

/**
 *
 * @author xulei
 */
public class RXWordCounterZipAlgorithm implements RXAlgorithm {

    public TestSubscriber<Object> testSubscriber;
    
    public RXWordCounterZipAlgorithm(TestSubscriber<Object> testSubscriber) {
        this.testSubscriber = testSubscriber;
    }
    
    @Override
    public Map<String, Integer> applyAlgorithm(String[] source) {
        Map<String, Integer> counters = new HashMap<>();
        Observable.from(source)
                .flatMap(word -> Observable.from(word.split(" ")))
		.zipWith(Observable.range(1, Integer.MAX_VALUE),
		        (string, count) -> {
                            if (counters.get(string) == null) {
                                counters.put(string, 1);
                            } else {
                                counters.put(string, counters.get(string) + 1);
                            }
                            return String.format("%2d. %s", count, string);
                        })
                .finallyDo(() -> System.out.println("---------RXWordCounterZipAlgorithm done---------"))
		.subscribe(testSubscriber);
        return counters;
    }

}