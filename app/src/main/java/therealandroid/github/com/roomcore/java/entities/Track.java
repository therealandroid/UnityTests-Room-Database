package therealandroid.github.com.roomcore.java.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.nfc.tech.NfcA;

import java.util.List;

/**
 * Created by diogojayme on 28/09/17.
 * <p>
 * SOLUTION
 * TODO https://stackoverflow.com/questions/44361824/how-can-i-represent-a-many-to-many-relation-with-android-room
 */

@Entity(tableName = "track", foreignKeys = {
        @ForeignKey(entity = Album.class, parentColumns = "id", childColumns = "album_id")
})
public class Track {

    @ColumnInfo(name = "id")
    @PrimaryKey
    long id;

    @ColumnInfo(name = "album_id")
    long albumId;

    @ColumnInfo(name = "track_name")
    String trackName;

    @Ignore
    List<Artist> artists;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
