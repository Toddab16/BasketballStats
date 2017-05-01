package com.toddbray.basketballstats;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Todd Desktop on 4/23/2017.
 */

public class ViewSchedule extends AppCompatActivity {
    DbDataSource db = new DbDataSource(this);
    TableLayout layout;
    TableRow row;
    TableRow.LayoutParams lp;
    TextView[] tv = new TextView[7];
    TextView[] tvHead = new TextView[7];
    String[] headers = {"DATE", "OPPONENT", "LOCATION", "JVG", "JVB", "GIRLS", "BOYS"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_schedule);
        layout = (TableLayout) findViewById(R.id.schedule_table);
        row = new TableRow(this);
        lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        // Displays headings for table
        displayHead();

        // Opens database
        db.open();

        // Queries database for games and saves them in List
        List<GameModel> games = db.getAllGames();

        // Loops through list of games
        for (int i = 0; i < games.size(); i++) {

            // Gets game model for game at current position in the loop
            GameModel currentGame = games.get(i);

            // Creates new row in the table
            row = new TableRow(this);
            lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            // Formats to properly display date and time objects
            DateFormat df = new SimpleDateFormat("MM/dd");
            DateFormat tf = new SimpleDateFormat("h:mm");

            // Saves information about game in an array
            String[] stats = {df.format(currentGame.getGame_date()), currentGame.getOpp_name(), currentGame.getLocation(), tf.format(currentGame.getGirls_jv()), tf.format(currentGame.getBoys_jv()), tf.format(currentGame.getGirls_v()), tf.format(currentGame.getBoys_v())};

            // Loops through array to display game information and adds row to table
            for (int i2 = 0; i2 < stats.length; i2++) {
                tv[i2] = new TextView(this);
                tv[i2].setPadding(0,5,0,0);
                tv[i2].setText(stats[i2]);
                tv[i2].setTextSize(24);
                if (i2 == 1 || i2 == 2) {
                    tv[i2].setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 2 ) );
                } else {
                    tv[i2].setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
                }
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
            tvHead[i].setText(headers[i]);
            tvHead[i].setTextSize(24);
            if (i == 1 || i == 2) {
                tvHead[i].setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 2 ) );
            } else {
                tvHead[i].setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            }
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
