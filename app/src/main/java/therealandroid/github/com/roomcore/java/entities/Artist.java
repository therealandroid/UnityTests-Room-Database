package therealandroid.github.com.roomcore.java.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by diogojayme on 28/09/17.
 */
@Entity(tableName = "artist")
public class Artist {

    @ColumnInfo(name = "id")
    @PrimaryKey
    long id;

    @ColumnInfo(name = "artist_name")
    private String artistName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
