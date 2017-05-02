package com.toddbray.basketballstats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class NewPlayer extends AppCompatActivity {

    DbDataSource db = new DbDataSource(this);
    // This value only works on physical devices
    //private String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    private String m_androidId = "Todd Bray Marshmallow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_player);


        Button saveButton = (Button) findViewById(R.id.player_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                boolean isValid;
                int number;

                EditText et = (EditText)findViewById(R.id.lname_editText);
                String lname = et.getText().toString();

                isValid = HelperV.isValidString(lname, getApplicationContext());
                if(!isValid) return;


                et = (EditText)findViewById(R.id.fname_editText);
                String fname = et.getText().toString();

                isValid = HelperV.isValidString(lname, getApplicationContext());
                if(!isValid) return;

                et = (EditText)findViewById(R.id.number_editText);

                isValid = HelperV.isValidString(lname, getApplicationContext());
                if(!isValid) return;
                else number = Integer.parseInt(et.getText().toString());


                RadioGroup rgroup = (RadioGroup) findViewById(R.id.player_class_group);
                int gradeId = rgroup.getCheckedRadioButtonId();
                RadioButton gradeButton = (RadioButton) findViewById(gradeId);
                String grade = gradeButton.getText().toString();


                PlayerModel newPlayer = new PlayerModel(m_androidId);
                newPlayer.setFirst_name(fname);
                newPlayer.setLast_name(lname);
                newPlayer.setNumber(number);
                newPlayer.setYear(grade);

                db.open();
                db.createPlayer(newPlayer);
                db.close();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

            }
        });




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
