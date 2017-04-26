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
        setContentView(R.layout.view_schedule);

        db.open();
        List<GameModel> games = db.getAllGames();

        final int num = games.size();
        TableLayout layout = (TableLayout) findViewById(R.id.schedule_table);

        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);

        TextView tvDate = new TextView(this);
        tvDate.setText("DATE");
        tvDate.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
        tvDate.setTextSize(24);

        TextView tvOpp = new TextView(this);
        tvOpp.setText("OPPONENT");
        tvOpp.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 4 ) );
        tvOpp.setTextSize(24);

        TextView tvLoc = new TextView(this);
        tvLoc.setText("LOCATION");
        tvLoc.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
        tvLoc.setTextSize(24);

        TextView tvgjv = new TextView(this);
        tvgjv.setText("G JV");
        tvgjv.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
        tvgjv.setTextSize(24);

        TextView tvbjv = new TextView(this);
        tvbjv.setText("B JV");
        tvbjv.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
        tvbjv.setTextSize(24);

        TextView tvgv = new TextView(this);
        tvgv.setText("GIRLS");
        tvgv.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
        tvgv.setTextSize(24);

        TextView tvbv = new TextView(this);
        tvbv.setText("BOYS");
        tvbv.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
        tvbv.setTextSize(24);

        row.addView(tvDate);
        row.addView(tvOpp);
        row.addView(tvLoc);
        row.addView(tvgjv);
        row.addView(tvbjv);
        row.addView(tvgv);
        row.addView(tvbv);
        layout.addView(row);



        for (int i = 0; i < games.size(); i++) {
            GameModel currentGame = games.get(i);
            row = new TableRow(this);
            lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            tvDate = new TextView(this);
            DateFormat df = new SimpleDateFormat("MM/dd");
            tvDate.setText(df.format(currentGame.getGame_date()));
            tvDate.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tvDate.setTextSize(24);
            tvOpp = new TextView(this);
            tvOpp.setText(currentGame.getOpp_name());
            tvOpp.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 4 ) );
            tvOpp.setTextSize(24);
            tvLoc = new TextView(this);
            tvLoc.setText(currentGame.getLocation());
            tvLoc.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tvLoc.setTextSize(24);

            tvgjv = new TextView(this);
            df = new SimpleDateFormat("h:mm");
            tvgjv.setText(df.format(currentGame.getGirls_jv()));
            tvgjv.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tvgjv.setTextSize(24);

            tvbjv = new TextView(this);
            tvbjv.setText(df.format(currentGame.getBoys_jv()));
            tvbjv.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tvbjv.setTextSize(24);

            tvgv = new TextView(this);
            tvgv.setText(df.format(currentGame.getGirls_v()));
            tvgv.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tvgv.setTextSize(24);

            tvbv = new TextView(this);
            tvbv.setText(df.format(currentGame.getBoys_v()));
            tvbv.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            tvbv.setTextSize(24);

            row.addView(tvDate);
            row.addView(tvOpp);
            row.addView(tvLoc);
            row.addView(tvgjv);
            row.addView(tvbjv);
            row.addView(tvgv);
            row.addView(tvbv);
            layout.addView(row);

        }


        db.close();
    }




}
