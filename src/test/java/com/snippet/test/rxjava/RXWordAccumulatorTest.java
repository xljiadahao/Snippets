package com.snippet.test.rxjava;

import com.snippet.rxjava.RXWordAccumulator;
import com.snippet.rxjava.RXWordCounterCollectAlgorithm;
import com.snippet.rxjava.RXWordCounterZipAlgorithm;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import rx.observers.TestSubscriber;

/**
 *
 * @author xulei
 */
public class RXWordAccumulatorTest {
    
    private TestSubscriber<Object> testSubscriber;
    private String[] words;

    @Before
    public void setup() {
        this.testSubscriber = new TestSubscriber<>();
        words = new String[] {"Java 8", 
                "RX Java", 
                "Enterprise Java", 
                "Hexo", 
                "Seamless Login", 
                "Seamless Payment", 
                "Super App", 
                "Alipay Little Program", 
                "Wechat Little Program", 
                "Json Web Token", 
                "Soft Token", 
                "PayPal Payment", 
                "Apple Pay Payment", 
                "Android Pay Payment", 
                "Alipay Payment", 
                "Wechat Pay Payment", 
                "Visa Payment", 
                "MasterCard Payment", 
                "UnionPay Payment"};
    }
    
    @Test
    public void testWordAccumulatorZipAlgorithm() {
        RXWordAccumulator accumulator = 
                new RXWordAccumulator(words, new RXWordCounterZipAlgorithm(testSubscriber));
        Map<String, Integer> counters = accumulator.wordAccumulate();
        testSubscriber.assertNoErrors();
        assertThat(testSubscriber.getOnNextEvents())
                .hasSize(43)
                .containsOnlyOnce("11. Payment")
                .containsOnlyOnce("14. Alipay")
                .containsOnlyOnce("25. PayPal")
                .containsOnlyOnce("43. Payment");
        assertTrue("word size mismatch", counters.keySet().size() == 25);
        assertTrue("word PayPal count mismatch", counters.get("PayPal") == 1);
        assertTrue("word Alipay count mismatch", counters.get("Alipay") == 2);
        assertTrue("word Payment count mismatch", counters.get("Payment") == 9);
    }
    
    @Test
    public void testWordAccumulatorCollectAlgorithm() {
        RXWordAccumulator accumulator = 
                new RXWordAccumulator(words, new RXWordCounterCollectAlgorithm());
        Map<String, Integer> counters = accumulator.wordAccumulate();
        assertTrue("word size mismatch", counters.keySet().size() == 25);
        assertTrue("word PayPal count mismatch", counters.get("PayPal") == 1);
        assertTrue("word Alipay count mismatch", counters.get("Alipay") == 2);
        assertTrue("word Payment count mismatch", counters.get("Payment") == 9);
    }

}