package therealandroid.github.com.roomcore;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import therealandroid.github.com.roomcore.java.dao.AlbumDao;
import therealandroid.github.com.roomcore.java.database.MyDatabase;
import therealandroid.github.com.roomcore.java.entities.Album;
import therealandroid.github.com.roomcore.java.entities.Artist;
import therealandroid.github.com.roomcore.java.entities.ArtistAlbum;
import therealandroid.github.com.roomcore.java.entities.ArtistTrack;
import therealandroid.github.com.roomcore.java.entities.ROLE_TYPE;
import therealandroid.github.com.roomcore.java.entities.Track;
import therealandroid.github.com.roomcore.java.extractor.AlbumSetExtractor;
import therealandroid.github.com.roomcore.java.pojo.AlbumWithArtist;
import therealandroid.github.com.roomcore.java.pojo.TrackWithArtist;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {
    private AlbumDao albumDao;

    private MyDatabase database;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        database = Room
                .databaseBuilder(context, MyDatabase.class, "my-db")
                .fallbackToDestructiveMigration().build();
        albumDao = database.albumDao();
    }

    @Test
    public void writeAlbumTracksAndGet() throws Exception {
        AlbumDao albumDao = database.albumDao();
        albumDao.insertAlbum(DatabaseUtil.createAlbum(4, "Wazzup"));
        albumDao.insertAlbum(DatabaseUtil.createAlbum(3, "Hold Up"));

        albumDao.insertTracks(DatabaseUtil.createListOfTracks1to10(4));
        albumDao.insertTracks(DatabaseUtil.createListOfTracks50to60(3));

//        List<AlbumWithTracks> albumWithTracksList = albumDao.albumWithTracks(4);
//        List<AlbumWithTracks> albumWithTracksList1 = albumDao.albumWithTracks(3);
//
//        Album album = albumWithTracksList.get(0).getAlbum();
    }

    private void deleteAllData(AlbumDao albumDao) {

        long a = albumDao.deleteArtistAlbumRelations();
        long b = albumDao.deleteArtistTrackRelations();
        long c = albumDao.deleteAllTracks();
        long d = albumDao.deleteAllAlbums();
        long e = albumDao.deleteAllArtists();

        System.out.println(String.format("Total Album Artists deleted {%d}\n Total Artists Track deleted {%d} \n  Total tracks deleted {%d}\n Total albums deleted {%d} \n Total deleted artists{%d}", a,b,c,d,e ));
    }

    /**
     * Explanation
     *
     * Albums can have many artists
     * Tracks can have many artists
     *
     * Each artist can participate as MAIN, PRODUCER or FEATURE in tracks OR albums
     *
     *
     * **/
    @Test
    public void testInsertRelationsOfAnAlbum() throws Exception {
        AlbumDao albumDao = database.albumDao();

        //clear some data first
        deleteAllData(albumDao);

        long ALBUM_ID = 1;
        long TRACK_ID = 2;
        long ARTIST_ID = 3;
        long ARTIST_ID_1 = 5;
        long ARTIST_ID_2 = 4;

        //CREATE FIRST ALBUM
        Album album = new Album();
        album.setAlbumName("Dream cheasers");
        album.setId(ALBUM_ID);

        //CREATE AN ARTIST FOR THIS ALBUM
        Artist artist = new Artist();
        artist.setId(ARTIST_ID);
        artist.setArtistName("Meek Mill");

        //THIS ARTIST JUST PARTICIPATE IN A TRACK
        Artist artist1 = new Artist();
        artist1.setId(ARTIST_ID_1);
        artist1.setArtistName("Shaun J");

        //CREATE AN ARTIST THAT JUST PARTICIPATE IN THIS ALBUM
        Artist artist2 = new Artist();
        artist2.setId(ARTIST_ID_2);
        artist2.setArtistName("Migos");

        //CREATE A TRACK
        Track track = new Track();
        track.setAlbumId(ALBUM_ID);
        track.setId(TRACK_ID);
        track.setTrackName("Track");

        ArtistTrack FIRST_ARTIST_TRACK_RELATION = new ArtistTrack();
        FIRST_ARTIST_TRACK_RELATION.setType(ROLE_TYPE.MAIN.ordinal());
        FIRST_ARTIST_TRACK_RELATION.setArtistId(ARTIST_ID); // THIS ARTIST IS FEATURE IN TRACK
        FIRST_ARTIST_TRACK_RELATION.setTrackId(TRACK_ID);

        ArtistTrack SECOND_ARTIST_TRACK_RELATION = new ArtistTrack();
        SECOND_ARTIST_TRACK_RELATION.setType(ROLE_TYPE.PRODUCER.ordinal());
        SECOND_ARTIST_TRACK_RELATION.setArtistId(ARTIST_ID_1); // THIS ARTIST IS FEATURE IN TRACK
        SECOND_ARTIST_TRACK_RELATION.setTrackId(TRACK_ID);

        //ALBUM <-> ARTIST
        ArtistAlbum FIRST_ALBUM_ARTIST_RELATION = new ArtistAlbum();
        FIRST_ALBUM_ARTIST_RELATION.setType(ROLE_TYPE.MAIN.ordinal());
        FIRST_ALBUM_ARTIST_RELATION.setArtistId(ARTIST_ID); // THIS ARTIST IS MAIN IN ALBUM
        FIRST_ALBUM_ARTIST_RELATION.setAlbumId(ALBUM_ID);

        //SECOND RELATION ALBUM <-> ARTIST
        ArtistAlbum SECOND_ARTIST_ALBUM_RELATION = new ArtistAlbum();
        SECOND_ARTIST_ALBUM_RELATION.setType(ROLE_TYPE.FEATURE.ordinal());
        SECOND_ARTIST_ALBUM_RELATION.setArtistId(ARTIST_ID_2); // THIS ARTIST IS FEATURE  IN THE ALBUM
        SECOND_ARTIST_ALBUM_RELATION.setAlbumId(ALBUM_ID);

        albumDao.insertAlbum(album);
        albumDao.insertArtist(artist, artist1, artist2);
        albumDao.insertTracks(track);

        albumDao.insertArtistTrackRelation(FIRST_ARTIST_TRACK_RELATION);
        albumDao.insertArtistAlbumRelation(FIRST_ALBUM_ARTIST_RELATION);
        albumDao.insertArtistAlbumRelation(SECOND_ARTIST_ALBUM_RELATION);

        List<Artist> albumWithArtists = albumDao.selectAlbumArtistsByAlbumId(ALBUM_ID);

        AlbumSetExtractor albumSetExtractor = new AlbumSetExtractor(albumDao);

        //Here we group Album with it artists and it tracks, each track with it artists

        Album albumFinal = albumSetExtractor
                .forAlbumId(ALBUM_ID)
                .withArtists()
                .withTracksAndArtists()
                .build();


        assertEquals(albumFinal.getAlbumName(), album.getAlbumName());
    }

    @After
    public void closeDb() throws IOException {
        database.close();
    }

    public static class DatabaseUtil {

        public static Album createAlbum(long id, String name) {
            Album album = new Album();
            album.setId(id);
            return album;
        }

        public static Track createTrack(long id, long albumid) {
            Track track = new Track();
            track.setId(id);
            track.setAlbumId(albumid); //create the relation between track and album
            return track;
        }

        public static List<Track> createListOfTracks50to60(long albumId) {
            List<Track> tracks = new ArrayList<>();

            for (int i = 50; i < 60; i++) {
                tracks.add(createTrack(i, albumId));
            }

            return tracks;
        }

        public static List<Track> createListOfTracks1to10(long albumId) {
            List<Track> tracks = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                tracks.add(createTrack(i, albumId));
            }

            return tracks;
        }
    }

}