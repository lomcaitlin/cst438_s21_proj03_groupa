/*
 * author: Garrett
 * date: 4/14/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.models;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static List<Game> sGames;

    private int mId;
    private String mName;
    private String mSeed;
    private List<Payout> mPayouts;

    public Game (int id, String name, String seed, List<Payout> payouts) {
        mId = id;
        mName = name;
        mSeed = seed;
        mPayouts = payouts;
    }

    public int getId () {
        return mId;
    }

    public void setId (int id) {
        mId = id;
    }

    public String getName () {
        return mName;
    }

    public void setName (String name) {
        mName = name;
    }

    public String getSeed () {
        return mSeed;
    }

    public void setSeed (String seed) {
        mSeed = seed;
    }

    public List<Payout> getPayouts () {
        return mPayouts;
    }

    public void setPayouts (List<Payout> payouts) {
        mPayouts = payouts;
    }

    public static List<Game> getGames () {
        if (sGames == null) {
            sGames = new ArrayList<>();
        }

        return sGames;
    }

    public static void addGame (Game game) {
        sGames.add(game);
    }
}
