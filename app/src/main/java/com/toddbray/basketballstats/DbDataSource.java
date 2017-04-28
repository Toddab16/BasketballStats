package com.toddbray.basketballstats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    private String insertQuery, updateQuery;

    public DbDataSource(Context context) {
        databaseHelper = new MySqlLiteHelper(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }


    public void runQuery(String query) {
        database.execSQL(query);
    }

    public GameModel createGame(GameModel gameModel) {
        ContentValues contentValues = new ContentValues();

        if(gameModel.getGame_id() != -99)
        {
            contentValues.put(MySqlLiteHelper.GameColumns.game_id.toString(), gameModel.getGame_id());

            /*
            database.execSQL("UPDATE " + MySqlLiteHelper.GAME_TABLE +
                " SET " + MySqlLiteHelper.GameColumns.game_date.toString() + " = '" + gameModel.getGame_date().toString() + "'," +
                " WHERE " + MySqlLiteHelper.StatColumns.game_id.toString() + " = '" + gameModel.getGame_id() + "'" );

            // Old non working (remove after execSQL is good)
            database.update(MySqlLiteHelper.GAME_TABLE,
                    MySqlLiteHelper.GameColumns.game_id.toString() + "=?", gameModel.getGame_id());
            */
        }
        else {


            long id = database.insert(MySqlLiteHelper.GAME_TABLE,
                    null, contentValues);

            gameModel.setGame_id((int) id);
        }

        return gameModel;
    }

    public void removeGame(int id) {
        database.delete(MySqlLiteHelper.STAT_TABLE, (MySqlLiteHelper.StatColumns.game_id.toString() + "=?"), new String[]{Integer.toString(id)});
        database.delete(MySqlLiteHelper.GAME_TABLE, (MySqlLiteHelper.GameColumns.game_id.toString() + "=?"), new String[]{Integer.toString(id)});
    }

    public List<GameModel> getAllGames() {
        List<GameModel> games = new ArrayList<>();

        String columns[] = MySqlLiteHelper.GameColumns.names();

        Cursor cursor = database.query(MySqlLiteHelper.GAME_TABLE,
                columns,
                null, null, null, null, null);

        cursor.moveToNext();
        while (!cursor.isAfterLast()) {
            GameModel game = cursorToGameModel(cursor);
            games.add(game);
            cursor.moveToNext();
        }
        cursor.close();

        return games;
    }

    private GameModel cursorToGameModel(Cursor cursor) {
        GameModel gameModel = new GameModel();

        // Build Integers
        int num = cursor.getInt(MySqlLiteHelper.GameColumns.android_id.ordinal());
        gameModel.setAndroid_id(num);

        num = cursor.getInt(MySqlLiteHelper.GameColumns.game_id.ordinal());
        gameModel.setGame_id(num);

        num = cursor.getInt(MySqlLiteHelper.GameColumns.season_id.ordinal());
        gameModel.setSeason_id(num);

        // Build Strings
        String s = cursor.getString(MySqlLiteHelper.GameColumns.opp_name.ordinal());
        gameModel.setOpp_name(s);

        s = cursor.getString(MySqlLiteHelper.GameColumns.location.ordinal());
        gameModel.setLocation(s);

        s = cursor.getString(MySqlLiteHelper.GameColumns.venue.ordinal());
        gameModel.setVenue(s);

        // Build Dates
        String dateStr = cursor.getString(MySqlLiteHelper.GameColumns.game_date.ordinal());
        gameModel.setGame_date(GetDate(dateStr));

        dateStr = cursor.getString(MySqlLiteHelper.GameColumns.girls_jv.ordinal());
        gameModel.setGirls_jv(GetDate(dateStr));

        dateStr = cursor.getString(MySqlLiteHelper.GameColumns.boys_jv.ordinal());
        gameModel.setBoys_jv(GetDate(dateStr));

        dateStr = cursor.getString(MySqlLiteHelper.GameColumns.girls_v.ordinal());
        gameModel.setGirls_v(GetDate(dateStr));

        dateStr = cursor.getString(MySqlLiteHelper.GameColumns.boys_v.ordinal());
        gameModel.setBoys_v(GetDate(dateStr));

        return gameModel;
    }

    public PlayerModel createPlayer(PlayerModel playerModel) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySqlLiteHelper.PlayerColumns.android_id.toString(), playerModel.getAndroid_id());
        contentValues.put(MySqlLiteHelper.PlayerColumns.first_name.toString(), playerModel.getFirst_name().toString());
        contentValues.put(MySqlLiteHelper.PlayerColumns.last_name.toString(), playerModel.getLast_name().toString());
        contentValues.put(MySqlLiteHelper.PlayerColumns.year.toString(), playerModel.getYear().toString());
        contentValues.put(MySqlLiteHelper.PlayerColumns.number.toString(), playerModel.getNumber());

        // insert into comment (comment, date_created) values ('hi', '12:00 AM')
        long id = database.insert(MySqlLiteHelper.PLAYER_TABLE,
                null, contentValues);

        playerModel.setPlayer_id((int) id);

        return playerModel;
    }

    public void removePlayer(int id) {
        database.delete(MySqlLiteHelper.STAT_TABLE, (MySqlLiteHelper.StatColumns.player_id.toString() + "=?"), new String[]{Integer.toString(id)});
        database.delete(MySqlLiteHelper.PLAYER_TABLE, (MySqlLiteHelper.PlayerColumns.player_id.toString() + "=?"), new String[]{Integer.toString(id)});
    }

    public List<PlayerModel> getAllPlayers() {
        List<PlayerModel> players = new ArrayList<>();

        String columns[] = MySqlLiteHelper.PlayerColumns.names();

        Cursor cursor = database.query(MySqlLiteHelper.PLAYER_TABLE,
                columns,
                null, null, null, null, null);

        cursor.moveToNext();
        while (!cursor.isAfterLast()) {
            PlayerModel player = cursorToPlayerModel(cursor);
            players.add(player);
            cursor.moveToNext();
        }
        cursor.close();

        return players;
    }

    private PlayerModel cursorToPlayerModel(Cursor cursor) {
        PlayerModel playerModel = new PlayerModel();

        // Build Integers
        int num = cursor.getInt(MySqlLiteHelper.PlayerColumns.android_id.ordinal());
        playerModel.setAndroid_id(num);

        num = cursor.getInt(MySqlLiteHelper.PlayerColumns.player_id.ordinal());
        playerModel.setPlayer_id(num);

        num = cursor.getInt(MySqlLiteHelper.PlayerColumns.number.ordinal());
        playerModel.setNumber(num);

        // Build Strings
        String s = cursor.getString(MySqlLiteHelper.PlayerColumns.first_name.ordinal());
        playerModel.setFirst_name(s);

        s = cursor.getString(MySqlLiteHelper.PlayerColumns.last_name.ordinal());
        playerModel.setLast_name(s);

        s = cursor.getString(MySqlLiteHelper.PlayerColumns.year.ordinal());
        playerModel.setYear(s);

        return playerModel;
    }

    public void createStat(StatModel statModel) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySqlLiteHelper.StatColumns.game_id.toString(), statModel.getGame_id());
        contentValues.put(MySqlLiteHelper.StatColumns.player_id.toString(), statModel.getPlayer_id());
        contentValues.put(MySqlLiteHelper.StatColumns.steal.toString(), statModel.getSteal());
        contentValues.put(MySqlLiteHelper.StatColumns.d_rebound.toString(), statModel.getD_rebound());
        contentValues.put(MySqlLiteHelper.StatColumns.o_rebound.toString(), statModel.getO_rebound());
        contentValues.put(MySqlLiteHelper.StatColumns.assist.toString(), statModel.getAssist());
        contentValues.put(MySqlLiteHelper.StatColumns.turnover.toString(), statModel.getTurnover());
        contentValues.put(MySqlLiteHelper.StatColumns.two_pointer.toString(), statModel.getTwo_pointer());
        contentValues.put(MySqlLiteHelper.StatColumns.two_pointer_made.toString(), statModel.getTwo_pointer_made());
        contentValues.put(MySqlLiteHelper.StatColumns.three_pointer.toString(), statModel.getThree_pointer());
        contentValues.put(MySqlLiteHelper.StatColumns.three_pointer_made.toString(), statModel.getThree_pointer_made());
        contentValues.put(MySqlLiteHelper.StatColumns.free_throw.toString(), statModel.getFree_throw());
        contentValues.put(MySqlLiteHelper.StatColumns.free_throw_made.toString(), statModel.getFree_throw_made());
        contentValues.put(MySqlLiteHelper.StatColumns.charge.toString(), statModel.getCharge());

        // insert into comment (comment, date_created) values ('hi', '12:00 AM')
        database.insert(MySqlLiteHelper.STAT_TABLE,
                null, contentValues);
    }

    public void removeStat(int g_id, int p_id) {
        database.execSQL("DELETE FROM " + MySqlLiteHelper.STAT_TABLE +
                " WHERE " + MySqlLiteHelper.StatColumns.game_id.toString() + " = '" + g_id + "'" +
                " AND " + MySqlLiteHelper.StatColumns.player_id.toString() + " = '" + p_id + "'" );
    }

    public List<StatModel> getAllStats() {
        List<StatModel> stats = new ArrayList<>();

        String columns[] = MySqlLiteHelper.StatColumns.names();

        Cursor cursor = database.query(MySqlLiteHelper.STAT_TABLE,
                columns,
                null, null, null, null, null);

        cursor.moveToNext();
        while (!cursor.isAfterLast()) {
            StatModel stat = cursorToStatModel(cursor);
            stats.add(stat);
            cursor.moveToNext();
        }
        cursor.close();

        return stats;
    }

    private StatModel cursorToStatModel(Cursor cursor) {
        StatModel statModel = new StatModel();

        // Build Integers
        int num = cursor.getInt(MySqlLiteHelper.StatColumns.android_id.ordinal());
        statModel.setAndroid_id(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.stat_id.ordinal());
        statModel.setStat_id(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.game_id.ordinal());
        statModel.setGame_id(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.player_id.ordinal());
        statModel.setPlayer_id(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.d_rebound.ordinal());
        statModel.setD_rebound(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.steal.ordinal());
        statModel.setSteal(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.three_pointer.ordinal());
        statModel.setThree_pointer(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.three_pointer_made.ordinal());
        statModel.setThree_pointer_made(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.assist.ordinal());
        statModel.setAssist(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.o_rebound.ordinal());
        statModel.setO_rebound(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.turnover.ordinal());
        statModel.setTurnover(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.two_pointer.ordinal());
        statModel.setTwo_pointer(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.two_pointer_made.ordinal());
        statModel.setTwo_pointer_made(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.free_throw.ordinal());
        statModel.setFree_throw(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.free_throw_made.ordinal());
        statModel.setFree_throw_made(num);

        num = cursor.getInt(MySqlLiteHelper.StatColumns.charge.ordinal());
        statModel.setCharge(num);

        return statModel;
    }

    public SeasonModel createSeason(SeasonModel seasonModel) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySqlLiteHelper.PlayerColumns.last_name.toString(), seasonModel.getSeason_id());

        // insert into comment (comment, date_created) values ('hi', '12:00 AM')
        long id = database.insert(MySqlLiteHelper.PLAYER_TABLE,
                null, contentValues);

        seasonModel.setSeason_id((int) id);

        return seasonModel;
    }

    public void removeSeason(int id) {
        database.delete(MySqlLiteHelper.STAT_TABLE, (MySqlLiteHelper.StatColumns.player_id.toString() + "=?"), new String[]{Integer.toString(id)});
        database.delete(MySqlLiteHelper.GAME_TABLE, (MySqlLiteHelper.GameColumns.game_id.toString() + "=?"), new String[]{Integer.toString(id)});
        database.delete(MySqlLiteHelper.SEASON_TABLE, (MySqlLiteHelper.SeasonColumns.season_id.toString() + "=?"), new String[]{Integer.toString(id)});
    }

    public List<SeasonModel> getAllSeasons() {
        List<SeasonModel> seasons = new ArrayList<>();

        String columns[] = MySqlLiteHelper.SeasonColumns.names();

        Cursor cursor = database.query(MySqlLiteHelper.SEASON_TABLE,
                columns,
                null, null, null, null, null);

        cursor.moveToNext();
        while (!cursor.isAfterLast()) {
            SeasonModel season = cursorToSeasonModel(cursor);
            seasons.add(season);
            cursor.moveToNext();
        }
        cursor.close();

        return seasons;
    }

    private SeasonModel cursorToSeasonModel(Cursor cursor) {
        SeasonModel seasonModel = new SeasonModel();

        // Build Integers
        int num = cursor.getInt(MySqlLiteHelper.PlayerColumns.android_id.ordinal());
        seasonModel.setAndroid_id(num);

        num = cursor.getInt(MySqlLiteHelper.PlayerColumns.player_id.ordinal());
        seasonModel.setSeason_id(num);

        num = cursor.getInt(MySqlLiteHelper.PlayerColumns.number.ordinal());
        seasonModel.setSeason_name(num);

        return seasonModel;
    }

    public Date GetDate(String d) {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
        GameModel testModel = new GameModel();

        try {
            Date date = dateFormat.parse(d);
            testModel.setGame_date(date);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
