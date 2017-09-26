package therealandroid.github.com.roomcore.java;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import therealandroid.github.com.roomcore.java.dao.UserDao;
import therealandroid.github.com.roomcore.java.entities.User;

/**
 * Created by diogojayme on 21/09/17.
 */

@Database(entities = {User.class}, version = 2)
public abstract class MyDatabase extends RoomDatabase {
    abstract UserDao userDao();
}
