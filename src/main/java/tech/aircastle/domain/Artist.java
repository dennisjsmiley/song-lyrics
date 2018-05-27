package tech.aircastle.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by djsmiley on 5/27/18.
 */
@Entity
public class Artist extends AbstractBaseEntity {

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "artist")
    private List<Song> songs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
