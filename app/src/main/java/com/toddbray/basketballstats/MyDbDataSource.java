package com.toddbray.basketballstats;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brad on 4/24/2017.
 */

public class MyDbDataSource extends AsyncTask<Context, Integer, String> {

    private static final String MYSQL_HOST = "jdbc:mysql://foxi.wuffhost.ovh:3306/t_bray_bball_stats";
    private static final String MYSQL_USERNAME = "t_bray_19751087";
    private static final String MYSQL_PASSWORD = "bballadmin16";

    private List<GameModel> games;
    private List<PlayerModel> players;
    private List<StatModel> stats;
    private List<SeasonModel> seasons;

    private String insertQuery;
    private String updateQuery;

    /*
    Synchronize MySQL
    INSERT INTO table (id, name, age) VALUES(1, "A", 19) ON DUPLICATE KEY UPDATE
    name="A", age=19

    Synchronize SQLite
    INSERT OR IGNORE INTO table_name VALUES ($variable_id, $variable_name);
    UPDATE visits SET field_name = $variable_field WHERE id = $variable_id;
     */


    @Override
    protected void onPreExecute() {
        /*
        TextView tv = (TextView) findViewById(R.id.result_textView);
        tv.setText("Download 0% complete");
        progressBar.setProgress(0);
        */
        //DbDataSource sqlite = new DbDataSource();

    }

