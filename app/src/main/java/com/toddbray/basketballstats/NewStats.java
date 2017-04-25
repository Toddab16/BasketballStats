package com.toddbray.basketballstats;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Todd Desktop on 4/22/2017.
 */

public class NewStats extends AppCompatActivity {

    private Button buttonList[][] = new Button[5][12];
    private Button lineupList[] = new Button[5];
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

    private static final int[] lineup_ids = {
      R.id.player1_button, R.id.player2_button, R.id.player3_button, R.id.player4_button, R.id.player5_button
    };

    DbDataSource db = new DbDataSource(this);
    int game_id;
    int mode = 0;
    PlayerModel lineup[];
    StatModel game_stats[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_stats);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            game_id = extras.getInt("GAME_ID");
        }

        db.open();

        List<PlayerModel> players = db.getAllPlayers();
        List<StatModel> player_stats = db.getAllStats();

        lineup = new PlayerModel[5];
        game_stats = new StatModel[players.size()];

        for (int i = 0; i < players.size(); i++) {
            game_stats[i] = new StatModel();
            game_stats[i].setPlayer_id(players.get(i).getPlayer_id());
            game_stats[i].setGame_id(game_id);
        }

        for (int i = 0; i < buttons.length; i++) {
            for (int i2 = 0; i2 < buttons[i].length; i2++) {
                buttonList[i][i2] = ((Button) findViewById(buttons[i][i2]));
                final int it = i;
                final int it2 = i2;
                buttonList[i][i2].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv = (TextView) findViewById(stats_tv[it][it2]);
                        int stat = Integer.parseInt(tv.getText().toString());
                        if (mode == 0) {
                            stat++;
                        } else {
                            stat--;
                        }
                        tv.setText(Integer.toString(stat));
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
                });
            }
        }

        for (int i = 0; i < lineup.length; i++) {
            lineupList[i] = ((Button) findViewById(lineup_ids[i]));
            final int it = i;
            lineupList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    subDialog(it);
                }
            });
        }

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

    }


    public void subDialog (int pos) {
        final int lineup_pos = pos;
        final List<PlayerModel> players = db.getAllPlayers();
        CharSequence[] roster = new CharSequence[players.size()];
        for (int i = 0; i < players.size(); i++) {
            roster[i] = Integer.toString(players.get(i).getNumber()) + " " + players.get(i).getFirst_name() + " " + players.get(i).getLast_name();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Player to Sub In");
        builder.setItems(roster, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (lineup[lineup_pos] != null) {
                    subOut(lineup[lineup_pos], lineup_pos);
                }

                subIn(players.get(item), lineup_pos);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void subIn (PlayerModel player, int pos) {
        int selection = 0;
        for (int i = 0; i < game_stats.length; i++) {
            if (player.getPlayer_id() == game_stats[i].getPlayer_id()) {
                selection = i;
            }
        }
        if (game_stats[selection].getTwo_pointer() >= 0) {
            TextView tv = (TextView) findViewById(stats_tv[pos][0]);
            tv.setText(Integer.toString(game_stats[selection].getTwo_pointer_made()));
            tv = (TextView) findViewById(stats_tv[pos][1]);
            tv.setText(Integer.toString(game_stats[selection].getTwo_pointer()));
            tv = (TextView) findViewById(stats_tv[pos][2]);
            tv.setText(Integer.toString(game_stats[selection].getThree_pointer_made()));
            tv = (TextView) findViewById(stats_tv[pos][3]);
            tv.setText(Integer.toString(game_stats[selection].getThree_pointer()));
            tv = (TextView) findViewById(stats_tv[pos][4]);
            tv.setText(Integer.toString(game_stats[selection].getFree_throw_made()));
            tv = (TextView) findViewById(stats_tv[pos][5]);
            tv.setText(Integer.toString(game_stats[selection].getFree_throw()));
            tv = (TextView) findViewById(stats_tv[pos][6]);
            tv.setText(Integer.toString(game_stats[selection].getO_rebound()));
            tv = (TextView) findViewById(stats_tv[pos][7]);
            tv.setText(Integer.toString(game_stats[selection].getD_rebound()));
            tv = (TextView) findViewById(stats_tv[pos][8]);
            tv.setText(Integer.toString(game_stats[selection].getAssist()));
            tv = (TextView) findViewById(stats_tv[pos][9]);
            tv.setText(Integer.toString(game_stats[selection].getSteal()));
            tv = (TextView) findViewById(stats_tv[pos][10]);
            tv.setText(Integer.toString(game_stats[selection].getTurnover()));
            tv = (TextView) findViewById(stats_tv[pos][11]);
            tv.setText(Integer.toString(game_stats[selection].getCharge()));
        }
        lineup[pos] = player;
        lineupList[pos] = ((Button) findViewById(lineup_ids[pos]));
        lineupList[pos].setText(Integer.toString(player.getNumber()));
    }

    public void subOut (PlayerModel player, int pos) {
        int selection = 0;
        for (int i = 0; i < game_stats.length; i++) {
            if (player.getPlayer_id() == game_stats[i].getPlayer_id()) {
                selection = i;
            }
        }
        TextView tv = (TextView)findViewById(stats_tv[pos][0]);
        game_stats[selection].setTwo_pointer_made(Integer.parseInt(tv.getText().toString()));
        Log.i("Player", player.getFirst_name());
        Log.i("FGM", Integer.toString(game_stats[selection].getTwo_pointer_made()));

        tv = (TextView)findViewById(stats_tv[pos][1]);
        game_stats[selection].setTwo_pointer(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][2]);
        game_stats[selection].setThree_pointer_made(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][3]);
        game_stats[selection].setThree_pointer(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][4]);
        game_stats[selection].setFree_throw_made(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][5]);
        game_stats[selection].setFree_throw(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][6]);
        game_stats[selection].setO_rebound(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][7]);
        game_stats[selection].setD_rebound(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][8]);
        game_stats[selection].setAssist(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][9]);
        game_stats[selection].setSteal(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][10]);
        game_stats[selection].setTurnover(Integer.parseInt(tv.getText().toString()));
        tv = (TextView)findViewById(stats_tv[pos][11]);
        game_stats[selection].setCharge(Integer.parseInt(tv.getText().toString()));

    }


}
