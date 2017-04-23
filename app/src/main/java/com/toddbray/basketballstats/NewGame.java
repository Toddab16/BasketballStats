package com.toddbray.basketballstats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Todd Desktop on 4/22/2017.
 */

public class NewGame extends AppCompatActivity {

    DbDataSource db = new DbDataSource(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_game);


        Button saveButton = (Button) findViewById(R.id.game_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText et = (EditText)findViewById(R.id.date_editText);
                String date = et.getText().toString();
                et = (EditText)findViewById(R.id.opponent_editText);
                String opp = et.getText().toString();
                RadioGroup rgroup = (RadioGroup) findViewById(R.id.location_group);
                int loc = rgroup.getCheckedRadioButtonId();
                RadioButton locButton = (RadioButton) findViewById(loc);
                String location = locButton.getText().toString();
                et = (EditText)findViewById(R.id.location_editText);
                String location_name = et.getText().toString();
                et = (EditText)findViewById(R.id.gjv_time_editText);
                String gjv_time = et.getText().toString();
                et = (EditText)findViewById(R.id.bjv_time_editText);
                String bjv_time = et.getText().toString();
                et = (EditText)findViewById(R.id.gvar_time_editText);
                String gv_time = et.getText().toString();
                et = (EditText)findViewById(R.id.bvar_time_editText);
                String bv_time = et.getText().toString();


                Date dateTest = new Date();

                dateTest = db.GetDate(date);

                GameModel newGame = new GameModel();
                newGame.setGame_date(db.GetDate(date));
                newGame.setOpp_name(opp);
                newGame.setLocation(location_name);
                newGame.setVenue(location);
                newGame.setGirls_jv(db.GetDate(gjv_time));
                newGame.setGirls_v(db.GetDate(bjv_time));
                newGame.setBoys_jv(db.GetDate(gv_time));
                newGame.setBoys_v(db.GetDate(bv_time));

                db.open();

                db.createGame(newGame);
                db.close();

            }
        });




    }


}
