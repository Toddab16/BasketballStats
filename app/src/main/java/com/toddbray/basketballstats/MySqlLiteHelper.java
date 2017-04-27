package com.toddbray.basketballstats;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Brad on 4/14/2017.
 */

public class MySqlLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "bbstattracker.sqlite";
    private static final int DB_VERSION = 1;

    public static final String GAME_TABLE = "Game";
    public static final String PLAYER_TABLE = "Player";
    public static final String STAT_TABLE = "Stats";
    public static final String SEASON_TABLE = "Season";
    public static final String MYSQL_DATABASE = "t_bray_bball_stats";
    public static final String MYSQL_HOST = "jdbc:mysql://fox.wuffhost.ovh:3306/";
    public static final String MYSQL_USERNAME = "t_bray_19751087";
    public static final String MYSQL_PASSWORD = "bballadmin16";
    public static final int MYSQL_PORT = 3306;

    // UPDATE SQLITE_SEQUENCE SET seq = <n> WHERE name = '<table>'

    public enum GameColumns {
        game_id, season_id, game_date, opp_name, location, venue, girls_jv, boys_jv, girls_v, boys_v;

        public static String[] names() {
            GameColumns[] v = values();
            String[] names = new String[v.length];
            for (int i = 0; i < v.length; i++) {
                names[i] = v[i].toString();
            }
            return names;
        }
    }

    public enum PlayerColumns {
        player_id, first_name, last_name, year, number;

        public static String[] names() {
            PlayerColumns[] v = values();
            String[] names = new String[v.length];
            for (int i = 0; i < v.length; i++) {
                names[i] = v[i].toString();
            }
            return names;
        }
    }

    public enum StatColumns {
        game_id, player_id, o_rebound, d_rebound, assist, steal, turnover, two_pointer, three_pointer,
        two_pointer_made, three_pointer_made, free_throw, free_throw_made, charge;

        public static String[] names() {
            StatColumns[] v = values();
            String[] names = new String[v.length];
            for (int i = 0; i < v.length; i++) {
                names[i] = v[i].toString();
            }
            return names;
        }
    }

    public enum SumStatColumns {
        game_id, player_id, o_rebound, d_rebound, assist, steal, turnover, two_pointer, three_pointer,
        two_pointer_made, three_pointer_made, free_throw, free_throw_made, charge;

        public static String[] names() {
            SumStatColumns[] v = values();
            String[] names = new String[v.length];
            for (int i = 0; i < v.length; i++) {
                if(i < 2) {
                    names[i] = v[i].toString();
                }else {
                    names[i] = "sum(" + v[i].toString() + ")";
                }

            }
            return names;
        }
    }

    public enum SeasonColumns {
        season_id, season_name;

        public static String[] names() {
            SeasonColumns[] v = values();
            String[] names = new String[v.length];
            for (int i = 0; i < v.length; i++) {
                names[i] = v[i].toString();
            }
            return names;
        }
    }

    public MySqlLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("TEST..............", "Database Table Created!");
        String sql = "CREATE TABLE " + GAME_TABLE + " (" +
                GameColumns.game_id + " INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL  UNIQUE , " +
                GameColumns.season_id + " INTEGER NOT NULL , " +
                GameColumns.game_date + " TEXT NOT NULL , " +
                GameColumns.location + " TEXT NOT NULL , " +
                GameColumns.venue + " TEXT NOT NULL , " +
                GameColumns.girls_jv + " TEXT , " +
                GameColumns.boys_jv + " TEXT , " +
                GameColumns.girls_v + " TEXT , " +
                GameColumns.boys_v + " TEXT , " +
                GameColumns.opp_name + " TEXT NOT NULL ) ";

        db.execSQL(sql);

        sql = "CREATE TABLE " + PLAYER_TABLE + " (" +
                PlayerColumns.player_id + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
                PlayerColumns.first_name + " TEXT NOT NULL , " +
                PlayerColumns.last_name + " TEXT NOT NULL , " +
                PlayerColumns.year + " TEXT NOT NULL , " +
                PlayerColumns.number + " TEXT NOT NULL )";
        db.execSQL(sql);

        sql = "CREATE TABLE " + STAT_TABLE + " (" +
                StatColumns.game_id + " INTEGER NOT NULL , " +
                StatColumns.player_id + " INTEGER NOT NULL , " +
                StatColumns.o_rebound + " INTEGER NOT NULL DEFAULT 0 , " +
                StatColumns.d_rebound + " INTEGER NOT NULL DEFAULT 0 , " +
                StatColumns.assist + " INTEGER NOT NULL DEFAULT 0 , " +
                StatColumns.steal + " INTEGER NOT NULL DEFAULT 0 , " +
                StatColumns.turnover + " INTEGER NOT NULL DEFAULT 0 , " +
                StatColumns.two_pointer + " INTEGER NOT NULL DEFAULT 0 , " +
                StatColumns.two_pointer_made + " INTEGER NOT NULL DEFAULT 0 , " +
                StatColumns.three_pointer + " INTEGER NOT NULL DEFAULT 0 , " +
                StatColumns.three_pointer_made + " INTEGER NOT NULL DEFAULT 0 , " +
                StatColumns.free_throw + " INTEGER NOT NULL DEFAULT 0 , " +
                StatColumns.free_throw_made + " INTEGER NOT NULL DEFAULT 0 , " +
                StatColumns.charge + " INTEGER NOT NULL DEFAULT 0 , " +
                "PRIMARY KEY ( " + StatColumns.game_id + " , " + StatColumns.player_id + " ))";
        db.execSQL(sql);

        sql = "CREATE TABLE " + SEASON_TABLE + " (" +
                SeasonColumns.season_id + " INTEGER PRIMARY KEY NOT NULL UNIQUE , " +
                SeasonColumns.season_name + " INTEGER NOT NULL ) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            /*
            String sql = "alter table " + GAME_TABLE + " add column extra integer";
            db.execSQL(sql);

            sql = "update " + GAME_TABLE + " set extra = 42";
            db.execSQL(sql);
            */
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1 && oldVersion != newVersion) {
            /*
            // The reason we need all this code is because
            // SQLite does not support ALTER TABLE ... DROP COLUMN
            try {
                db.beginTransaction();

                // copy table that needs column dropped
                String sql = "alter table " + GAME_TABLE + " rename to tmp";
                db.execSQL(sql);

                // recreate the table in the old schema
                sql = "CREATE TABLE " + GAME_TABLE + " (" +
                        GameColumns.game_id + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
                        GameColumns.opp_name + " TEXT NOT NULL , " +
                        GameColumns.game_date + " TEXT NOT NULL )";
                db.execSQL(sql);

                // copy the data we want from the old table
                sql = "insert into " + GAME_TABLE +
                        " select " +
                        GameColumns.game_id + ", " +
                        GameColumns.opp_name + ", " +
                        GameColumns.game_date +
                        " from tmp";
                db.execSQL(sql);

                // get rid of the temprary table
                sql = "drop table tmp";
                db.execSQL(sql);

                db.setTransactionSuccessful();
            }  catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
            */
        }

    }
}
