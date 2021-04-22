package com.garrett.wiredgamble;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity {

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project02.model.AdminActivity;
import com.example.project02.model.User;

    public class HomeActivity extends AppCompatActivity {

        protected static final String ACTIVE_USER_KEY = "activeUser";
        private TextView tvUser;

        private User user;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
            Button adminButton = findViewById(R.id.adminButton);
            Button toSceneBtn = findViewById(R.id.startBtn);
            user = (User) getIntent().getSerializableExtra(ACTIVE_USER_KEY);
//getting user
            tvUser = findViewById(R.id.tvUser);

            toSceneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(HomeActivity.this,StoryScreen.class);
                    i.putExtra(ACTIVE_USER_KEY, user);
                    startActivity(i);    }
            });

//user goes to home activity and is welcomed
            if (user != null) {
                tvUser.setText("WELCOME " + user.getUserName());
            }


            if(user.getEmail().equals("admin2")){
                adminButton.setVisibility(View.VISIBLE);
            }

            adminButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HomeActivity.this, "Hello Admin", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(HomeActivity.this, AdminActivity.class);
                    i.putExtra(ACTIVE_USER_KEY, user);
                    startActivity(i);

                }

            });
        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.user_menu,menu);
            return true;
        }


        public void logoutUser(){
            Intent titleScreen = new Intent(this, MainActivity.class);
            titleScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(titleScreen);
        }
        @Override
        public boolean onOptionsItemSelected( MenuItem item){
            switch(item.getItemId()){
                case R.id.logoutUser:
                    logoutUser();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }

}
