package tech.aircastle.text;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by djsmiley on 6/2/18.
 */
public class WordTransitionCounterTests {

    @Test
    public void testAddCount() throws Exception {
        WordTransitionCounter wordTransitionCounter = new WordTransitionCounter();

        Integer expected = 0;
        Integer actual = wordTransitionCounter.getCount("first", "second");
        assertEquals(expected, actual);

        wordTransitionCounter.observe("first", "second");

        expected = 1;
        actual = wordTransitionCounter.getCount("first", "second");
        assertEquals(expected, actual);

        wordTransitionCounter.observe("first", "second");

        expected = 2;
        actual = wordTransitionCounter.getCount("first", "second");
        assertEquals(expected, actual);

        wordTransitionCounter.observe("first", "second");

        expected = 3;
        actual = wordTransitionCounter.getCount("first", "second");
        assertEquals(expected, actual);

        expected = 0;
        actual = wordTransitionCounter.getCount("first", "missing");
        assertEquals(expected, actual);

        wordTransitionCounter.observe("first", "missing");

        expected = 1;
        actual = wordTransitionCounter.getCount("first", "missing");
        assertEquals(expected, actual);

        wordTransitionCounter.observe("first", "missing");

        expected = 2;
        actual = wordTransitionCounter.getCount("first", "missing");
        assertEquals(expected, actual);

        wordTransitionCounter.observe("first", "missing");

        expected = 3;
        actual = wordTransitionCounter.getCount("first", "missing");
        assertEquals(expected, actual);

        expected = 0;
        actual = wordTransitionCounter.getCount("missing1", "missing2");
        assertEquals(expected, actual);

        wordTransitionCounter.observe("missing1", "missing2");

        expected = 1;
        actual = wordTransitionCounter.getCount("missing1", "missing2");
        assertEquals(expected, actual);

        wordTransitionCounter.observe("missing1", "missing3");

        expected = 1;
        actual = wordTransitionCounter.getCount("missing1", "missing3");
        assertEquals(expected, actual);

        wordTransitionCounter.observe("missing1", "missing2");

        expected = 2;
        actual = wordTransitionCounter.getCount("missing1", "missing2");
        assertEquals(expected, actual);

        wordTransitionCounter.observe("missing1", "missing2");

        expected = 3;
        actual = wordTransitionCounter.getCount("missing1", "missing2");
        assertEquals(expected, actual);

    }

    @Test
    public void testGetNormalizedCount() {
        WordTransitionCounter wordTransitionCounter = new WordTransitionCounter();

        Double expected = 0.0;
        Double actual = wordTransitionCounter.getNormalizedCount("first", "second");
        assertEquals(expected, actual);

        wordTransitionCounter.observe("first", "second");

        expected = 1.0;
        actual = wordTransitionCounter.getNormalizedCount("first", "second");
        assertEquals(expected, actual);

        wordTransitionCounter.observe("first", "third");

        expected = 0.5;
        actual = wordTransitionCounter.getNormalizedCount("first", "second");
        assertEquals(expected, actual);

        wordTransitionCounter.observe("first", "fourth");
        wordTransitionCounter.observe("first", "fifth");

        expected = 0.25;
        actual = wordTransitionCounter.getNormalizedCount("first", "second");
        assertEquals(expected, actual);
    }

    @Test
    public void testGetWordTransitionCount() throws Exception {
        WordTransitionCounter counter = new WordTransitionCounter();

        counter.observe("first", "second");
        counter.observe("first", "second");
        counter.observe("first", "second");
        counter.observe("first", "third");
        counter.observe("fourth", "fifth");

        Map<String, Map<String, Integer>> wordTransitionCount = new HashMap<>();
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("second", 3);
        countMap.put("third", 1);
        wordTransitionCount.put("first", countMap);

        Map<String, Integer> anotherCountMap = new HashMap<>();
        anotherCountMap.put("fifth", 1);
        wordTransitionCount.put("fourth", anotherCountMap);

        assertEquals(wordTransitionCount, counter.getWordTransitionCount());
    }

    @Test
    public void testGetNormalizedWordTransitionCount() throws Exception {
        WordTransitionCounter counter = new WordTransitionCounter();

        counter.observe("first", "second");
        counter.observe("first", "second");
        counter.observe("first", "second");
        counter.observe("first", "third");
        counter.observe("fourth", "fifth");
        counter.observe("fourth", "sixth");

        Map<String, Map<String, Double>> wordTransitionCount = new HashMap<>();
        Map<String, Double> countMap = new HashMap<>();
        countMap.put("second", 0.75);
        countMap.put("third", 0.25);
        wordTransitionCount.put("first", countMap);

        Map<String, Double> anotherCountMap = new HashMap<>();
        anotherCountMap.put("fifth", 0.5);
        anotherCountMap.put("sixth", 0.5);
        wordTransitionCount.put("fourth", anotherCountMap);

        assertEquals(wordTransitionCount, counter.getNormalizedWordTransitionCount());
    }
}
