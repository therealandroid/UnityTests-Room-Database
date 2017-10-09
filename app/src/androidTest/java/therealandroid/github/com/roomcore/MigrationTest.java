package therealandroid.github.com.roomcore;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import therealandroid.github.com.roomcore.java.database.Migrations;
import therealandroid.github.com.roomcore.java.database.MyDatabase;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static therealandroid.github.com.roomcore.java.database.Migrations.MIGRATION_1_2;
import static therealandroid.github.com.roomcore.java.database.Migrations.MIGRATION_1_3;

@RunWith(AndroidJUnit4.class)
public class MigrationTest {

    private static final String TEST_DB = "migration-test";
    private SupportSQLiteDatabase supportSQLiteDatabase;

    @Rule
    public MigrationTestHelper migrationTestHelper =
            new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                    MyDatabase.class.getCanonicalName(),
                    new FrameworkSQLiteOpenHelperFactory());

    @Before
    public void setUp() throws IOException {
        supportSQLiteDatabase = migrationTestHelper.createDatabase(TEST_DB, 1);
    }


    /**
     * Instrumentation test, which will execute on an Android device.
     *
     * Instructions
     * 0 - Delete all JSON from schemes folder
     * 1 - Remove the attributes from your Entity that you want to migrate later
     *   |- The test runs based on attribute "AGE"
     * 2 - Downgrade database version (Ex: 2 -> 1)
     * 3 - Rebuild to generate scheme JSON v1
     * 4 - Put the attributes back
     * 5 - Increase database version (Ex: 1 -> 2)
     * 6 - Rebuild to generate scheme JSON v2
     * 7 - Run
     * 8 - Check the Logcat it should have something like
     ==== VERSION 1 ====
     id=> 1  name => Diogo
     ==== VERSION 2 ====
     id=> 1  name => Diogo  age => 2147483647
     id=> 2  name => Douglas  age => 24
     *
     * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
     *
     */
    @Test
    public void migrate1To2_adding_new_attribute() throws IOException {
        final String FIRST_REGISTER_NAME = "Diogo";
        final int LAST_REGISTER_AGE = 24;
        final String LAST_REGISTER_NAME = "Douglas";

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        supportSQLiteDatabase.execSQL("INSERT INTO user('name') VALUES('" + FIRST_REGISTER_NAME + "')");

        Cursor cursor = supportSQLiteDatabase.query("SELECT * FROM user");

        System.out.println("");
        System.out.println("==== VERSION 1 ====");

        while (cursor.moveToNext()) {
            String userString = String.format("id=> %d  name => %s", cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("name")));
            System.out.println(userString);
        }

        //Close database and cursor
        supportSQLiteDatabase.close();
        cursor.close();

        // MIGRATION_1_2 as the migration process.
        supportSQLiteDatabase = migrationTestHelper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2);

        //LogCat
        System.out.println("");
        System.out.println("==== VERSION 2 ====");

        //Insert a new register
        supportSQLiteDatabase.execSQL("INSERT INTO user('name', 'age') VALUES('" + LAST_REGISTER_NAME + "', " + LAST_REGISTER_AGE + ")");
        cursor = supportSQLiteDatabase.query("SELECT * FROM user");

        //Variables to compare the data after migration
        //if the  firstRegisterName matches FIRST_REGISTER_NAME and firstRegisterAge matches the DEFAULT_VALUE_FOR_COLUMN_MIGRATION for the migration data
        //the test return success

        String firstRegisterName = null;
        int firstRegisterAge = 0;

        String lastRegisterName = null;
        int lastRegisterAge = 0;

        while (cursor.moveToNext()) {
            //LogCat
            String userString = String.format("id=> %d  name => %s  age => %d", cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("name")), cursor.getInt(cursor.getColumnIndex("age")));
            System.out.println(userString);

            //Get the first data from attribute of the migration to verify it's default value
            if(cursor.getPosition() == 0){
                firstRegisterName = cursor.getString(cursor.getColumnIndex("name"));
                firstRegisterAge= cursor.getInt(cursor.getColumnIndex("age"));
            }else{
                //Get the  last register after the migration happens
                lastRegisterName = cursor.getString(cursor.getColumnIndex("name"));
                lastRegisterAge = cursor.getInt(cursor.getColumnIndex("age"));
            }
        }

        //Here we check if the migration data matches after saved
        assertNotNull(lastRegisterName);
        assertEquals(lastRegisterName, LAST_REGISTER_NAME);
        assertEquals(lastRegisterAge, LAST_REGISTER_AGE);
        assertEquals(firstRegisterName, FIRST_REGISTER_NAME);
        assertEquals(firstRegisterAge, Migrations.DEFAULT_VALUE_FOR_COLUMN_MIGRATION);
    }

    /***
     * 1 - Delete the attribute AGE from the entity
     * 2 - Upgrade db version
     * 3 - Build and clean
     * 4 - TODO how to test the data after migration?
     * @throws IOException
     */
    @Test
    public void migration1to3_removing_attribute() throws IOException {
        supportSQLiteDatabase = migrationTestHelper.runMigrationsAndValidate(TEST_DB, 3, true, MIGRATION_1_3);
        Cursor cursor = supportSQLiteDatabase.query("SELECT * FROM user");

        System.out.println("");
        System.out.println("==== VERSION 3 ====");

        while (cursor.moveToNext()) {
            String userString = String.format("id=> %d  name => %s", cursor.getInt(cursor.getColumnIndex("id")), cursor.getString(cursor.getColumnIndex("name")));
            System.out.println(userString);
        }
    }
}

