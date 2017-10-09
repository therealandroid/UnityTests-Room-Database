package therealandroid.github.com.roomcore.java.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import therealandroid.github.com.roomcore.java.dao.AlbumDao;
import therealandroid.github.com.roomcore.java.entities.Album;
import therealandroid.github.com.roomcore.java.entities.Artist;
import therealandroid.github.com.roomcore.java.entities.ArtistAlbum;
import therealandroid.github.com.roomcore.java.entities.ArtistTrack;
import therealandroid.github.com.roomcore.java.entities.Track;

/**
 * Created by diogojayme on 21/09/17.
 */

@Database(entities = {
        Album.class,
        Artist.class,
        Track.class,
        ArtistTrack.class,
        ArtistAlbum.class
}, version = 4)
public abstract class MyDatabase extends RoomDatabase {
    public abstract AlbumDao albumDao();
}
