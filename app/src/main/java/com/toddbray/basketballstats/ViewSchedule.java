package com.toddbray.basketballstats;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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
    private DbDataSource dataSource;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_players);

        db.open();
        List<GameModel> games = db.getAllGames();

        final int num = games.size();
        TableLayout layout = (TableLayout) findViewById(R.id.schedule_table);
        for (int i = 0; i < games.size(); i++) {
            GameModel currentGame = games.get(i);
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView tvDate = new TextView(this);
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            tvDate.setText(df.format(currentGame.getGame_date()));
            tvDate.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tvDate.setTextSize(24);
            TextView tvOpp = new TextView(this);
            tvOpp.setText(currentGame.getOpp_name());
            tvOpp.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 4 ) );
            tvOpp.setTextSize(24);
            TextView tvLoc = new TextView(this);
            tvLoc.setText(currentGame.getLocation());
            tvLoc.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tvLoc.setTextSize(24);
            row.addView(tvDate);
            row.addView(tvOpp);
            row.addView(tvLoc);
            layout.addView(row);

        }


        db.close();
    }




}