    @Override
    protected String doInBackground(Context... contexts) {
        try {
            // TODO: Implement Progress Update

            // Open SQLite connection
            DbDataSource sqLite = new DbDataSource(contexts[0]);

            String result = "Database connection was successful\n";

            // Open MySQL connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(MYSQL_HOST, MYSQL_USERNAME, MYSQL_PASSWORD);

            Statement st = con.createStatement();

            ///////////////////////// GAME DATA ////////////////////////////////////////////////////

            // Send all game data
            games = sqLite.getAllGames();
            for (GameModel game : games) {
                createGameQuery(game);
                st.executeQuery(insertQuery);
                st.executeQuery(updateQuery);
            }

            // Receive all MySQL game data
            games = getAllGames(st);

            for (GameModel game : games) {
                createGameQuery(game);
                sqLite.runQuery(insertQuery);
                sqLite.runQuery(updateQuery);
            }

            ///////////////////////// END GAME DATA ////////////////////////////////////////////////

            ///////////////////////// PLAYER DATA //////////////////////////////////////////////////

            // Send all player data
            players = sqLite.getAllPlayers();
            for (PlayerModel player : players) {
                createPlayerQuery(player);
                st.executeQuery(insertQuery);
                st.executeQuery(updateQuery);
            }

            // Get all MySQL player data
            players = getAllPlayers(st);

            for (PlayerModel player : players) {
                createPlayerQuery(player);
                sqLite.runQuery(insertQuery);
                sqLite.runQuery(updateQuery);
            }

            ///////////////////////// END PLAYER DATA //////////////////////////////////////////////

            ///////////////////////// STAT DATA ////////////////////////////////////////////////////

            // Send all stat data
            stats = sqLite.getAllStats();
            for (StatModel stat : stats) {
                createStatQuery(stat);
                st.executeQuery(insertQuery);
                st.executeQuery(updateQuery);
            }

            // Get all MySQL stat data
            stats = getAllStats(st);

            for (StatModel stat : stats) {
                createStatQuery(stat);
                sqLite.runQuery(insertQuery);
                sqLite.runQuery(updateQuery);
            }

            ///////////////////////// END STAT DATA ////////////////////////////////////////////////

            ///////////////////////// SEASON DATA //////////////////////////////////////////////////

            // Send all season data
            seasons = sqLite.getAllSeasons();
            for (SeasonModel season : seasons) {
                createSeasonQuery(season);
                st.executeQuery(insertQuery);
                st.executeQuery(updateQuery);
            }

            // Get all MySQL season data
            seasons = getAllSeasons(st);

            for (SeasonModel season : seasons) {
                createSeasonQuery(season);
                sqLite.runQuery(insertQuery);
                sqLite.runQuery(updateQuery);
            }

            ///////////////////////// END SEASON DATA //////////////////////////////////////////////

            con.close();
            return result;
        }
        catch(Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        /*
        TextView tv = (TextView) findViewById(R.id.result_textView);
        int percent = (100 * values[0]) / values[1];
        tv.setText("Download " + percent + "% complete");
        progressBar.setProgress(percent);
        */
    }

    @Override
    protected void onPostExecute(String s) {
        /*
        super.onPostExecute(integer);
        TextView tv = (TextView) findViewById(R.id.result_textView);
        tv.setText("Download complete.  Downloaded " + integer + " bytes");
        */
    }

    @Override
    protected void onCancelled() {
        /*
        TextView tv = (TextView) findViewById(R.id.result_textView);
        tv.setText("Download canceled");
        */
    }

    private void createGameQuery(GameModel gameModel) {

        insertQuery = "INSERT OR IGNORE INTO " + MySqlLiteHelper.GAME_TABLE +
                " VALUES ( "+ gameModel.getGame_id() + " , " +
                gameModel.getSeason_id() + " , " +
                "'" + gameModel.getGame_date().toString() + "' , " +
                "'" + gameModel.getLocation() + "' , " +
                "'" + gameModel.getVenue() + "' , " +
                "'" + gameModel.getGirls_jv().toString() + "' , " +
                "'" + gameModel.getBoys_jv().toString() + "' , " +
                "'" + gameModel.getGirls_v().toString() + "' , " +
                "'" + gameModel.getBoys_v().toString() + "' , " +
                "'" + gameModel.getOpp_name() + "'" +
                " )";


        updateQuery = "UPDATE " + MySqlLiteHelper.GAME_TABLE + " " +
                "SET " + MySqlLiteHelper.GameColumns.season_id.toString() + " = " + gameModel.getSeason_id() + " , " +
                "SET " + MySqlLiteHelper.GameColumns.game_date.toString() + " = '" + gameModel.getGame_date().toString() + "' , " +
                "SET " + MySqlLiteHelper.GameColumns.location.toString() + " = '" + gameModel.getLocation() + "' , " +
                "SET " + MySqlLiteHelper.GameColumns.venue.toString() + " = '" + gameModel.getVenue() + "' , " +
                "SET " + MySqlLiteHelper.GameColumns.girls_jv.toString() + " = '" + gameModel.getGirls_jv().toString() + "' , " +
                "SET " + MySqlLiteHelper.GameColumns.boys_jv.toString() + " = '" + gameModel.getBoys_jv().toString() + "' , " +
                "SET " + MySqlLiteHelper.GameColumns.girls_v.toString() + " = '" + gameModel.getGirls_v().toString() + "' , " +
                "SET " + MySqlLiteHelper.GameColumns.boys_v.toString() + " = '" + gameModel.getBoys_v().toString() + "' , " +
                "SET " + MySqlLiteHelper.GameColumns.opp_name.toString() + " = '" + gameModel.getOpp_name() + "' " +
                "WHERE " + MySqlLiteHelper.GameColumns.game_id.toString() + " = " + gameModel.getGame_id();
    }

    private List<GameModel> getAllGames(Statement st) throws SQLException {
        List<GameModel> games = new ArrayList<>();

        ResultSet rs = st.executeQuery("SELECT * FROM " + MySqlLiteHelper.GAME_TABLE);
        //ResultSetMetaData rsmd = rs.getMetaData();

        rs.first();
        while(!rs.isAfterLast()) {

            games.add(resultSetToGameModel(rs));
            rs.next();
        }
        rs.close();

        return games;
    }

    private GameModel resultSetToGameModel(ResultSet rs) throws SQLException {
        GameModel gameModel = new GameModel();

        gameModel.setGame_id(rs.getInt(MySqlLiteHelper.GameColumns.game_id.toString()));
        gameModel.setSeason_id(rs.getInt(MySqlLiteHelper.GameColumns.season_id.toString()));
        gameModel.setGame_date(rs.getDate(MySqlLiteHelper.GameColumns.game_date.toString()));
        gameModel.setLocation(rs.getString(MySqlLiteHelper.GameColumns.location.toString()));
        gameModel.setVenue(rs.getString(MySqlLiteHelper.GameColumns.venue.toString()));
        gameModel.setGirls_jv(rs.getDate(MySqlLiteHelper.GameColumns.girls_jv.toString()));
        gameModel.setBoys_jv(rs.getDate(MySqlLiteHelper.GameColumns.boys_jv.toString()));
        gameModel.setGirls_v(rs.getDate(MySqlLiteHelper.GameColumns.girls_v.toString()));
        gameModel.setBoys_v(rs.getDate(MySqlLiteHelper.GameColumns.boys_v.toString()));
        gameModel.setOpp_name(rs.getString(MySqlLiteHelper.GameColumns.opp_name.toString()));

        return gameModel;
    }

    private void createPlayerQuery(PlayerModel playerModel) {

        insertQuery = "INSERT OR IGNORE INTO " + MySqlLiteHelper.PLAYER_TABLE +
                " VALUES ( "+ playerModel.getPlayer_id() + " , " +
                "'" + playerModel.getFirst_name() + "' , " +
                "'" + playerModel.getLast_name() + "' , " +
                "'" + playerModel.getYear() + "' , " +
                playerModel.getNumber() +
                " )";

        updateQuery = "UPDATE " + MySqlLiteHelper.PLAYER_TABLE + " " +
                "SET " + MySqlLiteHelper.PlayerColumns.first_name.toString() + " = '" + playerModel.getFirst_name() + "' , " +
                "SET " + MySqlLiteHelper.PlayerColumns.last_name.toString() + " = '" + playerModel.getLast_name() + "' , " +
                "SET " + MySqlLiteHelper.PlayerColumns.year.toString() + " = '" + playerModel.getYear() + "' , " +
                "SET " + MySqlLiteHelper.PlayerColumns.number.toString() + " = '" + playerModel.getNumber() + "' " +
                "WHERE " + MySqlLiteHelper.GameColumns.game_id.toString() + " = " + playerModel.getPlayer_id();
    }

    private List<PlayerModel> getAllPlayers(Statement st) throws SQLException {
        List<PlayerModel> players = new ArrayList<>();

        ResultSet rs = st.executeQuery("SELECT * FROM " + MySqlLiteHelper.PLAYER_TABLE);
        //ResultSetMetaData rsmd = rs.getMetaData();

        rs.first();
        while(!rs.isAfterLast()) {

            players.add(resultSetToPlayerModel(rs));
            rs.next();
        }
        rs.close();

        return players;
    }

    private PlayerModel resultSetToPlayerModel(ResultSet rs) throws SQLException {
        PlayerModel playerModel = new PlayerModel();

        playerModel.setPlayer_id(rs.getInt(MySqlLiteHelper.PlayerColumns.player_id.toString()));
        playerModel.setFirst_name(rs.getString(MySqlLiteHelper.PlayerColumns.first_name.toString()));
        playerModel.setLast_name(rs.getString(MySqlLiteHelper.PlayerColumns.last_name.toString()));
        playerModel.setYear(rs.getString(MySqlLiteHelper.PlayerColumns.year.toString()));
        playerModel.setNumber(rs.getInt(MySqlLiteHelper.PlayerColumns.number.toString()));

        return playerModel;
    }

    private void createStatQuery(StatModel statModel) {

        insertQuery = "INSERT OR IGNORE INTO " + MySqlLiteHelper.STAT_TABLE +
                " VALUES ( "+ statModel.getGame_id() + " , " +
                statModel.getPlayer_id() + " , " +
                statModel.getO_rebound() + " , " +
                statModel.getD_rebound() + " , " +
                statModel.getAssist() + " , " +
                statModel.getSteal() + " , " +
                statModel.getTurnover() + " , " +
                statModel.getTwo_pointer() + " , " +
                statModel.getTwo_pointer_made() + " , " +
                statModel.getThree_pointer() + " , " +
                statModel.getThree_pointer_made() + " , " +
                statModel.getFree_throw() + " , " +
                statModel.getFree_throw_made() + " , " +
                statModel.getCharge() +
                " )";

        updateQuery = "UPDATE " + MySqlLiteHelper.STAT_TABLE + " " +
                "SET " + MySqlLiteHelper.StatColumns.game_id.toString() + " = " + statModel.getGame_id() + " , " +
                "SET " + MySqlLiteHelper.StatColumns.player_id.toString() + " = " + statModel.getPlayer_id() + " , " +
                "SET " + MySqlLiteHelper.StatColumns.o_rebound.toString() + " = " + statModel.getO_rebound() + " , " +
                "SET " + MySqlLiteHelper.StatColumns.d_rebound.toString() + " = " + statModel.getD_rebound() + " , " +
                "SET " + MySqlLiteHelper.StatColumns.assist.toString() + " = " + statModel.getAssist() + " , " +
                "SET " + MySqlLiteHelper.StatColumns.steal.toString() + " = " + statModel.getSteal() + " , " +
                "SET " + MySqlLiteHelper.StatColumns.turnover.toString() + " = " + statModel.getTurnover() + " , " +
                "SET " + MySqlLiteHelper.StatColumns.two_pointer.toString() + " = " + statModel.getTwo_pointer() + " , " +
                "SET " + MySqlLiteHelper.StatColumns.two_pointer_made.toString() + " = " + statModel.getTwo_pointer_made() + " , " +
                "SET " + MySqlLiteHelper.StatColumns.three_pointer.toString() + " = " + statModel.getThree_pointer() + " , " +
                "SET " + MySqlLiteHelper.StatColumns.three_pointer_made.toString() + " = " + statModel.getThree_pointer_made() + " , " +
                "SET " + MySqlLiteHelper.StatColumns.free_throw.toString() + " = " + statModel.getFree_throw() + " , " +
                "SET " + MySqlLiteHelper.StatColumns.free_throw_made.toString() + " = " + statModel.getFree_throw_made() + " , " +
                "SET " + MySqlLiteHelper.StatColumns.charge.toString() + " = " + statModel.getCharge() + " " +
                "WHERE " + MySqlLiteHelper.StatColumns.game_id.toString() + " = " + statModel.getGame_id();
    }

    private List<StatModel> getAllStats(Statement st) throws SQLException {
        List<StatModel> stats = new ArrayList<>();

        ResultSet rs = st.executeQuery("SELECT * FROM " + MySqlLiteHelper.STAT_TABLE);
        //ResultSetMetaData rsmd = rs.getMetaData();

        rs.first();
        while(!rs.isAfterLast()) {

            stats.add(resultSetToStatModel(rs));
            rs.next();
        }
        rs.close();

        return stats;
    }

    private StatModel resultSetToStatModel(ResultSet rs) throws SQLException {
        StatModel statModel = new StatModel();

        statModel.setGame_id(rs.getInt(MySqlLiteHelper.StatColumns.game_id.toString()));
        statModel.setPlayer_id(rs.getInt(MySqlLiteHelper.StatColumns.player_id.toString()));
        statModel.setO_rebound(rs.getInt(MySqlLiteHelper.StatColumns.o_rebound.toString()));
        statModel.setD_rebound(rs.getInt(MySqlLiteHelper.StatColumns.d_rebound.toString()));
        statModel.setAssist(rs.getInt(MySqlLiteHelper.StatColumns.assist.toString()));
        statModel.setSteal(rs.getInt(MySqlLiteHelper.StatColumns.steal.toString()));
        statModel.setTurnover(rs.getInt(MySqlLiteHelper.StatColumns.turnover.toString()));
        statModel.setTwo_pointer(rs.getInt(MySqlLiteHelper.StatColumns.two_pointer.toString()));
        statModel.setTwo_pointer_made(rs.getInt(MySqlLiteHelper.StatColumns.two_pointer_made.toString()));
        statModel.setThree_pointer(rs.getInt(MySqlLiteHelper.StatColumns.three_pointer.toString()));
        statModel.setThree_pointer_made(rs.getInt(MySqlLiteHelper.StatColumns.three_pointer_made.toString()));
        statModel.setFree_throw(rs.getInt(MySqlLiteHelper.StatColumns.free_throw.toString()));
        statModel.setFree_throw_made(rs.getInt(MySqlLiteHelper.StatColumns.free_throw_made.toString()));
        statModel.setCharge(rs.getInt(MySqlLiteHelper.StatColumns.charge.toString()));

        return statModel;
    }

    private void createSeasonQuery(SeasonModel seasonModel) {

        insertQuery = "INSERT OR IGNORE INTO " + MySqlLiteHelper.SEASON_TABLE +
                " VALUES ( "+ seasonModel.getSeason_id() + " , " +
                seasonModel.getSeason_name() +
                " )";

        updateQuery = "UPDATE " + MySqlLiteHelper.SEASON_TABLE + " " +
                "SET " + MySqlLiteHelper.SeasonColumns.season_name.toString() + " = " + seasonModel.getSeason_name() + " " +
                "WHERE " + MySqlLiteHelper.SeasonColumns.season_id.toString() + " = " + seasonModel.getSeason_id();
    }

    private List<SeasonModel> getAllSeasons(Statement st) throws SQLException {
        List<SeasonModel> seasons = new ArrayList<>();

        ResultSet rs = st.executeQuery("SELECT * FROM " + MySqlLiteHelper.SEASON_TABLE);
        //ResultSetMetaData rsmd = rs.getMetaData();

        rs.first();
        while(!rs.isAfterLast()) {

            seasons.add(resultSetToSeasonModel(rs));
            rs.next();
        }
        rs.close();

        return seasons;
    }

    private SeasonModel resultSetToSeasonModel(ResultSet rs) throws SQLException {
        SeasonModel seasonModel = new SeasonModel();

        seasonModel.setSeason_id(rs.getInt(MySqlLiteHelper.SeasonColumns.season_id.toString()));
        seasonModel.setSeason_name(rs.getInt(MySqlLiteHelper.SeasonColumns.season_name.toString()));

        return seasonModel;
    }

}
