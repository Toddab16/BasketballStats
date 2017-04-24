package com.toddbray.basketballstats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by Todd Desktop on 4/22/2017.
 */

public class NewStats extends AppCompatActivity {

    DbDataSource db = new DbDataSource(this);
    int game_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_stats);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            game_id = extras.getInt("GAME_ID");
        }



    }


}
