/*
 * author: Garrett
 * date: 4/14/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Game")
public class Game extends ParseObject {
    private static List<Game> sGames;

    public static final String KEY_OBJECT_ID = "objectId";
    public static final String KEY_NAME = "name";
    public static final String KEY_SEED = "seed";

    public String getObjectId() {
        return getString(KEY_OBJECT_ID);
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
