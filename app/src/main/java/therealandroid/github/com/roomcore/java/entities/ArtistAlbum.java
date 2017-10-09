package therealandroid.github.com.roomcore.java.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by diogojayme on 03/10/17.
 */

@Entity(tableName = "artist_album", foreignKeys = {
        @ForeignKey(entity = Artist.class, parentColumns = { "id"}, childColumns = "artist_id"),
        @ForeignKey(entity = Album.class, parentColumns = { "id"}, childColumns = "album_id")
})
public class ArtistAlbum {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "album_id")
    private long albumId;

    @ColumnInfo(name = "artist_id")
    private long artistId;

    public long getId() {
        return id;
    }

    @ColumnInfo(name = "role_type")
    private long type;

    public void setId(long id) {
        this.id = id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

}
