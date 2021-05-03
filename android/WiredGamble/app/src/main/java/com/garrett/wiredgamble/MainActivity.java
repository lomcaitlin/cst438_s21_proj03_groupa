package com.garrett.wiredgamble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.util.Log;
import android.widget.Toast;

import com.garrett.wiredgamble.adapters.GameAdapter;
import com.garrett.wiredgamble.models.Game;
import com.garrett.wiredgamble.models.Payout;
import com.garrett.wiredgamble.models.internal.PlayableGame;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        buildRecyclerView();
        queryPayouts();
    }

    private void queryPayouts() {
        ParseQuery<Payout> query = ParseQuery.getQuery(Payout.class);

        query.include(Payout.KEY_GAME);
        query.setLimit(20);
        query.addDescendingOrder(Payout.KEY_GAME);

        query.findInBackground((payouts, e) -> {
            if (e != null) {
                Log.e("MainActivity", "Issues loading Payouts", e);
                return;
            }

            mGames.addAll(Payout.unwrapGames(payouts));
            mAdapter.notifyDataSetChanged();
        });
    }

    /**
     * Wire up the RecyclerView in this activity.
     */
    private void buildRecyclerView () {
        mRvGames = findViewById(R.id.rvGames);
        mRvGames.setHasFixedSize(true);
        mManager = new LinearLayoutManager(this);
        mAdapter = new GameAdapter(mGames, this, this);

        mRvGames.setLayoutManager(mManager);
        mRvGames.setAdapter(mAdapter);
    }

    @Override
    public void onGameClick (int position) {
        Game game = mGames.get(position);
        ParseUser user = ParseUser.getCurrentUser();

        PlayableGame playableGame = PlayableGame.fromGame(game, user);
        Context ctx = getApplicationContext();
        if (playableGame == null) {
            Toast.makeText(ctx, "Error Starting Game!", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(ctx, GameActivity.class);
        intent.putExtra(PlayableGame.class.getSimpleName(), Parcels.wrap(playableGame));
        intent.putParcelableArrayListExtra("payouts", (ArrayList<? extends Parcelable>) game.getPayouts());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        if (userIsAdmin()) {
            menu.findItem(R.id.menu_admin).setVisible(true);
        }
        getSupportActionBar().setTitle(ParseUser.getCurrentUser().getUsername() + " : " + ParseUser.getCurrentUser().get("balance").toString() + "coins");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.menu_admin:
                startActivity(new Intent(this, AdminActivity.class));
                return true;
            case R.id.logout_button:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                // close the login activity (to remove the back arrow)
                this.finish();
                ParseUser.logOut();
                return true;
                /*
            case R.id.edit_profile_button:
                @Breanna -> uncomment this and change the below line of code to the activity that you create for the profile
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                // close the login activity (to remove the back arrow)
                this.finish();
                return true;
                */
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean userIsAdmin() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null && currentUser.getBoolean("isAdmin")) {
            return true;
        }
        return false;
    }
}