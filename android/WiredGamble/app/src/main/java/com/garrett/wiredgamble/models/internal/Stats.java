/*
 * author: Garrett
 * date: 5/4/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.models.internal;

import com.garrett.wiredgamble.models.PlacedBet;
import com.garrett.wiredgamble.models.PlacedBetStatus;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Stats {
    private List<PlacedBet> mPlacedBets;
    private String mUsername;
    private int mGamesPlayed;
    private int mWon;
    private double mWinPercent;
    private double mTotalWon;
    private double mLargestWin;
    private String mGameId;

    public Stats(List<PlacedBet> placedBets, String username, String gameId) {
        mPlacedBets = placedBets;
        mUsername = username;
        mGamesPlayed = 0;
        mWon = 0;
        mWinPercent = 0;
        mTotalWon = 0;
        mLargestWin = 0;
        mGameId = gameId;
    }

    public List<PlacedBet> getPlacedBets() {
        return mPlacedBets;
    }

    public void setPlacedBets(List<PlacedBet> placedBets) {
        mPlacedBets = placedBets;
    }

    public String getUsername () {
        return mUsername;
    }

    public void setUsername (String username) {
        mUsername = username;
    }

    public int getGamesPlayed() {
        return mGamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        mGamesPlayed = gamesPlayed;
    }

    public int getWon() {
        return mWon;
    }

    public void setWon(int won) {
        mWon = won;
    }

    public double getLargestWin() {
        return mLargestWin;
    }

    public void setLargestWin(double largestWin) {
        mLargestWin = largestWin;
    }

    public double getTotalWon() {
        return mTotalWon;
    }

    public void setTotalWon(double totalWon) {
        mTotalWon = totalWon;
    }

    public String getGameId() {
        return mGameId;
    }

    public void setGameId(String gameId) {
        mGameId = gameId;
    }

    public double getWinPercent() {
        return mWinPercent;
    }

    public void setWinPercent(double winPercent) {
        mWinPercent = winPercent;
    }

    public void aggregate() {
        List<PlacedBet> placedBets = new ArrayList<>();
        for (PlacedBet placedBet: mPlacedBets) {
            if (placedBet.getPayout().getGame().getObjectId().equals(mGameId)) {
                placedBets.add(placedBet);
                mGamesPlayed++;

                if (placedBet.getStatus() == PlacedBetStatus.COMPLETED_WON.getStatus()) {
                    mWon++;

                    double coins = placedBet.getBet() * placedBet.getPayout().getMultiplier();
                    mTotalWon += coins;
                    mLargestWin = Math.max(coins, mLargestWin);
                }
            }
        }

        mWinPercent = ((double) mWon / (double) mGamesPlayed) * 100;
        mPlacedBets = placedBets;
    }

    public static void sortDescending(List<Stats> stats) {
        int i, j;

        for (i = 1; i < stats.size(); i++) {
            Stats curr = stats.get(i);
            j = i;

            while((j > 0) && (stats.get(j - 1).getWinPercent() < curr.getWinPercent())) {
                stats.set(j, stats.get(j - 1));
                --j;
            }

            stats.set(j, curr);
        }
    }
}
