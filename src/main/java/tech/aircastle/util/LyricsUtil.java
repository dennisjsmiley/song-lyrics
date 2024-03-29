package tech.aircastle.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.aircastle.text.WordTransitionCounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by djsmiley on 5/27/18.
 */
public class LyricsUtil {

    private static final Logger logger = LoggerFactory.getLogger(LyricsUtil.class);

    public static List<String> tokenize(String text) {
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(text.toLowerCase());

        List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        return tokens;
    }

    public static Map<String, Integer> computeWordCount(String text) {
        List<String> tokens = tokenize(text);

        Map<String, Integer> wordCount = new HashMap<>();
        for (String token : tokens) {
            Integer count = wordCount.getOrDefault(token, 0);
            wordCount.put(token, count + 1);
        }

        return wordCount;
    }

    public static Map<String, Double> computeNormalizedWordCount(String text) {
        Map<String, Integer> wordCount = computeWordCount(text);

        Integer total = 0;
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            total += entry.getValue();
        }

        Map<String, Double> normalizedWordCount = new HashMap<>();
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            Integer count = entry.getValue();
            normalizedWordCount.put(entry.getKey(), new Double(count) / new Double(total));
        }

        return normalizedWordCount; 
    }

    public static Map<String, Map<String, Integer>> computeWordTransitionCount(String text) {
        List<String> tokens = tokenize(text);

        WordTransitionCounter wordTransitionCounter = getTrainedWordTransitionCounter(tokens);

        return wordTransitionCounter.getWordTransitionCount();
    }

    public static Map<String, Map<String, Double>> computeNormalizedWordTransitionCount(String text) {
        List<String> tokens = tokenize(text);

        WordTransitionCounter counter = getTrainedWordTransitionCounter(tokens);

        return counter.getNormalizedWordTransitionCount();
    }

    public static List<String> getWordList(String text, int length) {
        List<String> tokens = tokenize(text);

        WordTransitionCounter counter = getTrainedWordTransitionCounter(tokens);

        int startWordIndex = ThreadLocalRandom.current().nextInt(0, tokens.size());
        String startWord = tokens.get(startWordIndex);

        return getWordList(counter, startWord, length);
    }

    public static List<String> getWordList(String text, String startWord, int length) {
        List<String> tokens = tokenize(text);

        WordTransitionCounter counter = getTrainedWordTransitionCounter(tokens);

        return getWordList(counter, startWord, length);
    }

    private static List<String> getWordList(WordTransitionCounter counter, String startWord, int length) {
        if (length < 1) {
            return new ArrayList<>();
        }

        List<String> wordList = new ArrayList<>();
        wordList.add(startWord);

        for (int i = 0; i < length - 1; i++) {
            String nextWord = counter.getNextWord(startWord);
            if (nextWord != null) {
                wordList.add(nextWord);
                startWord = nextWord;
            } else {
                break;
            }
        }

        return wordList;
    }

    private static WordTransitionCounter getTrainedWordTransitionCounter(List<String> tokens) {
        WordTransitionCounter counter = new WordTransitionCounter();

        for (int i = 1; i < tokens.size(); i++) {
            String prev = tokens.get(i - 1);
            String curr = tokens.get(i);
            counter.observe(prev, curr);
        }

        return counter;
    }
}
