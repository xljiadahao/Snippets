package com.snippet.rxjava;

import java.util.HashMap;
import java.util.Map;
import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * 1. Observer vs Observable
 * What makes up a stream in RxJava is a chain of observers and observable.
 * Observable: Produces data/events that others may have interest in.
 * Observer: Consumes data/events from Observable.
 * 
 * interface Observer<T> {
 *     void onCompleted();
 *     void onError(java.lang.Throwable e);
 *     void onNext(T t);
 * }
 * 
 * 2. RxJava Stream
 * Create a stream of Observable from a collection
 * Observable.from(someCollection)
 * Create a stream of Observable from a single value
 * Observable.just(someValue)
 * Create your own implementation of Observable
 * Observable.create(someDefinitionOfObservable)
 * 
 * Java 8 Stream vs RxJava Stream
 * Java 8 Stream:
 * Iterable<String> (pull), getDataFromLocalMemorySync().skip(10).take(5).map(...).forEach(...); //sync
 * Reads all elements in local memory 
 * (e.g., say we had 200 elements, all 200 would be read before passing it on to skip.
 * RxJava Stream (lazy processing):
 * Observable<String> (push), getDataFromAsyncSource().skip(10).take(5).map(...).subscribe(...); //async
 * Reads (typically) only 15 items (the first 10 we skipped and the next 5 we pass on to map)
 * 
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