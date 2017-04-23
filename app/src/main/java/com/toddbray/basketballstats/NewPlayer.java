package com.toddbray.basketballstats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Todd Desktop on 4/22/2017.
 */

public class NewPlayer extends AppCompatActivity {

    DbDataSource db = new DbDataSource(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_player);


        Button saveButton = (Button) findViewById(R.id.player_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText et = (EditText)findViewById(R.id.lname_editText);
                String lname = et.getText().toString();
                et = (EditText)findViewById(R.id.fname_editText);
                String fname = et.getText().toString();
                RadioGroup rgroup = (RadioGroup) findViewById(R.id.player_class_group);
                int gradeId = rgroup.getCheckedRadioButtonId();
                RadioButton gradeButton = (RadioButton) findViewById(gradeId);
                String grade = gradeButton.getText().toString();
                et = (EditText)findViewById(R.id.number_editText);
                int number = Integer.parseInt(et.getText().toString());

                PlayerModel newPlayer = new PlayerModel();
                newPlayer.setFirst_name(fname);
                newPlayer.setLast_name(lname);
                newPlayer.setNumber(number);
                newPlayer.setYear(grade);

                db.createPlayer(newPlayer);

            }
        });




    }


}
