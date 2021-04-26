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

import com.garrett.wiredgamble.AdminMainActivity;
import com.garrett.wiredgamble.MainActivity;
import com.garrett.wiredgamble.R;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginTabFragment extends Fragment {

    EditText etUsername;
    EditText etPassword;
    EditText etPasswordConf;
    Button button_login;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginTabFragment () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginTabFragment newInstance (String param1, String param2) {
        LoginTabFragment fragment = new LoginTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_tab, container, false);
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        button_login = view.findViewById(R.id.button_login);

        button_login.setOnClickListener(v -> {
            ParseUser.logInInBackground(etUsername.getText().toString().trim(), etPassword.getText().toString().trim(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        if (user.getUsername().equals("admin")) {
                            // log in the user
                            Intent intent = new Intent(getActivity(), AdminMainActivity.class);
                            startActivity(intent);
                            // close the login activity (to remove the back arrow)
                            Objects.requireNonNull(getActivity()).finish();
                        }
                        else {
                            // log in the user

                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            // close the login activity (to remove the back arrow)
                            Objects.requireNonNull(getActivity()).finish();
                        }
                    } else {
                        Toast.makeText(getContext(), "User Not Found", Toast.LENGTH_LONG).show();
                    }
                }
            });
        });
    }
}