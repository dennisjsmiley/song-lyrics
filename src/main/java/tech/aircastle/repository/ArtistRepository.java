package tech.aircastle.repository;

import org.springframework.data.repository.CrudRepository;
import tech.aircastle.domain.Artist;

/**
 * Created by djsmiley on 5/27/18.
 */
public interface ArtistRepository extends CrudRepository<Artist, String> {
}
