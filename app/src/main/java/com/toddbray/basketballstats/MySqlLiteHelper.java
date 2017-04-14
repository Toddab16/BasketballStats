package com.toddbray.basketballstats;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brad on 4/14/2017.
 */

public class MySqlLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "comment.sqlite";
    private static final int DB_VERSION = 1;

    public static final String COMMENT_TABLE = "Comments";

    public enum CommentColumns {
        comment_id, comment, date_created;

        public static String[] names() {
            CommentColumns[] v = values();
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
        String sql = "CREATE TABLE " + COMMENT_TABLE + " (" +
                CommentColumns.comment_id + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
                CommentColumns.comment + " TEXT NOT NULL , " +
                CommentColumns.date_created + " TEXT NOT NULL )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            String sql = "alter table " + COMMENT_TABLE + " add column extra integer";
            db.execSQL(sql);

            sql = "update " + COMMENT_TABLE + " set extra = 42";
            db.execSQL(sql);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1 && oldVersion != newVersion) {

            // The reason we need all this code is because
            // SQLite does not support ALTER TABLE ... DROP COLUMN
            try {
                db.beginTransaction();

                // copy table that needs column dropped
                String sql = "alter table " + COMMENT_TABLE + " rename to tmp";
                db.execSQL(sql);

                // recreate the table in the old schema
                sql = "CREATE TABLE " + COMMENT_TABLE + " (" +
                        CommentColumns.comment_id + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
                        CommentColumns.comment + " TEXT NOT NULL , " +
                        CommentColumns.date_created + " TEXT NOT NULL )";
                db.execSQL(sql);

                // copy the data we want from the old table
                sql = "insert into " + COMMENT_TABLE +
                        " select " +
                        CommentColumns.comment_id + ", " +
                        CommentColumns.comment + ", " +
                        CommentColumns.date_created +
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


        }

    }
}
