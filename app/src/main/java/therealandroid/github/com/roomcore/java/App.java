package therealandroid.github.com.roomcore.java;

import android.app.Application;
import android.arch.persistence.room.Room;

import static therealandroid.github.com.roomcore.java.Migrations.MIGRATION_1_2;
import static therealandroid.github.com.roomcore.java.Migrations.MIGRATION_2_3;

/**
 * Created by diogojayme on 22/09/17.
 */

public class App extends Application {

    public static App instance;
    private MyDatabase roomDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        roomDatabase = Room
                .databaseBuilder(this, MyDatabase.class, "Sample.db")
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .allowMainThreadQueries()
                .build();
    }

    public static MyDatabase database() {
        return instance.roomDatabase;
    }
}
