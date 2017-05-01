package com.toddbray.basketballstats;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewGame extends AppCompatActivity implements View.OnFocusChangeListener{

    DbDataSource db = new DbDataSource(this);
    Date date;
    Date [] times = new Date[4];
    // This value only works on physical devices
    //private String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    private String m_androidId = "Todd Bray Marshmallow";
    int season_id = 2017;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_game);
        EditText gjv = (EditText) findViewById(R.id.gjv_time_editText);
        EditText bjv = (EditText) findViewById(R.id.bjv_time_editText);
        EditText gv = (EditText) findViewById(R.id.gv_time_editText);
        EditText bv = (EditText) findViewById(R.id.bv_time_editText);
        gjv.setOnFocusChangeListener(this);
        bjv.setOnFocusChangeListener(this);
        gv.setOnFocusChangeListener(this);
        bv.setOnFocusChangeListener(this);
        Button save = (Button)findViewById(R.id.game_save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
                date = getDateFromDatePicker(dp);
                EditText opp = (EditText) findViewById(R.id.opponent_editText);
                String opponent = opp.getText().toString();
                RadioGroup rgroup = (RadioGroup) findViewById(R.id.location_group);
                int venue = rgroup.getCheckedRadioButtonId();
                RadioButton venueButton = (RadioButton) findViewById(venue);
                String venue_name = venueButton.getText().toString();
                EditText location = (EditText) findViewById(R.id.location_editText);
                String loc = location.getText().toString();

                GameModel newGame = new GameModel(m_androidId);
                newGame.setSeason_id(season_id);
                newGame.setGame_date(date);
                newGame.setGirls_jv(times[0]);
                newGame.setBoys_jv(times[1]);
                newGame.setGirls_v(times[2]);
                newGame.setBoys_v(times[3]);
                newGame.setOpp_name(opponent);
                newGame.setVenue(venue_name);
                newGame.setLocation(loc);

                db.open();
                db.createGame(newGame);
                db.close();

                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onFocusChange(View view, boolean b) {

        if (b){
        switch(view.getId()) {
            case R.id.gjv_time_editText:
                getTimePicker(view, 0);
                break;

            case R.id.bjv_time_editText:
                getTimePicker(view, 1);
                break;

            case R.id.gv_time_editText:
                getTimePicker(view, 2);
                break;

            case R.id.bv_time_editText:
                getTimePicker(view, 3);
                break;

            default:
                break;

        }
        }
    }

    public void getTimePicker (View v, int i) {
        final EditText et = (EditText) findViewById(v.getId());
        final int time_code = i;
        int hour = 16;
        int minute = 00;
        TimePickerDialog timePicker = new TimePickerDialog(this, 2, new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                DateFormat df = new SimpleDateFormat("h:mm");
                times[time_code] = new Date();
                times[time_code].setMinutes(selectedMinute);
                times[time_code].setHours(selectedHour);
                et.setText(df.format(times[time_code]));
             }
        }, hour, minute, false);
        timePicker.setTitle("Select Time");
        timePicker.show();

    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
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
