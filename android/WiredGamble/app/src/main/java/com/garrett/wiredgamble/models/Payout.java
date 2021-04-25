/*
 * author: Garrett
 * date: 4/14/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Payout")
public class Payout extends ParseObject {
    public static final String KEY_GAME = "game";
    public static final String KEY_MULTIPLIER = "multiplier";
    public static final String KEY_ODDS = "odds";

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

    /**
     * Takes a List of Payouts and unwraps it into the corresponding
     * Game(s) coalescing payouts into a List for each Game.
     * @param payouts a List of Payout to unwrap
     * @return        a List of Game wit their corresponding payouts
     */
    public static List<Game> unwrapGames(List<Payout> payouts) {
        List<Game> games = new ArrayList<>();
        int i = -1;

        for (Payout payout: payouts) {
            Game game = payout.getGame();

            if ((i = Game.findGame(games, game)) > 0) {
                game = games.get(i);
                game.addPayout(payout);
                games.set(i, game);
            } else {
                game.addPayout(payout);
                games.add(game);
            }
        }

        return games;
    }
}
