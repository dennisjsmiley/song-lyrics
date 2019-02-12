package tech.aircastle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.aircastle.domain.Song;
import tech.aircastle.repository.SongRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private SongRepository songRepository;

    @Autowired
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
}
