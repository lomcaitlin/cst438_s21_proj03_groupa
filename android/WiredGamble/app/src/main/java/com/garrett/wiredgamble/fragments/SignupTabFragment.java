package com.garrett.wiredgamble.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.garrett.wiredgamble.MainActivity;
import com.garrett.wiredgamble.R;
import com.parse.ParseUser;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupTabFragment extends Fragment {

    EditText etUsername;
    EditText etPassword;
    EditText etPasswordConf;
    Button btnSignup;

    public SignupTabFragment () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SignupTabFragment.
     */
    public static SignupTabFragment newInstance () {
        SignupTabFragment fragment = new SignupTabFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_tab, container, false);
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        etPasswordConf = view.findViewById(R.id.etPasswordConf);
        btnSignup = view.findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (!password.equals(etPasswordConf.getText().toString().trim())) {
                Toast.makeText(getContext(), "Passwords don't match!", Toast.LENGTH_SHORT).show();
                return;
            }

            ParseUser user = new ParseUser();

            user.setUsername(username);
            user.setPassword(password);
            // default balance
            user.put("balance",  100.00);
            // default admin status
            user.put("isAdmin", false);

            user.signUpInBackground(e -> {
                if (e == null) {
                    Toast.makeText(getContext(), "Account Created", Toast.LENGTH_LONG).show();

                    // log in the new user
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                    // close the login activity (to remove the back arrow)
                    Objects.requireNonNull(getActivity()).finish();
                } else {
                    ParseUser.logOut();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}