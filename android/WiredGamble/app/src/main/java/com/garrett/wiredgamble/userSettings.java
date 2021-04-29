package com.garrett.wiredgamble;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseQuery;
import com.parse.ParseUser;

public class userSettings extends AppCompatActivity {
    EditText newUser;
    EditText newPw;
    EditText pwConf;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        newUser = findViewById(R.id.newUsername);
        newPw =  findViewById(R.id.newPassword);
        pwConf = findViewById(R.id.passwordConf);
        update = findViewById(R.id.updateBtn);

        update.setOnClickListener(view -> {
            String username = newUser.getText().toString().trim();
            String password = newPw.getText().toString().trim();
            String passwordConf = pwConf.getText().toString().trim();
            if(currentUser != null) {
                    if(password.equals(passwordConf)) {
                        currentUser.put("username", username);
                        currentUser.put("password", password);
                    } else {
                        Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
                    }
                currentUser.saveInBackground(e ->{
                    if(e==null) {
//
                            Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
//
                    }
//                if(query.whereEqualTo("username", username)!= null) {
//                    Toast.makeText(this, "Username Taken", Toast.LENGTH_SHORT).show();
//                }else {
//                    currentUser.put("username", username);
//                    Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
//                }
//                if (!password.equals(passwordConf)) {
//                    Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
//                    return;
//                }else {
//                    currentUser.put("password", password);
//                }
                });
            }

        });




    }
}