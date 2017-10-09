package therealandroid.github.com.roomcore.java.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

import therealandroid.github.com.roomcore.java.entities.Album;
import therealandroid.github.com.roomcore.java.entities.Artist;
import therealandroid.github.com.roomcore.java.entities.ArtistAlbum;

/**
 * Created by diogojayme on 04/10/17.
 *
 */
public class AlbumWithArtist {
    @Embedded
    private Album album;

    @Relation(parentColumn = "id", entityColumn = "artist_id", entity = ArtistAlbum.class)
    private List<ArtistAlbum> artists;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<ArtistAlbum> getArtists() {
        return artists;
    }

    public void setArtists(List<ArtistAlbum> artists) {
        this.artists = artists;
    }
}
