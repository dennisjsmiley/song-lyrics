package tech.aircastle.service;

import tech.aircastle.domain.Song;

import java.util.List;
import java.util.Optional;

public interface SongService {
    List<Song> findAll();
    Song save(Song song);
    Optional<Song> findById(String id);
}
