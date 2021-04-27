package com.garrett.wiredgamble;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class userSettings extends AppCompatActivity {
    EditText newUser;
    EditText newPw;
    EditText pwConf;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        newUser = findViewById(R.id.newUsername);
        newPw =  findViewById(R.id.newPassword);
        pwConf = findViewById(R.id.passwordConf);

    }
}