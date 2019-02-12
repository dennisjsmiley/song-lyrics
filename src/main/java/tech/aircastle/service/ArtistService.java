package tech.aircastle.service;

import tech.aircastle.domain.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistService {
    List<Artist> findAll();
    Artist save(Artist artist);
    Optional<Artist> findById(String id);
    boolean addArtistSong(String artistId, String songId);
}
