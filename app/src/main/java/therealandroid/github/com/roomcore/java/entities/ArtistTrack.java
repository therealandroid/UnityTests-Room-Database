package therealandroid.github.com.roomcore.java.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by diogojayme on 02/10/17.
 */

@Entity(tableName = "artist_track", foreignKeys = {
        @ForeignKey(entity = Artist.class, parentColumns = "id", childColumns = "artist_id"),
        @ForeignKey(entity = Track.class, parentColumns = "id", childColumns = "track_id")
})
public class ArtistTrack {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "artist_id")
    private long artistId;

    @ColumnInfo(name = "track_id")
    private long trackId;

    @ColumnInfo(name = "role_type")
    private long type;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(long trackId) {
        this.trackId = trackId;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

}
