package tech.aircastle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.aircastle.domain.Song;
import tech.aircastle.repository.SongRepository;
import tech.aircastle.util.LyricsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class SongServiceImpl implements SongService {

    private SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<Song> findAll() {
        List<Song> songs = new ArrayList<>();
        for (Song song : songRepository.findAll()) {
            songs.add(song);
        }
        return songs;
    }

    @Override
    public Song save(Song song) {
        return songRepository.save(song);
    }

    @Override
    public Optional<Song> findById(String id) {
        return songRepository.findById(id);
    }

    private <S> Optional<S> fetchSongAndTransform(String songId, Function<Song, S> function) {
        Optional<Song> songResult = findById(songId);
        if (songResult.isPresent()) {
            return Optional.of(function.apply(songResult.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<String>> tokenize(String songId) {
        return fetchSongAndTransform(songId, song -> LyricsUtil.tokenize(song.getLyrics()));
    }

    @Override
    public Optional<Map<String, Integer>> computeWordCount(String songId) {
        return fetchSongAndTransform(songId, song -> LyricsUtil.computeWordCount(song.getLyrics()));
    }

    @Override
    public Optional<Map<String, Double>> computeNormailzedWordCount(String songId) {
        return fetchSongAndTransform(songId, song -> LyricsUtil.computeNormalizedWordCount(song.getLyrics()));
    }

    @Override
    public Optional<Map<String, Map<String, Integer>>> computeWordTransitionCount(String songId) {
        return fetchSongAndTransform(songId, song -> LyricsUtil.computeWordTransitionCount(song.getLyrics()));
    }

    @Override
    public Optional<Map<String, Map<String, Double>>> computeNormalizedWordTransitionCount(String songId) {
        return fetchSongAndTransform(songId, song -> LyricsUtil.computeNormalizedWordTransitionCount(song.getLyrics()));
    }

    @Override
    public Optional<List<String>> computeWordMarkovModel(String songId, String startWord, Integer length) {
        return fetchSongAndTransform(songId, song -> {
            return (startWord != null) ?
                LyricsUtil.getWordList(song.getLyrics(), startWord, length) :
                LyricsUtil.getWordList(song.getLyrics(), length);
        });
    }
}
