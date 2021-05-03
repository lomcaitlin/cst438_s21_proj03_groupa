package com.garrett.wiredgamble.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.garrett.wiredgamble.GameActivity;
import com.garrett.wiredgamble.R;
import com.garrett.wiredgamble.models.Payout;
import com.garrett.wiredgamble.models.PlacedBet;
import com.garrett.wiredgamble.models.internal.PlayableGame;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RouletteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RouletteFragment extends Fragment {
    private static final int PADDING_BORDER_AMOUNT = 10;

    private final HashMap<Character, Payout> mPayoutMap = new HashMap<>();
    private char mSelectedColor;
    private PlayableGame mPlayableGame;

    private ImageView ivGameImageP;
    private CardView cvColor;
    private ImageButton ibColor1, ibColor2, ibColor3;
    private TextView tvColor1, tvColor2, tvColor3;
    private EditText etBet, e;
    private Button btnPlay;


    public RouletteFragment () {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RouletteFragment.
     */
    public static RouletteFragment newInstance () {
        RouletteFragment fragment = new RouletteFragment();
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameActivity a = (GameActivity) getActivity();
        mPlayableGame = a.getPlayableGame();
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roulette, container, false);
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wireUpDisplay(view);
        clear();

        ibColor1.setOnClickListener(v -> {
            ibColor2.setPadding(0, 0, 0, 0);
            ibColor3.setPadding(0, 0, 0, 0);
            ibColor1.setPadding(PADDING_BORDER_AMOUNT,
                                PADDING_BORDER_AMOUNT,
                                PADDING_BORDER_AMOUNT,
                                PADDING_BORDER_AMOUNT);
            mSelectedColor = 'b';
        });
        ibColor2.setOnClickListener(v -> {
            ibColor1.setPadding(0, 0, 0, 0);
            ibColor3.setPadding(0, 0, 0, 0);
            ibColor2.setPadding(PADDING_BORDER_AMOUNT,
                                PADDING_BORDER_AMOUNT,
                                PADDING_BORDER_AMOUNT,
                                PADDING_BORDER_AMOUNT);
            mSelectedColor = 'r';
        });
        ibColor3.setOnClickListener(v -> {
            ibColor1.setPadding(0, 0, 0, 0);
            ibColor2.setPadding(0, 0, 0, 0);
            ibColor3.setPadding(PADDING_BORDER_AMOUNT,
                                PADDING_BORDER_AMOUNT,
                                PADDING_BORDER_AMOUNT,
                                PADDING_BORDER_AMOUNT);
            mSelectedColor = 'g';
        });

        btnPlay.setOnClickListener(v -> {
            String b = etBet.getText().toString();
            double bet = 0.0;

            try {
                bet = Double.parseDouble(b);
            } catch (NumberFormatException e) {
                Log.e("Roulette", "Empty bet", e);
                Toast.makeText(getContext(),
                        "Bet must be a positive amount!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            double balance = mPlayableGame.getUser().getDouble("balance");
            Payout payout = mPayoutMap.get(mSelectedColor);

            if (!validate(bet, balance, payout)) {
                return;
            }

            clear();
            PlacedBet placedBet = mPlayableGame.register(bet,
                                                         balance,
                                                         payout);

            char winner = mPlayableGame.play(placedBet, mPayoutMap, mSelectedColor);
            gameFinished(winner, bet, payout);
        });
    }

    /**
     * Clear the user input.
     */
    private void clear() {
        mSelectedColor = '\0';
        ibColor1.setPadding(0, 0, 0, 0);
        ibColor2.setPadding(0, 0, 0, 0);
        ibColor3.setPadding(0, 0, 0, 0);
        etBet.setText("0.0");
    }

    /**
     * Validate the user input.
     * @param bet       a double of the amount the user wants to bet
     * @param balance   a double of how many coins the user currently has
     * @param payout    a Payout (here a color) the user has bet on
     * @return          a boolean of whether or not the input is valid
     */
    private boolean validate(double bet, double balance, Payout payout) {
        if (bet > balance) {
            Toast.makeText(getContext(),
                    "Cannot bet more than your current balance!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (bet <= 0.0) {
            Toast.makeText(getContext(),
                    "Bet must be a positive amount!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (payout == null) {
            Toast.makeText(getContext(),
                    "You must select a color!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * Display some Toast messages once the game has finished playing.
     * @param winner    a char of which color in the PayoutMap won
     * @param bet       a double of the amount the user bet
     * @param payout    a Payout that the user bet on
     */
    private void gameFinished(char winner, double bet, Payout payout) {
        String msg;
        switch (winner) {
            case 'b':
                msg = "Black won!";
                break;
            case 'r':
                msg = "Red won!";
                break;
            case 'g':
                msg = "Green won!";
                break;
            default:
                msg = "Error spinning the roulette wheel!";
        }

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),
                       winner == mSelectedColor ?
                            String.format("You won %.2f!", bet * payout.getMultiplier()) :
                            String.format("You Lost %.2f", bet),
                       Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("DefaultLocale")
    private void wireUpDisplay(View view) {
        tvColor1 = (TextView) view.findViewById(R.id.tvColor1);
        tvColor2 = (TextView) view.findViewById(R.id.tvColor2);
        tvColor3 = (TextView) view.findViewById(R.id.tvColor3);

        ivGameImageP = (ImageView) view.findViewById(R.id.ivGameImageP);

        List<Payout> payouts = mPlayableGame.getGame().getPayouts();
        mPayoutMap.put('b', payouts.get(0));
        tvColor1.setText(String.format("%.1fx", payouts.get(0).getMultiplier()));
        mPayoutMap.put('r', payouts.get(0));
        tvColor2.setText(String.format("%.1fx", payouts.get(0).getMultiplier()));
        mPayoutMap.put('g', payouts.get(1));
        tvColor3.setText(String.format("%.1fx", payouts.get(1).getMultiplier()));

        ParseFile image = mPlayableGame.getGame().getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivGameImageP);
        } else {
            // default if we fail loading an image
            ivGameImageP.setImageResource(R.drawable.ic_play_game);
        }
        ivGameImageP.setBackgroundResource(R.drawable.login_bg);

        cvColor = view.findViewById(R.id.cvColor1);
        ibColor1 = (ImageButton) cvColor.getChildAt(0);
        cvColor = view.findViewById(R.id.cvColor2);
        ibColor2 = (ImageButton) cvColor.getChildAt(0);
        cvColor = view.findViewById(R.id.cvColor3);
        ibColor3 = (ImageButton) cvColor.getChildAt(0);

        etBet = (EditText) view.findViewById(R.id.etCoins);
        btnPlay = (Button) view.findViewById(R.id.btnPlay);
    }
}