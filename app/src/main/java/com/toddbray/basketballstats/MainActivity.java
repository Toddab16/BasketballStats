package com.toddbray.basketballstats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DbDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_stats);

        dataSource = new DbDataSource(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();

        dataSource.open();

        List<Comment> comments = dataSource.getAllComments();

        /*
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                R.layout.list_textview, comments);
        setListAdapter(adapter);
        */
    }

    @Override
    protected void onStop() {
        super.onStop();
        dataSource.close();
    }
}
