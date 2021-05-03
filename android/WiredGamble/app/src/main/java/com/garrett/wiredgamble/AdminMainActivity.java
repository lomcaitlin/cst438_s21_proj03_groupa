package com.garrett.wiredgamble;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class AdminMainActivity extends AppCompatActivity {
    private TextView mMainDisplay;

    private void displayUsers() throws ParseException {
        StringBuilder sb = new StringBuilder();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.findInBackground((objects, e) -> {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                    System.out.println(objects.iterator().next().getUsername());
                    sb.append("User: " + objects.get(i).getUsername() + "\n");
                    }
                } else {
                    System.out.println("No Users Found");
                }
                mMainDisplay.setText(sb.toString());
            });
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main);
        mMainDisplay = findViewById(R.id.users_text_view);
        try {
            displayUsers();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
}
