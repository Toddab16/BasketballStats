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

    public static final int NEW_ROW = 99;
    public static final String GAME_TABLE = "Game";
    public static final String PLAYER_TABLE = "Player";
    public static final String STAT_TABLE = "Stat";
    public static final String SEASON_TABLE = "Season";

    public enum GameColumns {

        android_id, season_id, game_id, game_date, opp_name, location, venue, girls_jv, boys_jv, girls_v, boys_v;


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
        android_id, player_id, first_name, last_name, year, number;

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
        android_id, stat_id, game_id, player_id, o_rebound, d_rebound, assist, steal, turnover, two_pointer, three_pointer,
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
        android_id, stat_id, game_id, player_id, o_rebound, d_rebound, assist, steal, turnover, two_pointer, three_pointer,
        two_pointer_made, three_pointer_made, free_throw, free_throw_made, charge;

        public static String[] names() {
            SumStatColumns[] v = values();
            String[] names = new String[v.length];
            for (int i = 0; i < v.length; i++) {
                if(i < 3) {
                    names[i] = v[i].toString();
                }else {
                    names[i] = "sum(" + v[i].toString() + ")";
                }

            }
            return names;
        }
    }

    public enum SeasonColumns {
        android_id, season_id, season_name;

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
                GameColumns.android_id + " TEXT NOT NULL , " +
                GameColumns.game_id + " INTEGER NOT NULL DEFAULT 0 , " +
                GameColumns.season_id + " INTEGER NOT NULL , " +
                GameColumns.game_date + " TEXT NOT NULL , " +
                GameColumns.location + " TEXT NOT NULL , " +
                GameColumns.venue + " TEXT NOT NULL , " +
                GameColumns.girls_jv + " TEXT , " +
                GameColumns.boys_jv + " TEXT , " +
                GameColumns.girls_v + " TEXT , " +
                GameColumns.boys_v + " TEXT , " +
                GameColumns.opp_name + " TEXT NOT NULL , " +
                "UNIQUE ( " + GameColumns.android_id + " , " + GameColumns.game_id + " ) ON CONFLICT IGNORE )";

        db.execSQL(sql);

        sql = "CREATE TABLE " + PLAYER_TABLE + " (" +
                PlayerColumns.android_id + " TEXT NOT NULL , " +
                PlayerColumns.player_id + " INTEGER NOT NULL DEFAULT 0 , " +
                PlayerColumns.first_name + " TEXT NOT NULL , " +
                PlayerColumns.last_name + " TEXT NOT NULL , " +
                PlayerColumns.year + " TEXT NOT NULL , " +
                PlayerColumns.number + " TEXT NOT NULL ," +
                "UNIQUE ( " + PlayerColumns.android_id + " , " + PlayerColumns.player_id + " ) ON CONFLICT IGNORE )";
        db.execSQL(sql);

        sql = "CREATE TABLE " + STAT_TABLE + " (" +
                StatColumns.android_id + " TEXT NOT NULL , " +
                StatColumns.stat_id + " INTEGER NOT NULL DEFAULT 0 , " +
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
                "UNIQUE ( " + StatColumns.android_id + " , " + StatColumns.stat_id + " ) ON CONFLICT IGNORE )";
        db.execSQL(sql);

        sql = "CREATE TABLE " + SEASON_TABLE + " (" +
                SeasonColumns.android_id + " TEXT NOT NULL , " +
                SeasonColumns.season_id + " INTEGER NOT NULL DEFAULT 0 , " +
                SeasonColumns.season_name + " INTEGER NOT NULL ," +
                "UNIQUE ( " + SeasonColumns.android_id + " , " + SeasonColumns.season_id + " ) ON CONFLICT IGNORE )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            // Not Implemented
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1 && oldVersion != newVersion) {
            // Not Implemented
        }

    }
}
