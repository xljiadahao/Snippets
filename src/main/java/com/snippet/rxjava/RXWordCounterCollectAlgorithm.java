package com.snippet.rxjava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import rx.Observable;

/**
 *
 * @author xulei
 */
public class RXWordCounterCollectAlgorithm implements RXAlgorithm {

    @Override
    public Map<String, Integer> applyAlgorithm(String[] source) {
        Map<String, Integer> counters = new HashMap<>();
        Observable.from(source)
                .flatMap(word -> Observable.from(word.split(" ")))
                .collect(() -> new ArrayList<String>(), (state, item) -> state.add(item))
                .finallyDo(() -> System.out.println("---------RXWordCounterCollectAlgorithm done---------"))
                .subscribe((collect) -> {
                    collect.stream().forEach((key) -> {
                        if (counters.get(key) == null) {
                            counters.put(key, 1);
                        } else {
                            counters.put(key, counters.get(key) + 1);
                        }
                    });
                });
        return counters;
    }

}