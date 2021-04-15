/*
 * author: Garrett
 * date: 4/14/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.models;

public class PlacedBet {
    private Payout mPayout;
    private PlacedBetStatus mStatus;
    private float mBet;

    public PlacedBet (Payout payout, PlacedBetStatus status, float bet) {
        mPayout = payout;
        mStatus = status;
        mBet = bet;
    }

    public Payout getPayout () {
        return mPayout;
    }

    public void setPayout (Payout payout) {
        mPayout = payout;
    }

    public PlacedBetStatus getStatus () {
        return mStatus;
    }

    public void setStatus (PlacedBetStatus status) {
        mStatus = status;
    }

    public float getBet () {
        return mBet;
    }

    public void setBet (float bet) {
        mBet = bet;
    }
}
