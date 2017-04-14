package com.toddbray.basketballstats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Brad on 4/14/2017.
 */

public class DbDataSource {
    private SQLiteDatabase database;
    private MySqlLiteHelper databaseHelper;

    public DbDataSource(Context context) {
        databaseHelper = new MySqlLiteHelper(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Comment createComment(String commentStr) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySqlLiteHelper.CommentColumns.comment.toString(), commentStr);
        Date dateCreated = new Date();
        contentValues.put(MySqlLiteHelper.CommentColumns.date_created.toString(), dateCreated.toString());

        /* insert into comment (comment, date_created) values ('hi', '12:00 AM') */
        long id = database.insert(MySqlLiteHelper.COMMENT_TABLE,
                null, contentValues);

        String[] columnNames = MySqlLiteHelper.CommentColumns.names();

        // select * from comment where comment_id = 2
        Cursor cursor = database.query(MySqlLiteHelper.COMMENT_TABLE,
                columnNames,
                MySqlLiteHelper.CommentColumns.comment_id + " = " + id,
                null, null, null, null
        );

        cursor.moveToFirst();
        Comment comment = cursorToComment(cursor);
        cursor.close();

        return comment;
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();

        String columns[] = MySqlLiteHelper.CommentColumns.names();

        Cursor cursor = database.query(MySqlLiteHelper.COMMENT_TABLE,
                columns,
                null, null, null, null, null);

        cursor.moveToNext();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        cursor.close();

        return comments;
    }

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();

        int commentId = cursor.getInt(MySqlLiteHelper.CommentColumns.comment_id.ordinal());
        comment.setCommentId(commentId);

        String com = cursor.getString(MySqlLiteHelper.CommentColumns.comment.ordinal());
        comment.setComment(com);

        String dateStr = cursor.getString(MySqlLiteHelper.CommentColumns.date_created.ordinal());

        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

        try {
            Date date = dateFormat.parse(dateStr);
            comment.setDateCreated(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return comment;
    }
}
