package therealandroid.github.com.roomcore.java.extractor;

import java.util.ArrayList;
import java.util.List;

import therealandroid.github.com.roomcore.java.dao.AlbumDao;
import therealandroid.github.com.roomcore.java.entities.Album;
import therealandroid.github.com.roomcore.java.entities.Artist;
import therealandroid.github.com.roomcore.java.entities.Track;

/**
 * Created by diogojayme on 05/10/17.
 */

public class AlbumSetExtractor {

    private AlbumDao albumDao;

    private Album album;

    public AlbumSetExtractor(AlbumDao albumDao){
        this.albumDao = albumDao;
    }

    public AlbumSetExtractor forAlbumId(long albumId){
        album = albumDao.getAlbumById(albumId);
        return this;
    }

    public AlbumSetExtractor withArtists(){
        List<Artist> artists = albumDao.selectAlbumArtistsByAlbumId(album.getId());
        album.setArtists(artists);
        return this;
    }

    public AlbumSetExtractor withTracksAndArtists(){
        List<Track> tracks = albumDao.selectTracksByAlbumId(album.getId());

        for (int i = 0; i < tracks.size(); i++) {
            List<Artist> artists = albumDao.selectArtistsByTrackId(tracks.get(i).getId());
            tracks.get(i).setArtists(artists);
        }

        album.setTracks(tracks);
        return this;
    }

    public Album build(){
        return album;
    }

}
