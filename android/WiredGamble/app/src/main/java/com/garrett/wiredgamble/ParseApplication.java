/*
 * author: Garrett
 * date: 4/15/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble;

import com.garrett.wiredgamble.models.Game;
import com.garrett.wiredgamble.models.Payout;
import com.garrett.wiredgamble.models.PlacedBet;
import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("GDKnilVZeYv0fQTXTRp7RZp6DujJ0cd5MAAfAVz4")
                .clientKey("Cf3QhUBtwbRvEVf3A63ApNfltjxLteDh4DqFGNp3")
                .server("https://parseapi.back4app.com")
                .build()
        );

        ParseObject.registerSubclass(Game.class);
        ParseObject.registerSubclass(Payout.class);
        ParseObject.registerSubclass(PlacedBet.class);
    }
}
