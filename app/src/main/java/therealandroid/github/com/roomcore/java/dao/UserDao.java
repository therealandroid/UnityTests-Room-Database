package therealandroid.github.com.roomcore.java.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import therealandroid.github.com.roomcore.java.entities.User;

/**
 * Created by diogojayme on 22/09/17.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    public void insertAll(List<User> users);

}
