/*
 * author: Garrett
 * date: 4/26/2021
 * project: Wired Gamble
 * description: Encapsulates an instance of a game, user, and rng.
 */
package com.garrett.wiredgamble.models.internal;

import androidx.fragment.app.Fragment;

import com.garrett.wiredgamble.models.Game;
import com.garrett.wiredgamble.models.Payout;
import com.garrett.wiredgamble.models.PlacedBet;
import com.parse.ParseUser;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Random;

public abstract class PlayableGame {
    protected Game mGame;
    protected ParseUser mUser;
    protected Random mRandom;
    protected long mSeedNum;

    /**
     * Empty constructor required for Parceler.
     */
    public PlayableGame() {

    }

    public PlayableGame(Game game, ParseUser user) {
        mGame = game;
        mUser = user;
        mSeedNum = seedRoll();
        mRandom = new Random();
        mRandom.setSeed(mSeedNum);
    }

    public Game getGame() {
        return mGame;
    }

    public ParseUser getUser() {
        return mUser;
    }

    /**
     * Override this method to return the fragment for the game's layout.
     * @return  a fragment for the game
     */
    public abstract Fragment getFragment();

    /**
     * Override this function to register the mUser's bet on the current mGame as PlacedBet.
     * This function should not validate input, and it should store the PlacedBet object
     * in the database before returning it. This function should also stage the user's
     * balance by removing the amount bet.
     * @param bet       a double of the amount of coins bet by the user
     * @param balance   a double of the user's current balance
     * @param payout    a Payout of the outcome the user bet on
     * @return          a PlacedBet encapsulating the current iteration of the game
     */
    public abstract PlacedBet register(double bet, double balance, Payout payout);

    /**
     * Override this function to provide the logic for playing mGame. Here the the PlacedBet
     * should be updated with the outcome of the game, if the user won they should be given
     * the proper amount of coins, and the winning outcome should be returned.
     * @param placedBet a PlacedBet generated from a call to register()
     * @param payoutMap a HashMap of Characters to Payouts with the characters being something
     *                  to easily identify what was the outcome is. Each possible outcome
     *                  should be added with a unique char even if they share a Payout. This
     *                  should be defined in each game's respective fragment
     * @param selected  a char representing whichever outcome was bet on. This must be in the
     *                  PayoutMap
     * @return          a char representing which char in the PayoutMap won
     */
    public abstract char play(PlacedBet placedBet, HashMap<Character, Payout> payoutMap, char selected);

    /**
     * Create the seed for the rng for this instance. Should be called in the
     * constructor, and if needed can be called from the object's fragment.
     * @return      a long for seeding mRandom
     */
    protected final long seedRoll() {
        Long time =  System.currentTimeMillis();
        String hexSeed = String.format("%x", new BigInteger(1, mGame.getSeed().getBytes()));
        Long seed = Long.parseLong(hexSeed, 16);

        return time + seed;
    }

    /**
     * Create an instance of a child of PlayableGame from a game and user.
     * @param game  the game (requires a derived class)
     * @param user  the current user
     * @return      a new instance of a PlayableGame if one exists for that game,
     *              otherwise null
     */
    public static PlayableGame fromGame(Game game, ParseUser user) {
        switch (game.getName().toLowerCase()) {
            case "roulette":
                return new Roulette(game, user);

            default:
                return null;
        }
    }
}
