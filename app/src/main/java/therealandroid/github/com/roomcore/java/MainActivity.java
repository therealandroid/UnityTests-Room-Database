package therealandroid.github.com.roomcore.java;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import therealandroid.github.com.roomcore.java.dao.AlbumDao;
import therealandroid.github.com.roomcore.java.database.MyDatabase;
import therealandroid.github.com.roomcore.java.entities.Album;

/**
 * Created by diogojayme on 28/09/17.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyDatabase database= Room
                .databaseBuilder(this, MyDatabase.class, "Sample.db")
                .allowMainThreadQueries()
                .build();

        AlbumDao albumDao = database.albumDao();

    }
}
