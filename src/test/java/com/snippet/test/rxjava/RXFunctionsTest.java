package com.snippet.test.rxjava;

import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * 3. RX Marble Diagram: http://rxmarbles.com/
 * Composable Functions and Categories:
 * Transform - Map, FlatMap, Reduce, Scan, etc.
 * Filter - Take, Skip, Sample, TakeWhile, Filter
 * Combine - Concat, Merge, Zip, CombineLatest, Multicast, Publish, Cache, RefCount
 * Concurrency - ObserveOn, SubscribeOn
 * Error Handling - OnErrorReturn, OnErrorResume
 * 
 * 
 * @author xulei
 */
public class RXFunctionsTest {
    
    private TestSubscriber<Object> testSubscriber;
    
    @Before
    public void setup() {
        this.testSubscriber = new TestSubscriber<>();
    }
    
    @Test
    public void testMap() {
        String someStrings[] = {"Hello", " ", "World","!"};
        Observable.from(someStrings)
                .doOnNext((s) -> System.out.println("doOnNext: " + s))
                .map((s) -> s + s.length())
                .doOnCompleted(() -> System.out.println("testMap doOnCompleted"))
                .subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.getOnNextEvents())
                .hasSize(4)
                .containsOnlyOnce("Hello5")
                .containsOnlyOnce(" 1")
                .containsOnlyOnce("World5")
                .containsOnlyOnce("!1");
    }

    @Test
    public void testMapExceptionResume() {
        String someStrings[] = {"Hello", " ", "World","!"};
        Observable.from(someStrings)
                .map((s) -> s.substring(3))
                .onExceptionResumeNext(Observable.just("word"))
                .doOnCompleted(() -> System.out.println("testMapExceptionResume doOnCompleted"))
                .subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.getOnNextEvents())
                .hasSize(2)
                .containsOnlyOnce("lo")
                .containsOnlyOnce("word");
    }
    
    @Test
    public void testMapError() {
        String someStrings[] = {"Hello", " ", "World","!"};
        Observable.from(someStrings)
                .map((s) -> s.substring(3))
                .doOnError((r) -> System.out.println("testMapError doOnError, " + r.getMessage() + ", " + r.getClass().getName()))
                .doOnCompleted(() -> System.out.println("testMapError doOnCompleted"))
                .subscribe(testSubscriber);
        testSubscriber.assertError(StringIndexOutOfBoundsException.class);
    }
    
    @Test
    public void testFilter() {
        String someStrings[] = {"Hello", " ", "World","!"};
        Observable.from(someStrings)
                .filter((s) -> s.length() < 3)
                .doOnCompleted(() -> System.out.println("testFilter doOnCompleted"))
                .subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.getOnNextEvents())
                .hasSize(2)
                .containsOnlyOnce(" ")
                .containsOnlyOnce("!");
    }
    
    @Test
    public void testMerge() {
        String someStrings[] = {"Hello", " ", "World","!"};
        String mergeStrings[] = {"PayPal", "AntFin", "WeChat"};
        Observable.from(someStrings)
                .mergeWith(Observable.from(mergeStrings))
                .doOnNext((s) -> System.out.println("doOnNext: " + s))
                .doOnCompleted(() -> System.out.println("testMerge doOnCompleted"))
                .subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.getOnNextEvents())
                .hasSize(7)
                .containsOnlyOnce("Hello")
                .containsOnlyOnce(" ")
                .containsOnlyOnce("World")
                .containsOnlyOnce("!")
                .containsOnlyOnce("PayPal")
                .containsOnlyOnce("AntFin")
                .containsOnlyOnce("WeChat");
    }
    
    @Test
    public void testZipReduce() {
        String someStrings[] = {"US", "China", "World"};
        String zipStrings[] = {"PayPal", "AntFin", "WeChat"};
        Observable.from(someStrings)
                .zipWith(Observable.from(zipStrings), (someString, zipString) -> String.format("%s: %s", zipString, someString))
                .doOnNext((s) -> System.out.println("doOnNext zip: " + s))
                .reduce((before, after) -> String.format("%s, %s", before, after))
                .doOnNext((s) -> System.out.println("doOnNext reduece: " + s))
                .doOnCompleted(() -> System.out.println("testZipReduce doOnCompleted"))
                .subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.getOnNextEvents())
                .hasSize(1)
                .containsOnlyOnce("PayPal: US, AntFin: China, WeChat: World");
    }
    
    @Test
    public void testGroupBy() {
        String someStrings[] = {"Alipay", "AliExpress",  "US", "China", "Singapore"};
        Observable.from(someStrings)
                .groupBy((someString) -> someString.contains("Ali") ? "Alibaba" : someString)
                .map((group) -> group.getKey())
                .doOnNext((key) -> System.out.println("doOnNext: " + key))
                .doOnCompleted(() -> System.out.println("testGroupBy doOnCompleted"))
                .subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.getOnNextEvents())
                .hasSize(4)
                .containsOnlyOnce("Alibaba")
                .containsOnlyOnce("Singapore")
                .containsOnlyOnce("China")
                .containsOnlyOnce("US");
    }
    
    @Test
    public void testCollect() {
        String someStrings[] = {"Alipay", "AliExpress", "US", "China", "Singapore"};
        Observable.from(someStrings)
                .groupBy((someString) -> someString.contains("Ali") ? "Alibaba" : someString)
                .map((group) -> group.getKey())
                .collect(() -> new ArrayList<String>(), (state, item) -> state.add(item)) 
                // [Alibaba, AliExpress, US, China, Singapore]
                .doOnCompleted(() -> System.out.println("testGroupBy doOnCompleted"))
                .subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.getOnNextEvents()).hasSize(1);
    }
    
    @Test
    public void testFromVsJust() {
        String someStrings[] = {"Alipay", "AliExpress", "US", "China", "Singapore"};
        // from: stream the elements as the pipeline by pushing each element
        testSubscriber = new TestSubscriber<>();
        Observable.from(someStrings).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.getOnNextEvents()).hasSize(5);
        // just: stream the elements as the pipeline by pushing with the single element
        testSubscriber = new TestSubscriber<>();
        Observable.just(someStrings).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.getOnNextEvents()).hasSize(1);
    }
    
}
