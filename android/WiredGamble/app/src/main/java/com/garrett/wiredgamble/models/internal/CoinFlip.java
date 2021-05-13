package com.garrett.wiredgamble.models.internal;

import androidx.fragment.app.Fragment;

import com.garrett.wiredgamble.fragments.RouletteFragment;
import com.garrett.wiredgamble.models.Game;
import com.garrett.wiredgamble.models.Payout;
import com.garrett.wiredgamble.models.PlacedBet;
import com.garrett.wiredgamble.models.PlacedBetStatus;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.HashMap;

@Parcel
public class CoinFlip extends PlayableGame{
    private static final Fragment sFragment = new RouletteFragment();

    public CoinFlip(){

    }
    public CoinFlip(Game game, ParseUser user){
        super(game, user);
    }
    @Override
    public Fragment getFragment(){
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
    @Override
    public char play(PlacedBet placedBet, HashMap<Character, Payout> payoutMap, char selected) {
        double outcome = mRandom.nextDouble();
        double bound = 0.0;
        char winner = 'a';

        for (Character c: payoutMap.keySet()) {
            bound += payoutMap.get(c).getOdds();
            if (outcome <= bound) {
                winner = c;
                break;
            }
        }

        if (winner == selected) {
            ParseUser user = placedBet.getUser();
            double balance = user.getDouble("balance");
            user.put("balance",
                    balance + placedBet.getBet() * placedBet.getPayout().getMultiplier());
            user.saveInBackground();

            placedBet.setStatus(PlacedBetStatus.COMPLETED_WON);
        } else {
            placedBet.setStatus(PlacedBetStatus.COMPLETED_LOST);
        }
        placedBet.saveInBackground();

        return winner;
    }


}
