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
        assertEquals(Arrays.asList("word1", "word2", "word3"), LyricsUtil.tokenize(text));
    }

    @Test
    public void testComputeWordCount() {
        String text = "Word1 Word2 Word3";

        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("word1", 1);
        expectedResult.put("word2", 1);
        expectedResult.put("word3", 1);

        assertEquals(expectedResult, LyricsUtil.computeWordCount(text));
    }

    @Test
    public void testComputeNormalizedWordCount() {
        String text = "Word1 Word2 Word3 Word4";

        Map<String, Double> expectedResult = new HashMap<>();
        expectedResult.put("word1", 0.25);
        expectedResult.put("word2", 0.25);
        expectedResult.put("word3", 0.25);
        expectedResult.put("word4", 0.25);

        assertEquals(expectedResult, LyricsUtil.computeNormalizedWordCount(text));
    }

    @Test
    public void testComputeWordTransitionCount() throws Exception {
        Map<String, Map<String, Integer>> wordTransitionCount = new HashMap<>();

        String text = "word1 word2 word3";

        Map<String, Integer> countMap1 = new HashMap<>();
        countMap1.put("word2", 1);
        wordTransitionCount.put("word1", countMap1);

        Map<String, Integer> countMap2 = new HashMap<>();
        countMap2.put("word3", 1);
        wordTransitionCount.put("word2", countMap2);

        assertEquals(wordTransitionCount, LyricsUtil.computeWordTransitionCount(text));
    }

    @Test
    public void testComputeNormalizedWordTransitionCount() throws Exception {
        Map<String, Map<String, Double>> wordTransitionCount = new HashMap<>();

        String text = "word1 word2 word3";

        Map<String, Double> countMap1 = new HashMap<>();
        countMap1.put("word2", 1.0);
        wordTransitionCount.put("word1", countMap1);

        Map<String, Double> countMap2 = new HashMap<>();
        countMap2.put("word3", 1.0);
        wordTransitionCount.put("word2", countMap2);

        assertEquals(wordTransitionCount, LyricsUtil.computeNormalizedWordTransitionCount(text));
    }

    @Test
    public void testGetWordList() throws Exception {
        List<String> list = Arrays.asList("this", "is", "the", "only", "list", "produced");
        String text = String.join(" ", list);

        assertEquals(list, LyricsUtil.getWordList(text, "this", list.size()));

        assertEquals(Arrays.asList("this"), LyricsUtil.getWordList(text, "this", 0));
    }
}
