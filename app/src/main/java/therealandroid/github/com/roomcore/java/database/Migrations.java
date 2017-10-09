package therealandroid.github.com.roomcore.java.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;

/**
 * Created by diogojayme on 26/09/17.
 */

public class Migrations {

    public static final int DEFAULT_VALUE_FOR_COLUMN_MIGRATION = Integer.MAX_VALUE;

    public static Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user ADD COLUMN age INTEGER NOT NULL DEFAULT " + DEFAULT_VALUE_FOR_COLUMN_MIGRATION);
        }
    };

    public static Migration MIGRATION_1_3 = new Migration(1, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE user_new (id INTEGER NOT NULL, name TEXT NOT NULL, PRIMARY KEY(id))");
            database.execSQL("INSERT INTO user_new (id, name) SELECT id, name FROM user");
            database.execSQL("DROP TABLE user");
            database.execSQL("ALTER TABLE user_new RENAME TO user");
        }
    };
}
