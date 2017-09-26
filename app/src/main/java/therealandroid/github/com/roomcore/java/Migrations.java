package therealandroid.github.com.roomcore.java;

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

    public static Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user ADD COLUMN premium TEXT NULL");
        }
    };
}
