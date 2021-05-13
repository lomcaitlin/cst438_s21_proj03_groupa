package com.garrett.wiredgamble.models.internal;

import androidx.fragment.app.Fragment;

import com.garrett.wiredgamble.fragments.RouletteFragment;
import com.garrett.wiredgamble.models.Game;
import com.garrett.wiredgamble.models.Payout;
import com.garrett.wiredgamble.models.PlacedBet;
import com.garrett.wiredgamble.models.PlacedBetStatus;
import com.parse.ParseUser;

import org.parceler.Parcel;

@Parcel
public class CoinFlip extends PlayableGame{
    private static final Fragment sFragment = new RouletteFragment();

    public CoinFlip(){

    }
    public CoinFlip(Game game, ParseUser user){
        super(game, user);
    }
    @Override
    public Fragment getsFragment(){
        return sFragment;
    }
    @Override
    public PlacedBet register(double bet, double balance, Payout payout) {
        PlacedBet placedBet = new PlacedBet();

        mUser.put("balance", balance - bet);
        mUser.saveInBackground();

        placedBet.setBet(bet);
        placedBet.setUser(mUser);
        placedBet.setPayout(payout);
        placedBet.setStatus(PlacedBetStatus.ACTIVE);
        placedBet.saveInBackground();

        return placedBet;
    }



}
