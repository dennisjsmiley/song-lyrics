package tech.aircastle.text;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by djsmiley on 6/2/18.
 */
public class WordCounterTests {

    @Test
    public void testAddCount() throws Exception {
        WordCounter wordCounter = new WordCounter();

        Integer expected = 0;
        Integer actual = wordCounter.getCount("first", "second");
        assertEquals(expected, actual);

        wordCounter.addCount("first", "second");

        expected = 1;
        actual = wordCounter.getCount("first", "second");
        assertEquals(expected, actual);

        wordCounter.addCount("first", "second");

        expected = 2;
        actual = wordCounter.getCount("first", "second");
        assertEquals(expected, actual);

        wordCounter.addCount("first", "second");

        expected = 3;
        actual = wordCounter.getCount("first", "second");
        assertEquals(expected, actual);

        expected = 0;
        actual = wordCounter.getCount("first", "missing");
        assertEquals(expected, actual);

        wordCounter.addCount("first", "missing");

        expected = 1;
        actual = wordCounter.getCount("first", "missing");
        assertEquals(expected, actual);

        wordCounter.addCount("first", "missing");

        expected = 2;
        actual = wordCounter.getCount("first", "missing");
        assertEquals(expected, actual);

        wordCounter.addCount("first", "missing");

        expected = 3;
        actual = wordCounter.getCount("first", "missing");
        assertEquals(expected, actual);

        expected = 0;
        actual = wordCounter.getCount("missing1", "missing2");
        assertEquals(expected, actual);

        wordCounter.addCount("missing1", "missing2");

        expected = 1;
        actual = wordCounter.getCount("missing1", "missing2");
        assertEquals(expected, actual);

        wordCounter.addCount("missing1", "missing3");

        expected = 1;
        actual = wordCounter.getCount("missing1", "missing3");
        assertEquals(expected, actual);

        wordCounter.addCount("missing1", "missing2");

        expected = 2;
        actual = wordCounter.getCount("missing1", "missing2");
        assertEquals(expected, actual);

        wordCounter.addCount("missing1", "missing2");

        expected = 3;
        actual = wordCounter.getCount("missing1", "missing2");
        assertEquals(expected, actual);

    }
}
