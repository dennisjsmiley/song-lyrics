package tech.aircastle.text;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by djsmiley on 6/2/18.
 */
public class WordTransitionCounter {

    private final Map<String, Map<String, Integer>> wordCount = new HashMap<>();

    public void observe(String first, String second) {
        Map<String, Integer> countMap = wordCount.get(first);
        if (countMap != null) {
            Integer count = countMap.get(second);
            if (count != null) {
                count += 1;
            } else {
                count = 1;
            }
            countMap.put(second, count);
        } else {
            countMap = new HashMap<>();
            countMap.put(second, 1);

            wordCount.put(first, countMap);
        }
    }

    public Integer getCount(String first, String second) {
        Map<String, Integer> countMap = wordCount.getOrDefault(first, new HashMap<>());
        return countMap.getOrDefault(second, 0);
    }

    public Double getNormalizedCount(String first, String second) {
        Map<String, Integer> countMap = wordCount.get(first);
        if (countMap != null) {
            Integer count = countMap.get(second);
            if (count != null) {
                Integer total = 0;
                for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
                    total += entry.getValue();
                }
                return new Double(count) / new Double(total);
            }
        }
        return 0.0;
    }

    public Map<String, Map<String, Integer>> getWordTransitionCount() {
        return new HashMap<>(wordCount);
    }

    public Map<String, Map<String, Double>> getNormalizedWordTransitionCount() {
        Map<String, Map<String, Double>> normalizedWordTransitionCount = new HashMap<>();

        for (String first : wordCount.keySet()) {
            Map<String, Integer> countMap = wordCount.get(first);

            Integer total = 0;
            for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
                total += entry.getValue();
            }

            Map<String, Double> normalizedCountMap = new HashMap<>();
            for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
                normalizedCountMap.put(entry.getKey(), new Double(entry.getValue()) / new Double(total));
            }

            normalizedWordTransitionCount.put(first, normalizedCountMap);
        }

        return normalizedWordTransitionCount;
    }

    List<String> getWordHistogram(String word) {
        Map<String, Integer> countMap = wordCount.get(word);
        if (countMap != null) {
            List<String> histogram = new ArrayList<>();
            for (String key : countMap.keySet()) {
                Integer count = countMap.get(key);
                for (int i = 0; i < count; i++) {
                    histogram.add(key);
                }
            }
            return histogram;
        }
        return null;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(wordCount);
        } catch (Exception e) {
            return null;
        }
    }
}
