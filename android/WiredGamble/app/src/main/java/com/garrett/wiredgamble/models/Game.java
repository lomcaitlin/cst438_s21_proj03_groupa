/*
 * author: Garrett
 * date: 4/14/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Game")
public class Game extends ParseObject {
    private static List<Game> sGames;

    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_NAME = "name";
    public static final String KEY_SEED = "seed";
    public static final String KEY_IMAGE = "image";

    private List<Payout> mPayouts;

    public Game() {
        super();
        mPayouts = new ArrayList<>();
    }

    public String getName() {
        return getString(KEY_NAME);
    }

    public void setName(String name) {
        put(KEY_NAME, name);
    }

    public String getSeed() {
        return getString(KEY_SEED);
    }

    public void setSeed(String seed) {
        put(KEY_SEED, seed);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public List<Payout> getPayouts() {
        return mPayouts;
    }

    public void addPayout(Payout payout) {
        mPayouts.add(payout);
    }

    public void setPayouts (List<Payout> payouts) {
        mPayouts = payouts;
    }

    public static List<Game> getGames() {
        if (sGames == null) {
            sGames = new ArrayList<>();
        }

        return sGames;
    }

    public static void addGame(Game game) {
        sGames.add(game);
    }

    public static void setGames(List<Game> games) {
        sGames = games;
    }

    /**
     * Find the index of game in games if it exists.
     * @param games a List of Game to search
     * @param game  the Game to search for
     * @return      if found, returns the index game was found
     *              otherwise, returns -1
     */
    public static int findGame(List<Game> games, Game game) {
        String id = game.getObjectId();

        for (int i = 0; i < games.size(); ++i) {
            if (games.get(i).getObjectId().equals(id)) {
                return i;
            }
        }

        return -1;
    }
}
