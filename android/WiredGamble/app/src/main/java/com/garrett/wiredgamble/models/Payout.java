/*
 * author: Garrett
 * date: 4/14/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.models;

public class Payout {
    private int mId;
    private float mMultiplier;
    private float mOdds;

    public Payout (int id, float multiplier, float odds) {
        mId = id;
        mMultiplier = multiplier;
        mOdds = odds;
    }

    public int getId () {
        return mId;
    }

    public void setId (int id) {
        mId = id;
    }

    public float getMultiplier () {
        return mMultiplier;
    }

    public void setMultiplier (float multiplier) {
        mMultiplier = multiplier;
    }

    public float getOdds () {
        return mOdds;
    }

    public void setOdds (float odds) {
        mOdds = odds;
    }
}
