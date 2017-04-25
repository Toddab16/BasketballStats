package com.toddbray.basketballstats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DbDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new DbDataSource(getApplicationContext());
        Button newPlayer = (Button) findViewById(R.id.add_player_button);
        newPlayer.setOnClickListener(this);

        Button viewRoster = (Button) findViewById(R.id.roster_view_button);
        viewRoster.setOnClickListener(this);

        Button newGame = (Button) findViewById(R.id.add_game_button);
        newGame.setOnClickListener(this);

        Button viewSchedule = (Button) findViewById(R.id.schedule_view_button);
        viewSchedule.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        dataSource.open();

        List<GameModel> games = dataSource.getAllGames();
        List<PlayerModel> players = dataSource.getAllPlayers();
        List<StatModel> stats = dataSource.getAllStats();
        List<SeasonModel> seasons = dataSource.getAllSeasons();

        Date dateTest = new Date();

        GameModel game = new GameModel();
        game.setBoys_jv(dateTest);
        game.setBoys_v(dateTest);
        game.setGirls_jv(dateTest);
        game.setGirls_v(dateTest);
        game.setGame_date(dateTest);
        game.setLocation("Clarksville");
        game.setOpp_name("Broncos");
        game.setVenue("Arena");

        game = dataSource.createGame(game);

        /*
        PlayerModel player = new PlayerModel();
        player.setFirst_name("Brad");
        player.setLast_name("Jordan");
        player.setNumber(18);
        player.setYear("Sophomore");

        player = dataSource.createPlayer(player);
        */

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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_player_button:
                Intent intent = new Intent (this, NewPlayer.class);
                startActivity(intent);
                break;
            case R.id.add_game_button:
                intent = new Intent (this, NewGame.class);
                startActivity(intent);
                break;
            case R.id.roster_view_button:
                intent = new Intent (this, ViewPlayer.class);
                startActivity(intent);
                break;
            case R.id.schedule_view_button:
                intent = new Intent (this, ViewSchedule.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
