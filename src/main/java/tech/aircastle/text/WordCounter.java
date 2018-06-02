package tech.aircastle.text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by djsmiley on 6/2/18.
 */
public class WordCounter {

    private final Map<String, Map<String, Integer>> wordCount = new HashMap<>();

    public void addCount(String first, String second) {
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
}
