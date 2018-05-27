package tech.aircastle.util;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by djsmiley on 5/27/18.
 */
public class LyricsUtilTests {

    @Test
    public void testTokenize() {
        String text = "Word1 Word2 Word3";
        assertEquals(Arrays.asList("Word1", "Word2", "Word3"), LyricsUtil.tokenize(text));
    }

    @Test
    public void testComputeWordCount() {
        String text = "Word1 Word2 Word3";

        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("Word1", 1);
        expectedResult.put("Word2", 1);
        expectedResult.put("Word3", 1);

        assertEquals(expectedResult, LyricsUtil.computeWordCount(text));
    }
}
