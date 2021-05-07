package com.garrett.wiredgamble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.garrett.wiredgamble.fragments.LeaderboardFragment;
import com.garrett.wiredgamble.fragments.RouletteFragment;
import com.garrett.wiredgamble.models.Payout;
import com.garrett.wiredgamble.models.internal.PlayableGame;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private PlayableGame mPlayableGame;

    private TextView tvGameNameP, tvGameNamePB;
    private ImageButton ibInfo;
    private BottomNavigationView bnvGame;

    @SuppressLint ("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mPlayableGame = (PlayableGame) Parcels.unwrap(getIntent().getParcelableExtra(PlayableGame.class.getSimpleName()));
        List<Payout> payouts = getIntent().getParcelableArrayListExtra("payouts");
        mPlayableGame.getGame().setPayouts(payouts);

        tvGameNameP = (TextView) findViewById(R.id.tvGameNameP);
        tvGameNamePB = (TextView) findViewById(R.id.tvGameNamePB);
        tvGameNameP.setText(mPlayableGame.getGame().getName());
        tvGameNamePB.setText(mPlayableGame.getGame().getName());

        ibInfo = (ImageButton) findViewById(R.id.ibInfo);
        ibInfo.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(mPlayableGame.getGame().getName() + " info")
                    .setMessage(mPlayableGame.getGame().getDescription())
                    .setNeutralButton("close", ((dialog, which) -> {}))
                    .show();
        });

        bnvGame = (BottomNavigationView) findViewById(R.id.bnvGame);
        bnvGame.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_leaderboard:
                    fragment = new LeaderboardFragment();
                    break;
                case R.id.action_play:
                default:
                    fragment = mPlayableGame.getFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.flGameFrag, fragment).commit();
            return true;
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.flGameFrag, mPlayableGame.getFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        if (userIsAdmin()) {
            menu.findItem(R.id.menu_admin).setVisible(true);
        }
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

    public PlayableGame getPlayableGame() {
        return mPlayableGame;
    }
}