package com.toddbray.basketballstats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewStats extends AppCompatActivity {
    DbDataSource db = new DbDataSource(this);
    TableLayout layout;
    TableRow row;
    TableRow.LayoutParams lp;
    List<StatModel> stats = new ArrayList<>();
    int mode;
    int game_id = 0;
    int player_id;
    String opp_name;
    int season_id = 2017;

    // This value only works on physical devices
    //private String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    private String m_androidId = "Todd Bray Marshmallow";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_players);

        // Gets extras passed in from Main Activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            game_id = extras.getInt(MainActivity.GAME_ID);
            player_id = extras.getInt(MainActivity.PLAYER_ID);
            opp_name = extras.getString(MainActivity.OPP);
            mode = extras.getInt(MainActivity.MODE);
        }

        // opens database
        db.open();

        layout = (TableLayout) findViewById(R.id.roster_table);
        row = new TableRow(this);
        lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        // Switch statement to display different stats options
        switch (mode) {
            case 1:
                displayHead(); // Displays stat headings
                getGameStats(); // Loads cumulative game stats into stats list
                createTable(); // Populates table with stats
                db.close(); // closes database
                break;
            case 2:
                displayHead(); // Displays stat headings
                getSeasonStats(); // Loads cumulative season stats into stats list
                createTable(); // Populates table with stats
                db.close(); // closes database
                break;
            case 3:
                getPlayerStats(); // Loads cumulative player stats into stats list
                displayPlayerHeader(); // Displays player information
                displayHead(); // Displays stat headings
                createTable(); // Populates table with stats
                displayGameStatsHeader(); // Displays stat headings
                displayGameStats(); // Loads individual game stats into stats list
                createTable(); // Populates table with stats
                db.close(); // closes database
            default:
                break;

        }

    }

    /* Function that retrieves and displays stats from Stats List */
    public void createTable() {
        for (int i = 0; i < stats.size(); i++) {
            // Loads current player
            StatModel currentPlayer = stats.get(i);
            PlayerModel player = db.getPlayer(currentPlayer.getPlayer_id(), m_androidId);

            // Creates new row
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            // Displays relavent info for the first column
            if (mode != 3) {
                displayStat(Integer.toString(player.getNumber()) + "   " + player.getLast_name() + ", " + player.getFirst_name(), row, 0);
            } else if (mode == 3 && game_id == 0) {
                displayStat("SEASON TOTALS", row, 0);
                game_id = MySqlLiteHelper.NEW_ROW;
            } else {
                GameModel gm = db.getGame(stats.get(i).getGame_id(), m_androidId);
                game_id = gm.getGame_id();
                DateFormat df = new SimpleDateFormat("MM/dd");
                displayStat(df.format(gm.getGame_date()) + " " + gm.getOpp_name(), row,  0);
            }

            // Saves stats from stat model into string
            String stats [] = {Integer.toString(currentPlayer.getTwo_pointer_made() + currentPlayer.getThree_pointer_made()),
                    Integer.toString(currentPlayer.getTwo_pointer() + currentPlayer.getThree_pointer()),
                    Integer.toString(getPercentage((currentPlayer.getTwo_pointer_made() + currentPlayer.getThree_pointer_made()),(currentPlayer.getTwo_pointer() + currentPlayer.getThree_pointer()))) + "%",
                    Integer.toString(currentPlayer.getTwo_pointer_made()),
                    Integer.toString(currentPlayer.getTwo_pointer()),
                    Integer.toString(getPercentage(currentPlayer.getTwo_pointer_made(),currentPlayer.getTwo_pointer())) + "%",
                    Integer.toString(currentPlayer.getThree_pointer_made()),
                    Integer.toString(currentPlayer.getThree_pointer()),
                    Integer.toString(getPercentage(currentPlayer.getThree_pointer_made(),currentPlayer.getThree_pointer())) + "%",
                    Integer.toString(currentPlayer.getFree_throw_made()),
                    Integer.toString(currentPlayer.getFree_throw()),
                    Integer.toString(getPercentage(currentPlayer.getFree_throw_made(),currentPlayer.getFree_throw())) + "%",
                    Integer.toString(currentPlayer.getO_rebound() + currentPlayer.getD_rebound()),
                    Integer.toString(currentPlayer.getO_rebound()),
                    Integer.toString(currentPlayer.getD_rebound()),
                    Integer.toString(currentPlayer.getAssist()),
                    Integer.toString(currentPlayer.getSteal()),
                    Integer.toString(currentPlayer.getTurnover()),
                    Integer.toString(currentPlayer.getCharge())};

            // Loops through array to display stats on screen
            for (int i2 = 0; i2 < stats.length; i2++) {
                displayStat(stats[i2], row, 1);
            }
            layout.addView(row);

        }


    }

    /* Creates textview and adds to row */
    public void displayStat(String text, TableRow row, int align) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
        tv.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (align == 0) {
            tv.setGravity(Gravity.LEFT);
        } else {
            tv.setGravity(Gravity.CENTER);
        }
        tv.setTextSize(16);
        row.addView(tv);
    }

    /* Displays headings for each of the stat categories on screen */
    public void displayHead() {
        if (mode != 3) {
            displayStat("Player", row, 0);
        }
        else {
            displayStat("", row, 1);
        }
        String headers [] = {"FGM", "FGA", "FG%", "FG2M", "FG2A", "FG2%", "FG3M", "FG3A", "FG3%",
                            "FTM", "FTA", "FT%", "REB", "OREB", "DREB", "AST", "STL", "TO", "CHG"};
        for (int i = 0; i < headers.length; i++) {
            displayStat(headers[i], row, 1);
        }
        layout.addView(row);
    }

    /* Calculates percentage of 2 integer values and returns int */
    public int getPercentage(int i, int i2) {
        double percentage = (double) (i) / (double) (i2);
        percentage *= 100;
        return (int) Math.round(percentage);
    }

    /* Retrieves game stats from DB and loads them into stats List */
    public void getGameStats() {
        stats = db.getGameStats(game_id, m_androidId);
    }

    /* Retrieves player stats from DB and loads them into stats List */
    public void getPlayerStats() {
        if (db.checkStatPlayer(player_id, m_androidId)) {
            stats.add(db.getSeasonStats(player_id, season_id, m_androidId));
        } else {
            StatModel newPlayer = new StatModel(m_androidId);
            newPlayer.setPlayer_id(player_id);
            stats.add(newPlayer);
        }

    }

    /* Retrieves season stats from DB and loads them into stats List */
    public void getSeasonStats() {
        stats = new ArrayList<>();
        List<PlayerModel> players = db.getAllPlayers();
        for (int i = 0; i < players.size(); i++) {
            if (db.checkStatPlayer(players.get(i).getPlayer_id(), m_androidId)) {
                stats.add(db.getSeasonStats(players.get(i).getPlayer_id(),season_id, m_androidId)); // TODO: We need to reference a Season_ID where 1 is
            } else {
                StatModel newPlayer = new StatModel(m_androidId);
                newPlayer.setPlayer_id(players.get(i).getPlayer_id());
                stats.add(newPlayer);
            }
        }

    }

    /* Displays header for individual player stats */
    public void displayPlayerHeader() {
        TextView tv = new TextView(this);
        TextView tv2 = new TextView(this);
        PlayerModel pm = db.getPlayer(player_id, m_androidId);
        tv.setText("#" + Integer.toString(pm.getNumber()) + " " + pm.getFirst_name() + " " + pm.getLast_name());
        tv2.setText("SEASON STATS");
        tv.setGravity(Gravity.CENTER);
        tv2.setGravity(Gravity.CENTER);
        tv.setTextSize(36);
        tv2.setTextSize(24);
        layout.addView(tv);
        layout.addView(tv2);
    }

    /* Displays header for game stats */
    public void displayGameStatsHeader() {
        TextView tv = new TextView(this);
        tv.setText("GAME STATS");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(24);
        layout.addView(tv);
    }

    public void displayGameStats() {
        stats = db.getPlayerStats(player_id, m_androidId);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent iActivity_Main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(iActivity_Main);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
