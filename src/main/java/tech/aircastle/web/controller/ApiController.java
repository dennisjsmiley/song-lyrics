package tech.aircastle.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.aircastle.domain.Artist;
import tech.aircastle.domain.Song;
import tech.aircastle.repository.ArtistRepository;
import tech.aircastle.repository.SongRepository;
import tech.aircastle.text.WordTransitionCounter;
import tech.aircastle.util.LyricsUtil;

import java.util.*;

/**
 * Created by djsmiley on 5/27/18.
 */
@RestController
public class ApiController {

    private ArtistRepository artistRepository;

    private SongRepository songRepository;

    @Autowired
    public ApiController(ArtistRepository artistRepository, SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    @RequestMapping(value = "/artists", method = RequestMethod.GET)
    public List<Artist> findAllArtists() {
        List<Artist> artists = new ArrayList<>();

        for (Artist artist : artistRepository.findAll()) {
            artists.add(artist);
        }

        return artists;
    }

    @RequestMapping(value = "/artists", method = RequestMethod.POST)
    public ResponseEntity<String> addArtist(@RequestBody Artist artist) {
        Artist savedArtist = artistRepository.save(artist);
        return new ResponseEntity<>(savedArtist.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/artists/{artistId}", method = RequestMethod.GET)
    public ResponseEntity<Artist> findArtistById(@PathVariable String artistId) {
        Optional<Artist> result = artistRepository.findById(artistId);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/songs", method = RequestMethod.GET)
    public List<Song> findAllSongs() {
        List<Song> songs = new ArrayList<>();
        for (Song song : songRepository.findAll()) {
            songs.add(song);
        }

        return songs;
    }

    @RequestMapping(value = "/songs", method = RequestMethod.POST)
    public ResponseEntity<String> addSong(@RequestBody Song song) {
        Song savedSong = songRepository.save(song);
        return new ResponseEntity<>(savedSong.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/songs/{songId}", method = RequestMethod.GET)
    public ResponseEntity<Song> findSongById(@PathVariable String songId) {
        Optional<Song> result = songRepository.findById(songId);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/songs/{songId}/artist", method = RequestMethod.GET)
    public ResponseEntity<Artist> findArtistBySong(@PathVariable String songId) {
        Optional<Song> result = songRepository.findById(songId);
        if (result.isPresent()) {
            Song song = result.get();
            return new ResponseEntity<>(song.getArtist(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/songs/{songId}/tokens", method = RequestMethod.GET)
    public ResponseEntity<List<String>> tokenizedSong(@PathVariable String songId) {
        Optional<Song> result = songRepository.findById(songId);
        if (result.isPresent()) {
            Song song = result.get();
            List<String> tokens = LyricsUtil.tokenize(song.getLyrics());
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/songs/{songId}/wordCount", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Integer>> computeSongWordCount(@PathVariable String songId) {
        Optional<Song> result = songRepository.findById(songId);
        if (result.isPresent()) {
            Song song = result.get();
            Map<String, Integer> wordCount = LyricsUtil.computeWordCount(song.getLyrics());
            return new ResponseEntity<>(wordCount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/songs/{songId}/normalizedWordCount", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Double>> computeSongNormalizedWordCount(@PathVariable String songId) {
        Optional<Song> result = songRepository.findById(songId);
        if (result.isPresent()) {
            Song song = result.get();
            Map<String, Double> wordCount = LyricsUtil.computeNormalizedWordCount(song.getLyrics());
            return new ResponseEntity<>(wordCount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/songs/{songId}/wordTransitionCount", method = RequestMethod.GET)
    public ResponseEntity<String> computeWordTransitionCount(@PathVariable String songId) {
        Optional<Song> result = songRepository.findById(songId);
        if (result.isPresent()) {
            Song song = result.get();
            WordTransitionCounter wordTransitionCounter = LyricsUtil.computeWordTransitionCount(song.getLyrics());

            return new ResponseEntity<>(wordTransitionCounter.toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/artists/{artistId}/songs", method = RequestMethod.GET)
    public ResponseEntity<List<Song>> findSongsByArtist(@PathVariable String artistId) {
        Optional<Artist> result = artistRepository.findById(artistId);
        if (result.isPresent()) {
            Artist artist = result.get();
            return new ResponseEntity<>(artist.getSongs(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/artists/{artistId}/songs/{songId}", method = RequestMethod.POST)
    public ResponseEntity<Void> associateSongToArtist(@PathVariable String artistId,
                                                      @PathVariable String songId) {
        Optional<Artist> artistResult = artistRepository.findById(artistId);
        if (artistResult.isPresent()) {
            Optional<Song> songResult = songRepository.findById(songId);
            if (songResult.isPresent()) {
                Artist artist = artistResult.get();
                Song song = songResult.get();
                song.setArtist(artist);
                songRepository.save(song);

                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
