package therealandroid.github.com.roomcore.java.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/**
 * Created by diogojayme on 28/09/17.
 */
@Entity(tableName = "album")
public class Album {

    @ColumnInfo(name = "id")
    @PrimaryKey
    long id;

    @ColumnInfo(name = "album_name")
    String albumName;

    @Ignore
    List<Artist> artists;

    @Ignore
    List<Track> tracks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
