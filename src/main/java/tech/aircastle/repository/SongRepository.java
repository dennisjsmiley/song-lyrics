package tech.aircastle.repository;

import org.springframework.data.repository.CrudRepository;
import tech.aircastle.domain.Song;

/**
 * Created by djsmiley on 5/27/18.
 */
public interface SongRepository extends CrudRepository<Song, String> {
}
