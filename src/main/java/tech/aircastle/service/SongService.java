package tech.aircastle.service;

import tech.aircastle.domain.Song;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SongService {
    List<Song> findAll();
    Song save(Song song);
    Optional<Song> findById(String id);

    Optional<List<String>> tokenize(String songId);

    Optional<Map<String, Integer>> computeWordCount(String songId);
    Optional<Map<String, Double>> computeNormailzedWordCount(String songId);

    Optional<Map<String, Map<String, Integer>>> computeWordTransitionCount(String songId);
    Optional<Map<String, Map<String, Double>>> computeNormalizedWordTransitionCount(String songId);

    Optional<List<String>> computeWordMarkovModel(String songId, String startWord, Integer length);
}
