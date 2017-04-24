package com.toddbray.basketballstats;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Todd Desktop on 4/24/2017.
 */

public class GameList extends ListActivity {
    private DbDataSource db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DbDataSource(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        db.open();

        List<GameModel> games = db.getAllGames();
        ArrayAdapter<GameModel> adapter = new ArrayAdapter<GameModel>(getApplicationContext(),
                R.layout.game_list, R.id.game_list_textView, games);
        setListAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ArrayAdapter<GameModel> adapter = (ArrayAdapter<GameModel>) getListAdapter();
        GameModel game = adapter.getItem(position);

        Intent intent = new Intent (this, NewStats.class);
        intent.putExtra("GAME_ID",game.getGame_id());
        startActivity(intent);
    }
}
