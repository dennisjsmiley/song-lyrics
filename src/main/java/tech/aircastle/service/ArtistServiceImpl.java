package tech.aircastle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.aircastle.domain.Artist;
import tech.aircastle.domain.Song;
import tech.aircastle.repository.ArtistRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {

    private ArtistRepository artistRepository;

    private SongService songService;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository, SongService songService) {
        this.artistRepository = artistRepository;
        this.songService = songService;
    }

    @Override
    public List<Artist> findAll() {
        List<Artist> artists = new ArrayList<>();
        for (Artist artist : artistRepository.findAll()) {
            artists.add(artist);
        }
        return artists;
    }

    @Override
    public Artist save(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public Optional<Artist> findById(String id) {
        return artistRepository.findById(id);
    }

    @Override
    public boolean addArtistSong(String artistId, String songId) {
        Optional<Artist> artistResult = artistRepository.findById(artistId);
        if (artistResult.isPresent()) {
            Artist artist = artistResult.get();
            Optional<Song> songResult = songService.findById(songId);
            if (songResult.isPresent()) {
                Song song = songResult.get();
                song.setArtist(artist);
                songService.save(song);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
