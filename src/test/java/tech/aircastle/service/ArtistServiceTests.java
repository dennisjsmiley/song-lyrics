package tech.aircastle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tech.aircastle.domain.Artist;
import tech.aircastle.repository.ArtistRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArtistServiceTests {
    @Autowired
    private ArtistService artistService;

    @MockBean
    private ArtistRepository artistRepository;

    @MockBean
    private SongService songRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testFindAllArtists() {
        List<Artist> myArtists = Arrays.asList(getSampleArtist());

        Mockito.when(artistRepository.findAll()).thenReturn(myArtists);

        List<Artist> foundArtists = artistService.findAll();
        assertEquals(1, foundArtists.size());

        Artist foundArtist = foundArtists.get(0);
        assertEquals("My Artist", foundArtist.getName());

        foundArtists.forEach(item -> logger.info("artist: {}", item.getName()));

        logger.info("random: {}", Math.random());
    }

    private Artist getSampleArtist() {
        Artist artist = new Artist();
        artist.setName("My Artist");
        return artist;
    }
}
