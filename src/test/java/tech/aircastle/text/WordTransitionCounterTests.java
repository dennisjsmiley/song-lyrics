package tech.aircastle.text;

import org.junit.Test;

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
}
