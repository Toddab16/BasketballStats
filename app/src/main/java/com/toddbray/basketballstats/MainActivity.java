package com.toddbray.basketballstats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DbDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_stats);

        dataSource = new DbDataSource(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();

        dataSource.open();

        List<GameModel> games = dataSource.getAllGames();
        List<PlayerModel> players = dataSource.getAllPlayers();
        List<StatModel> stats = dataSource.getAllStats();

        /*
        ArrayAdapter<GameModel> adapter = new ArrayAdapter<GameModel>(this,
                R.layout.textview_nameofGameView, comments);
        setListAdapter(adapter);
        */

        /*
        ArrayAdapter<PlayerModel> adapter = new ArrayAdapter<PlayerModel>(this,
                R.layout.textview_nameofPlayerView, comments);
        setListAdapter(adapter);
        */

        /*
        ArrayAdapter<StatModel> adapter = new ArrayAdapter<StatModel>(this,
                R.layout.textview_nameofStatView, comments);
        setListAdapter(adapter);
        */
    }

    @Override
    protected void onStop() {
        super.onStop();
        dataSource.close();
    }
}
