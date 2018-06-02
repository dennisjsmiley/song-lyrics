package tech.aircastle.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.aircastle.text.WordCounter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static WordCounter computeWordTransitionCount(String text) {
        WordCounter wordCounter = new WordCounter();

        List<String> tokens = tokenize(text);

        for (int i = 1; i < tokens.size(); i++) {
            String prev = tokens.get(i - 1);
            String curr = tokens.get(i);

            wordCounter.addCount(prev, curr);
        }

        return wordCounter;
    }
}
