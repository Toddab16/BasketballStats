package com.toddbray.basketballstats;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewStats extends AppCompatActivity {

    // This value only works on physical devices
    //private String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    private String m_androidId = "Todd Bray Marshmallow";

    // Creates array for stat buttons
    private Button buttonList[][] = new Button[5][12];

    // Creates array for player buttons
    private Button lineupList[] = new Button[5];

    // Arrays of ids for stats buttons
    private static final int[][] buttons = {
            {R.id.fg2m_button1, R.id.fg2a_button1, R.id.fg3m_button1, R.id.fg3a_button1, R.id.ftm_button1, R.id.fta_button1,
            R.id.oreb_button1, R.id.dreb_button1, R.id.ast_button1, R.id.stl_button1, R.id.to_button1, R.id.chg_button1 },
            {R.id.fg2m_button2, R.id.fg2a_button2, R.id.fg3m_button2, R.id.fg3a_button2, R.id.ftm_button2, R.id.fta_button2,
            R.id.oreb_button2, R.id.dreb_button2, R.id.ast_button2, R.id.stl_button2, R.id.to_button2, R.id.chg_button2},
            {R.id.fg2m_button3, R.id.fg2a_button3, R.id.fg3m_button3, R.id.fg3a_button3, R.id.ftm_button3, R.id.fta_button3,
            R.id.oreb_button3, R.id.dreb_button3, R.id.ast_button3, R.id.stl_button3, R.id.to_button3, R.id.chg_button3},
            {R.id.fg2m_button4, R.id.fg2a_button4, R.id.fg3m_button4, R.id.fg3a_button4, R.id.ftm_button4, R.id.fta_button4,
            R.id.oreb_button4, R.id.dreb_button4, R.id.ast_button4, R.id.stl_button4, R.id.to_button4, R.id.chg_button4},
            {R.id.fg2m_button5, R.id.fg2a_button5, R.id.fg3m_button5, R.id.fg3a_button5, R.id.ftm_button5, R.id.fta_button5,
            R.id.oreb_button5, R.id.dreb_button5, R.id.ast_button5, R.id.stl_button5, R.id.to_button5, R.id.chg_button5}
    };

    // Arrays of ids for stats textViews
    private static final int[][] stats_tv = {
            {R.id.fg2m_textView1, R.id.fg2a_textView1, R.id.fg3m_textView1, R.id.fg3a_textView1, R.id.ftm_textView1, R.id.fta_textView1,
            R.id.oreb_textView1, R.id.dreb_textView1, R.id.ast_textView1, R.id.stl_textView1, R.id.to_textView1, R.id.chg_textView1},
            {R.id.fg2m_textView2, R.id.fg2a_textView2, R.id.fg3m_textView2, R.id.fg3a_textView2, R.id.ftm_textView2, R.id.fta_textView2,
            R.id.oreb_textView2, R.id.dreb_textView2, R.id.ast_textView2, R.id.stl_textView2, R.id.to_textView2, R.id.chg_textView2},
            {R.id.fg2m_textView3, R.id.fg2a_textView3, R.id.fg3m_textView3, R.id.fg3a_textView3, R.id.ftm_textView3, R.id.fta_textView3,
            R.id.oreb_textView3, R.id.dreb_textView3, R.id.ast_textView3, R.id.stl_textView3, R.id.to_textView3, R.id.chg_textView3},
            {R.id.fg2m_textView4, R.id.fg2a_textView4, R.id.fg3m_textView4, R.id.fg3a_textView4, R.id.ftm_textView4, R.id.fta_textView4,
            R.id.oreb_textView4, R.id.dreb_textView4, R.id.ast_textView4, R.id.stl_textView4, R.id.to_textView4, R.id.chg_textView4},
            {R.id.fg2m_textView5, R.id.fg2a_textView5, R.id.fg3m_textView5, R.id.fg3a_textView5, R.id.ftm_textView5, R.id.fta_textView5,
            R.id.oreb_textView5, R.id.dreb_textView5, R.id.ast_textView5, R.id.stl_textView5, R.id.to_textView5, R.id.chg_textView5}
    };

    // Arrays of ids for player buttons
    private static final int[] lineup_ids = {
      R.id.player1_button, R.id.player2_button, R.id.player3_button, R.id.player4_button, R.id.player5_button
    };


    DbDataSource db = new DbDataSource(this);
    int game_id; // Game id
    int mode = 0; // Mode used for addition or subtraction of stats
    String opp_name; // Opponents name displayed at top of screen
    List<Integer> lineup = new ArrayList<>(); // List containing the player_ids of players currently in the lineup
    int rosterSize; // Size of roster
    List<PlayerModel> players; // List of all players on the team
    HashMap<Integer, StatModel> game_stats = new HashMap<Integer, StatModel>(); // Hashmap to identify statmodel to use, player_id used as the key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_stats);

        // Gets extras from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            game_id = extras.getInt(MainActivity.GAME_ID);
            opp_name = extras.getString(MainActivity.OPP);
        }

        // Opens Database
        db.open();

        // Sets textview at the top of the screen to identify the current game opponent
        TextView tv = (TextView)findViewById(R.id.opponent_textView);
        tv.setText(opp_name);

        // Loads all players from the database and saves roster size
        players = db.getAllPlayers();
        rosterSize = players.size();

        // Initializes lineup array
        for (int i = 0; i < 5; i++) {
            lineup.add(0);
        }

        // Gets player stats from database
        // If stats already exist for the player, loads them into the game
        // If no stats exist for the player, initializes a new stat model
        for (int i = 0; i < rosterSize; i++) {
            Boolean b = db.checkStat(players.get(i).getPlayer_id(),game_id, m_androidId);
            if (b) {
                game_stats.put(players.get(i).getPlayer_id(), db.getStat(players.get(i).getPlayer_id(),game_id, m_androidId));
            } else {
                StatModel stat = new StatModel(m_androidId);
                stat.setPlayer_id(players.get(i).getPlayer_id());
                game_stats.put(players.get(i).getPlayer_id(), stat);

            }
        }

        // Sets listener for each of the stats buttons
        // If mode is add - Adds one to stat
        // If mode is subtract - Subtracts one from stat
        for (int i = 0; i < buttons.length; i++) {
            for (int i2 = 0; i2 < buttons[i].length; i2++) {
                buttonList[i][i2] = ((Button) findViewById(buttons[i][i2]));
                final int it = i;
                final int it2 = i2;
                buttonList[i][i2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (lineup.get(it) != 0) {
                            TextView tv = (TextView) findViewById(stats_tv[it][it2]);
                            int stat = Integer.parseInt(tv.getText().toString());
                            if (mode == 0) {
                                stat++;
                            } else {
                                stat--;
                            }
                            tv.setText(Integer.toString(stat));

                            // Method for 2 point, 3 point and free throw made
                            // If the made button is presses, also adds one to attempted stat
                            if (it2 == 0 || it2 == 2 || it2 == 4) {
                                int n = it2 + 1;
                                tv = (TextView) findViewById(stats_tv[it][n]);
                                stat = Integer.parseInt(tv.getText().toString());
                                if (mode == 0) {
                                    stat++;
                                } else {
                                    stat--;
                                }
                                tv.setText(Integer.toString(stat));

                            }
                        }
                    }
                });
            }
        }

        // Click event when player jersey is pressed
        for (int i = 0; i < 5; i++) {
            lineupList[i] = ((Button) findViewById(lineup_ids[i]));
            final int it = i;
            lineupList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subDialog(it);
                }
            });
        }

        // Switches between add and subtract mode
        Switch sw = (Switch) findViewById(R.id.add_delete_switch);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    mode = 1;
                }
                else {
                    mode = 0;
                }
            }
        });

        // Save button click
        // Subs out all players (So stats will be saved to database)
        // Writes data to the DB
        Button b = (Button)findViewById(R.id.save_stats_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < 5; i++) {
                    if (lineup.get(i) != 0) {
                        subOut(lineup.get(i), i);
                    }
                }

                for (int i = 0; i < rosterSize; i++) {
                    Boolean b = db.checkStat(players.get(i).getPlayer_id(),game_id, m_androidId);
                    if (game_stats.get(players.get(i).getPlayer_id()).getGame_id() == game_id && !b) {
                        db.createStat(game_stats.get(players.get(i).getPlayer_id()));
                    }
                    if (game_stats.get(players.get(i).getPlayer_id()).getGame_id() == game_id && b) {
                        db.createStat(game_stats.get(players.get(i).getPlayer_id()));

                    }
                }
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }


        });

    }


    // Dialog for subbing in a new player
    // Creates a list of all players currently not in the lineup
    // Selected player is sent to sub in function along with lineup position
    public void subDialog (int pos) {
        final int lineup_pos = pos;
        final List<Integer> bench_ids = new ArrayList<Integer>();
        for(int i = 0; i < rosterSize; i++) {
            bench_ids.add(players.get(i).getPlayer_id());
        }
        bench_ids.removeAll(lineup);
        final List<String> bench = new ArrayList<String>();
        for (int i = 0; i < bench_ids.size(); i++) {
            PlayerModel pm = db.getPlayer(bench_ids.get(i), m_androidId);
            bench.add(Integer.toString(pm.getNumber()) + " " + pm.getFirst_name() + " " + pm.getLast_name());
        }
        CharSequence[] benchChar = bench.toArray(new CharSequence[bench.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Player to Sub In");
        builder.setItems(benchChar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (lineup.get(lineup_pos) != 0) {
                    subOut(lineup.get(lineup_pos), lineup_pos);
                }
                subIn(bench_ids.get(item), lineup_pos);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    // Fuction takes the player ID of the player being subbed in and the lineup position
    // Loads stats for player being subbed in
    public void subIn (int player_id, int pos) {

        if (game_stats.get(player_id).getTwo_pointer() >= 0) {
            TextView tv;

            tv = (TextView) findViewById(stats_tv[pos][0]);
            tv.setText(Integer.toString(game_stats.get(player_id).getTwo_pointer_made()));
            tv = (TextView) findViewById(stats_tv[pos][1]);
            tv.setText(Integer.toString(game_stats.get(player_id).getTwo_pointer()));
            tv = (TextView) findViewById(stats_tv[pos][2]);
            tv.setText(Integer.toString(game_stats.get(player_id).getThree_pointer_made()));
            tv = (TextView) findViewById(stats_tv[pos][3]);
            tv.setText(Integer.toString(game_stats.get(player_id).getThree_pointer()));
            tv = (TextView) findViewById(stats_tv[pos][4]);
            tv.setText(Integer.toString(game_stats.get(player_id).getFree_throw_made()));
            tv = (TextView) findViewById(stats_tv[pos][5]);
            tv.setText(Integer.toString(game_stats.get(player_id).getFree_throw()));
            tv = (TextView) findViewById(stats_tv[pos][6]);
            tv.setText(Integer.toString(game_stats.get(player_id).getO_rebound()));
            tv = (TextView) findViewById(stats_tv[pos][7]);
            tv.setText(Integer.toString(game_stats.get(player_id).getD_rebound()));
            tv = (TextView) findViewById(stats_tv[pos][8]);
            tv.setText(Integer.toString(game_stats.get(player_id).getAssist()));
            tv = (TextView) findViewById(stats_tv[pos][9]);
            tv.setText(Integer.toString(game_stats.get(player_id).getSteal()));
            tv = (TextView) findViewById(stats_tv[pos][10]);
            tv.setText(Integer.toString(game_stats.get(player_id).getTurnover()));
            tv = (TextView) findViewById(stats_tv[pos][11]);
            tv.setText(Integer.toString(game_stats.get(player_id).getCharge()));
        }

        // Adds game id to player being subbed in
        // Determines if a player played in the game or not
        game_stats.get(player_id).setGame_id(game_id);

        // Adds player to lineup
        lineup.set(pos, player_id);

        // Sets jersey number on screen
        lineupList[pos] = ((Button) findViewById(lineup_ids[pos]));
        PlayerModel pm = db.getPlayer(player_id, m_androidId);
        lineupList[pos].setText(Integer.toString(pm.getNumber()));
    }

    // Player being subbed out of the lineup
    // Gets current game stats from stat TextViews and saves them to stat model
    public void subOut (int playerOut, int pos) {
        TextView tv = (TextView)findViewById(stats_tv[pos][0]);
        game_stats.get(playerOut).setTwo_pointer_made(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][1]);
        game_stats.get(playerOut).setTwo_pointer(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][2]);
        game_stats.get(playerOut).setThree_pointer_made(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][3]);
        game_stats.get(playerOut).setThree_pointer(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][4]);
        game_stats.get(playerOut).setFree_throw_made(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][5]);
        game_stats.get(playerOut).setFree_throw(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][6]);
        game_stats.get(playerOut).setO_rebound(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][7]);
        game_stats.get(playerOut).setD_rebound(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][8]);
        game_stats.get(playerOut).setAssist(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][9]);
        game_stats.get(playerOut).setSteal(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][10]);
        game_stats.get(playerOut).setTurnover(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][11]);
        game_stats.get(playerOut).setCharge(Integer.parseInt(tv.getText().toString()));
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
