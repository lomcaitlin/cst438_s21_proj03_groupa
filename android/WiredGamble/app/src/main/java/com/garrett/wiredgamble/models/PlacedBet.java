/*
 * author: Garrett
 * date: 4/14/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("PlacedBet")
public class PlacedBet extends ParseObject {
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_PAYOUT = "payout";
    public static final String KEY_BET = "bet";
    public static final String KEY_STATUS = "status";
    public static final String KEY_USER = "user";

    public Payout getPayout() {
        return (Payout) getParseObject(KEY_PAYOUT);
    }

    public void setPayout(Payout payout) {
        put(KEY_PAYOUT, payout);
    }

    public double getBet() {
        return getDouble(KEY_BET);
    }

    public void setBet(double bet) {
        put(KEY_BET, bet);
    }

    public int getStatus() {
        return getInt(KEY_STATUS);
    }

    public void setStatus(PlacedBetStatus status) {
        put(KEY_STATUS, status.getStatus());
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }
}
