package com.toddbray.basketballstats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class ViewPlayer extends AppCompatActivity {
    DbDataSource db = new DbDataSource(this);
    TableLayout layout;
    TableRow row;
    TableRow.LayoutParams lp;
    TextView[] tv = new TextView[3];
    TextView[] tvHead = new TextView[3];
    String[] headers = {"#", "PLAYER", "CLASS"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_players);
        layout = (TableLayout) findViewById(R.id.roster_table);
        row = new TableRow(this);
        lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        // Displays headings for table
        displayHead();

        // Opens database
        db.open();

        // Queries database for players and saves them in List
        List<PlayerModel> players = db.getAllPlayers();

        // Loops through list of players
        for (int i = 0; i < players.size(); i++) {

            // Gets player model for player at current position in the loop
            PlayerModel currentPlayer = players.get(i);

            // Creates new row in the table
            row = new TableRow(this);
            lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            // Saves information about player in an array
            String[] stats = {Integer.toString(currentPlayer.getNumber()), currentPlayer.getFirst_name() + " " + currentPlayer.getLast_name(), currentPlayer.getYear()};

            // Loops through array to display player information
            for (int i2 = 0; i2 < stats.length; i2++) {
                tv[i2] = new TextView(this);
                tv[i2].setPadding(0,5,50,0);
                tv[i2].setText(stats[i2]);
                tv[i2].setTextSize(24);
                row.addView(tv[i2]);
            }
            layout.addView(row);
        }
        // closes database
        db.close();
    }

    /* Function to display table headings */
    public void displayHead() {
        for (int i = 0; i < headers.length; i++) {
            tvHead[i] = new TextView(this);
            tvHead[i].setPadding(0,5,50,0);
            tvHead[i].setText(headers[i]);
            tvHead[i].setTextSize(24);
            row.addView(tvHead[i]);
        }
        layout.addView(row);
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
