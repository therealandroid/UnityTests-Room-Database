package therealandroid.github.com.roomcore.java.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

import therealandroid.github.com.roomcore.java.entities.Album;
import therealandroid.github.com.roomcore.java.entities.Artist;
import therealandroid.github.com.roomcore.java.entities.ArtistTrack;
import therealandroid.github.com.roomcore.java.entities.Track;

/**
 * Created by diogojayme on 03/10/17.
 */

public class TrackWithArtist {

    @Embedded
    Track track;

    @Relation(parentColumn = "id", entityColumn = "artist_id", entity = ArtistTrack.class, projection = { "id", "artist_name" })
    List<Artist> artists;

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

}
