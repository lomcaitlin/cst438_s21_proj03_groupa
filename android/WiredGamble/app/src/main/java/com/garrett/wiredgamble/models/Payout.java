/*
 * author: Garrett
 * date: 4/14/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Payout")
public class Payout extends ParseObject {
    public static final String KEY_OBJECT_ID = "objectId";
    public static final String KEY_GAME = "game";
    public static final String KEY_MULTIPLIER = "multiplier";
    public static final String KEY_ODDS = "odds";

    public String getObjectId() {
        return getString(KEY_OBJECT_ID);
    }

    public Game getGame() {
        return (Game) getParseObject(KEY_GAME);
    }

    public void setGame(Game game) {
        put(KEY_GAME, game);
    }

    public double getMultiplier() {
        return getDouble(KEY_MULTIPLIER);
    }

    public void setMultiplier(double multiplier) {
        put(KEY_MULTIPLIER, multiplier);
    }

    public double getOdds() {
        return getDouble(KEY_ODDS);
    }

    public void setOdds(double odds) {
        put(KEY_ODDS, odds);
    }
}
