package com.toddbray.basketballstats;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.List;

import static android.R.id.input;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DbDataSource dataSource;
    private MyDbDataSource myDataSource;

    private final int GET_INTERNET = 5150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new DbDataSource(getApplicationContext());
        myDataSource = new MyDbDataSource();

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

        Button viewGameStats = (Button) findViewById(R.id.game_stats_button);
        viewGameStats.setOnClickListener(this);

        Button viewSeasonStats = (Button) findViewById(R.id.season_stats_button);
        viewSeasonStats.setOnClickListener(this);

        Button viewPlayerStats = (Button) findViewById(R.id.player_stats_button);
        viewPlayerStats.setOnClickListener(this);
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

        // checkPermission();

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
            case R.id.view_roster_button:
                intent = new Intent (this, ViewPlayer.class);
                startActivity(intent);
                break;
            case R.id.view_schedule_button:
                intent = new Intent (this, ViewSchedule.class);
                startActivity(intent);
                break;
            case R.id.start_game_button:
                gamesDialog(0);
                break;
            case R.id.game_stats_button:
                gamesDialog(1);
                break;
            case R.id.season_stats_button:
                intent = new Intent(getApplicationContext(), ViewStats.class);
                intent.putExtra("MODE", 2);
                startActivity(intent);
                break;
            case R.id.player_stats_button:
                playersDialog();
                break;
            default:
                break;
        }
    }

    // Ask user for permission to save write to external disk
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case GET_INTERNET:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    try {
                        //String s = myDataSource;
                    }
                    catch(Exception e) {

                    }
                }
                break;

            default:
                break;
        }
    }

    // Verify if permission to save has been allowed (Needed for API 23+)
    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, GET_INTERNET);
        } else {
            try {
                //String s = myDataSource;
                //Toast.makeText(getApplicationContext(), s,
                //        Toast.LENGTH_LONG).show();
            } catch (Exception e) {

            }
        }
    }

    public void gamesDialog (int mode) {
        final int mode_select = mode;
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
                if (mode_select == 0) {
                    Intent intent = new Intent(getApplicationContext(), NewStats.class);
                    intent.putExtra("GAME_ID", games.get(item).getGame_id());
                    intent.putExtra("OPP", games.get(item).getOpp_name());
                    startActivity(intent);
                }
                if (mode_select == 1) {
                    Intent intent = new Intent(getApplicationContext(), ViewStats.class);
                    intent.putExtra("GAME_ID", games.get(item).getGame_id());
                    intent.putExtra("OPP", games.get(item).getOpp_name());
                    intent.putExtra("MODE", 1);
                    startActivity(intent);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void playersDialog () {
        final List<PlayerModel> players = dataSource.getAllPlayers();
        CharSequence[] players_list = new CharSequence[players.size()];
        for (int i = 0; i < players.size(); i++) {
            players_list[i] = Integer.toString(players.get(i).getNumber()) + "     " + players.get(i).getLast_name() + ", " + players.get(i).getFirst_name();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Player");

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setItems(players_list, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                    Intent intent = new Intent(getApplicationContext(), ViewStats.class);
                    intent.putExtra("PLAYER_ID", players.get(item).getPlayer_id());
                    intent.putExtra("MODE", 3);
                    startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
