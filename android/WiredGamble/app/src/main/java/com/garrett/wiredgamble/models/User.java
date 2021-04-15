/*
 * author: Garrett
 * date: 4/14/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static List<User> sUsers;

    private String mUsername;
    private String mPassword;
    private float mBalance;
    private boolean mIsAdmin;
    private List<PlacedBet> mHistory;

    public User (String username, String password, float balance, boolean isAdmin, List<PlacedBet> history) {
        mUsername = username;
        mPassword = password;
        mBalance = balance;
        mIsAdmin = isAdmin;
        mHistory = history;
    }

    public String getUsername () {
        return mUsername;
    }

    public void setUsername (String username) {
        mUsername = username;
    }

    public String getPassword () {
        return mPassword;
    }

    public void setPassword (String password) {
        mPassword = password;
    }

    public float getBalance () {
        return mBalance;
    }

    public void setBalance (float balance) {
        mBalance = balance;
    }

    public boolean isAdmin () {
        return mIsAdmin;
    }

    public void setAdmin (boolean admin) {
        mIsAdmin = admin;
    }

    public List<PlacedBet> getHistory () {
        return mHistory;
    }

    public void setHistory (List<PlacedBet> history) {
        mHistory = history;
    }

    public static List<User> getUsers () {
        if (sUsers == null) {
            sUsers = new ArrayList<>();
        }

        return sUsers;
    }

    public static void addUser (User user) {
        sUsers.add(user);
    }
}
