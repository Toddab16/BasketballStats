package com.toddbray.basketballstats;

import android.app.ListActivity;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Todd Desktop on 4/23/2017.
 */

public class ViewStats extends AppCompatActivity {
    DbDataSource db = new DbDataSource(this);
    TableLayout layout;
    List<StatModel> stats = new ArrayList<>();
    int mode;
    int game_id = 0;
    int player_id;
    String opp_name;

    // This value only works on physical devices
    //private String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    private String m_androidId = "Todd Bray Marshmallow";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_players);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            game_id = extras.getInt("GAME_ID");
            player_id = extras.getInt("PLAYER_ID");
            opp_name = extras.getString("OPP");
            mode = extras.getInt("MODE");
        }

        db.open();

        layout = (TableLayout) findViewById(R.id.roster_table);

        switch (mode) {
            case 1:
                displayHead();
                getGameStats();
                createTable();
                db.close();
                break;
            case 2:
                displayHead();
                getSeasonStats();
                createTable();
                db.close();
                break;
            case 3:
                getPlayerStats();
                displayPlayerHeader();
                displayHead();
                createTable();
                displayGameStatsHeader();
                displayGameStats();
                createTable();
                db.close();
            default:
                break;

        }

    }

    public void createTable() {
        for (int i = 0; i < stats.size(); i++) {
            StatModel currentPlayer = stats.get(i);
            PlayerModel player = db.getPlayer(currentPlayer.getPlayer_id(), m_androidId);
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
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
            displayStat(Integer.toString(currentPlayer.getTwo_pointer_made() + currentPlayer.getThree_pointer_made()), row, 1);
            displayStat(Integer.toString(currentPlayer.getTwo_pointer() + currentPlayer.getThree_pointer()), row, 1);
            displayStat(Integer.toString(getPercentage((currentPlayer.getTwo_pointer_made() + currentPlayer.getThree_pointer_made()),(currentPlayer.getTwo_pointer() + currentPlayer.getThree_pointer()))) + "%", row, 1);
            displayStat(Integer.toString(currentPlayer.getTwo_pointer_made()), row, 1);
            displayStat(Integer.toString(currentPlayer.getTwo_pointer()), row, 1);
            displayStat(Integer.toString(getPercentage(currentPlayer.getTwo_pointer_made(),currentPlayer.getTwo_pointer())) + "%", row, 1);
            displayStat(Integer.toString(currentPlayer.getThree_pointer_made()), row, 1);
            displayStat(Integer.toString(currentPlayer.getThree_pointer()), row, 1);
            displayStat(Integer.toString(getPercentage(currentPlayer.getThree_pointer_made(),currentPlayer.getThree_pointer())) + "%", row, 1);
            displayStat(Integer.toString(currentPlayer.getFree_throw_made()), row, 1);
            displayStat(Integer.toString(currentPlayer.getFree_throw()), row, 1);
            displayStat(Integer.toString(getPercentage(currentPlayer.getFree_throw_made(),currentPlayer.getFree_throw())) + "%", row, 1);
            displayStat(Integer.toString(currentPlayer.getO_rebound() + currentPlayer.getD_rebound()), row, 1);
            displayStat(Integer.toString(currentPlayer.getO_rebound()), row, 1);
            displayStat(Integer.toString(currentPlayer.getD_rebound()), row, 1);
            displayStat(Integer.toString(currentPlayer.getAssist()), row, 1);
            displayStat(Integer.toString(currentPlayer.getSteal()), row, 1);
            displayStat(Integer.toString(currentPlayer.getTurnover()), row, 1);
            displayStat(Integer.toString(currentPlayer.getCharge()), row, 1);
            layout.addView(row);

        }


    }


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

    public void displayHead() {
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        if (mode != 3) {
            displayStat("Player", row, 0);
        }
        else {
            displayStat("", row, 1);
        }
        displayStat("FGM", row, 1);
        displayStat("FGA", row, 1);
        displayStat("FG%", row, 1);
        displayStat("FG2M", row, 1);
        displayStat("FG2A", row, 1);
        displayStat("FG2%", row, 1);
        displayStat("FG3M", row, 1);
        displayStat("FG3A", row, 1);
        displayStat("FG3%", row, 1);
        displayStat("FTM", row, 1);
        displayStat("FTA", row, 1);
        displayStat("FT%", row, 1);
        displayStat("REB", row, 1);
        displayStat("OREB", row, 1);
        displayStat("DREB", row, 1);
        displayStat("AST", row, 1);
        displayStat("STL", row, 1);
        displayStat("TO", row, 1);
        displayStat("CHG", row, 1);
        layout.addView(row);
    }

    public int getPercentage(int i, int i2) {
        Double percentage = (double) (i) / (double) (i2);
        percentage *= 100;
        return percentage.intValue();
    }

    public void getGameStats() {
        stats = db.getGameStats(game_id, m_androidId);
    }

    public void getPlayerStats() {
        if (db.checkStatPlayer(player_id, m_androidId)) {
            stats.add(db.getSeasonStats(player_id, 1, m_androidId)); // TODO: We need to reference a Season_ID where 1 is
        } else {
            StatModel newPlayer = new StatModel(m_androidId);
            newPlayer.setPlayer_id(player_id);
            stats.add(newPlayer);
        }

    }

    public void getSeasonStats() {
        stats = new ArrayList<>();
        List<PlayerModel> players = db.getAllPlayers();
        for (int i = 0; i < players.size(); i++) {
            if (db.checkStatPlayer(players.get(i).getPlayer_id(), m_androidId)) {
                stats.add(db.getSeasonStats(players.get(i).getPlayer_id(),1, m_androidId)); // TODO: We need to reference a Season_ID where 1 is
            } else {
                StatModel newPlayer = new StatModel(m_androidId);
                newPlayer.setPlayer_id(players.get(i).getPlayer_id());
                stats.add(newPlayer);
            }
        }

    }

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



}
