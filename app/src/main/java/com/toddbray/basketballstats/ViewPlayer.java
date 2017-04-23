package com.toddbray.basketballstats;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Todd Desktop on 4/23/2017.
 */

public class ViewPlayer extends AppCompatActivity {
    DbDataSource db = new DbDataSource(this);
    private DbDataSource dataSource;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_players);

        db.open();
        List<PlayerModel> players = db.getAllPlayers();

        final int num = players.size();
        TableLayout layout = (TableLayout) findViewById(R.id.roster_table);
        for (int i = 0; i < players.size(); i++) {
            PlayerModel currentPlayer = players.get(i);
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView tvNum = new TextView(this);
            tvNum.setText(Integer.toString(currentPlayer.getNumber()));
            tvNum.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tvNum.setTextSize(24);
            TextView tvName = new TextView(this);
            tvName.setText(currentPlayer.getFirst_name() + " " + currentPlayer.getLast_name());
            tvName.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 4 ) );
            tvName.setTextSize(24);
            TextView tvGrade = new TextView(this);
            tvGrade.setText(currentPlayer.getYear());
            tvGrade.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tvGrade.setTextSize(24);
            row.addView(tvNum);
            row.addView(tvName);
            row.addView(tvGrade);
            layout.addView(row);

        }


        db.close();
    }




}
