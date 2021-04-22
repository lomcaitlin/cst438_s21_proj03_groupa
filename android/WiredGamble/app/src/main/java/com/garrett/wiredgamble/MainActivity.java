package com.garrett.wiredgamble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.garrett.wiredgamble.adapters.GameAdapter;
import com.garrett.wiredgamble.models.Game;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameAdapter.OnGameClickListener {
    private RecyclerView mRvGames;
    private RecyclerView.LayoutManager mManager;
    private GameAdapter mAdapter;

    private List<Game> mGames;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGames = new ArrayList<>();
        Game g = new Game();
        g.setName("foo");
        mGames.add(g);
        Game g2  = new Game();
        g2.setName("bar");
        mGames.add(g2);
        Game g3 = new Game();
        g3.setName("baz");
        mGames.add(g3);
        Game g4 = new Game();
        g4.setName("fdhjsnk");
        mGames.add(g4);

        buildRecyclerView();
    }

    /**
     * Wire up the RecyclerView in this activity.
     */
    private void buildRecyclerView () {
        mRvGames = findViewById(R.id.rvGames);
        mRvGames.setHasFixedSize(true);
        mManager = new LinearLayoutManager(this);
        mAdapter = new GameAdapter(mGames, this);

        mRvGames.setLayoutManager(mManager);
        mRvGames.setAdapter(mAdapter);
    }

    @Override
    public void onGameClick (int position) {
        Toast.makeText(this, "Clicked: " + position, Toast.LENGTH_SHORT).show();
    }
}