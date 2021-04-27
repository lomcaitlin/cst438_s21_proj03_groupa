package com.garrett.wiredgamble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.garrett.wiredgamble.adapters.AdminGameAdapter;
import com.garrett.wiredgamble.adapters.AdminUserAdapter;
import com.garrett.wiredgamble.models.Game;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminActivity extends AppCompatActivity implements AdminUserAdapter.OnAdminUserListener, AdminGameAdapter.OnAdminGameListener {

    private RecyclerView userRV;
    private RecyclerView gameRV;
    private List<ParseUser> users = new ArrayList<>();
    private List<Game> games = new ArrayList<>();
    private AdminUserAdapter userAdapter;
    private AdminGameAdapter gameAdapter;
    private TextView admin_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        userRV = findViewById(R.id.users_rv);
        gameRV = findViewById(R.id.games_rv);
        admin_name = findViewById(R.id.admin_name);
        admin_name.setText(ParseUser.getCurrentUser().getUsername() + "!");
        initGameRV();
        insertGames();
        initUserRV();
        insertUsers();
    }

    private void insertGames() {
        ParseQuery<Game> query = ParseQuery.getQuery(Game.class);
        query.findInBackground((games, e) -> {
            if (e != null) {
                Log.e("AdminActivity", "Issues loading games", e);
                return;
            }
            for (Game game : games) {
                Log.d("Games: ", game.getName());
            }
            this.games.addAll(games);
            gameAdapter.notifyDataSetChanged();
        });
    }

    private void initGameRV() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        gameRV.setLayoutManager(linearLayoutManager);
        gameAdapter = new AdminGameAdapter(games, this, this);
        gameRV.setAdapter(gameAdapter);
    }

    private void insertUsers() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground((users, e) -> {
           if (e != null) {
               Log.e("AdminActivity", "Issues loading users", e);
               return;
           }
           this.users.addAll(users);
            userAdapter.notifyDataSetChanged();
        });
    }

    private void initUserRV() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        userRV.setLayoutManager(linearLayoutManager);
        userAdapter = new AdminUserAdapter(users, this);
        userRV.setAdapter(userAdapter);
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

    @Override
    public void onAdminUserClick(int position) {
        Toast.makeText(this, "Clicked user at: " + position, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "Clicked: " + position, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, users.get(position).getObjectId(), Toast.LENGTH_SHORT).show();
        deleteUserAlert(users.get(position));
    }

    private void deleteUserAlert(ParseUser user) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete User")
                .setMessage("Are you sure you want to delete user " + user.getUsername())
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, String> params = new HashMap<>();
                        params.put("user", user.getObjectId());
//                     ParseCloud.callFunctionInBackground("deleteUser", params, (result, e) -> {
//                         if (e != null) {
//                             Log.e("AdminActivity", "Issues loading users", e);
//                         }
//                         Log.d("User delete", "Successfully delete user");
//                         adapter.notifyDataSetChanged();
//                     });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void deleteUser() {
    }

    @Override
    public void onAdminGameClick(int position) {
        Toast.makeText(this, "Clicked game at: " + position, Toast.LENGTH_SHORT).show();
    }
}