package therealandroid.github.com.roomcore.java.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import therealandroid.github.com.roomcore.java.entities.Album;
import therealandroid.github.com.roomcore.java.entities.Artist;
import therealandroid.github.com.roomcore.java.entities.ArtistAlbum;
import therealandroid.github.com.roomcore.java.entities.ArtistTrack;
import therealandroid.github.com.roomcore.java.pojo.AlbumWithArtist;
import therealandroid.github.com.roomcore.java.entities.Track;
import therealandroid.github.com.roomcore.java.pojo.TrackWithArtist;

/**
 * Created by diogojayme on 28/09/17.
 */

@Dao
public interface AlbumDao {

    @Query("DELETE FROM artist_album")
    public int deleteArtistAlbumRelations();

    @Query("DELETE FROM artist_track")
    public int deleteArtistTrackRelations();

    @Query("DELETE FROM track")
    public int deleteAllTracks();

    @Query("DELETE FROM album")
    public int deleteAllAlbums();

    @Query("DELETE FROM artist")
    public int deleteAllArtists();

    @Insert
    public void insertAlbum(Album album);

    @Insert
    public void insertTracks(List<Track> tracks);

    @Insert
    public void insertArtist(Artist... artist);

    @Insert
    public void insertTracks(Track track);

    @Query("SELECT * FROM album WHERE id = :albumId")
    Album getAlbumById(long albumId);

    @Query("SELECT * FROM ARTIST")
    List<Artist> getAllArtists();

    @Insert
    void insertArtistTrackRelation(ArtistTrack artistTrack);

    @Insert
    void insertArtistAlbumRelation(ArtistAlbum artistAlbum);

    /** Step 1 - Buscar toos os albums com seus  artistas **/
    @Query("SELECT al.id, al.album_name, at.id, at.artist_name, aa.role_type FROM artist_album aa INNER JOIN album al INNER JOIN artist at ON aa.artist_id = at.id WHERE al.id = :albumId")
    List<AlbumWithArtist> selectAlbumWithArtistsByAlbumId(long albumId);

    @Query("SELECT *, aa.role_type FROM artist_album aa INNER JOIN album al INNER JOIN artist at ON aa.artist_id = at.id WHERE al.id = :albumId")
    List<Artist> selectAlbumArtistsByAlbumId(long albumId);

    @Query("SELECT * FROM track t INNER JOIN artist_track at ON at.artist_id = :artistId")
    List<Track> selectTracksByArtistId(long artistId);

    @Query("SELECT t.id, t.track_name FROM track t INNER JOIN album al where al.id = :albumId")
    List<Track> selectTracksByAlbumId(long albumId);

    @Query("SELECT a.id, a.artist_name, at.role_type from artist a INNER JOIN artist_track at ON at.artist_id = a.id where track_id = :trackId")
    List<Artist> selectArtistsByTrackId(long trackId);

    @Query("SELECT * FROM artist_track")
    List<ArtistTrack> selectArtistTrack();

}

