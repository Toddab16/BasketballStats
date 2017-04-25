package com.toddbray.basketballstats;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

        Button newGame = (Button) findViewById(R.id.add_game_button);
        newGame.setOnClickListener(this);

        Button startGame = (Button) findViewById(R.id.start_game_button);
        startGame.setOnClickListener(this);

        Button viewRoster = (Button) findViewById(R.id.view_roster_button);
        viewRoster.setOnClickListener(this);

        Button viewSchedule = (Button) findViewById(R.id.view_schedule_button);
        viewSchedule.setOnClickListener(this);
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
            case R.id.view_roster_button:
                intent = new Intent (this, ViewPlayer.class);
                startActivity(intent);
                break;
            case R.id.view_schedule_button:
                intent = new Intent (this, ViewSchedule.class);
                startActivity(intent);
                break;
            case R.id.start_game_button:
                gamesDialog();
                break;

            default:
                break;
        }
    }

    public void gamesDialog () {
        final List<GameModel> games = dataSource.getAllGames();
        CharSequence[] games_list = new CharSequence[games.size()];
        DateFormat df = new SimpleDateFormat("MM/dd");
        for (int i = 0; i < games.size(); i++) {
            games_list[i] = df.format(games.get(i).getGame_date()) + "     " + games.get(i).getOpp_name();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Game");

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setItems(games_list, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Intent intent = new Intent (getApplicationContext(), NewStats.class);
                intent.putExtra("GAME_ID",games.get(item).getGame_id());
                intent.putExtra("OPP", games.get(item).getOpp_name());
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
