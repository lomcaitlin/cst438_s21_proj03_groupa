package com.garrett.wiredgamble.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garrett.wiredgamble.GameActivity;
import com.garrett.wiredgamble.ParseApplication;
import com.garrett.wiredgamble.R;
import com.garrett.wiredgamble.adapters.LeaderboardAdapter;
import com.garrett.wiredgamble.models.PlacedBet;
import com.garrett.wiredgamble.models.internal.PlayableGame;
import com.garrett.wiredgamble.models.internal.Stats;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardFragment extends Fragment {
    private PlayableGame mPlayableGame;
    private List<Stats> mStats;

    private LeaderboardAdapter mAdapter;
    private RecyclerView rvLeaderboard;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LeaderboardFragment.
     */
    public static LeaderboardFragment newInstance() {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameActivity a = (GameActivity) getActivity();
        assert a != null;
        mPlayableGame = a.getPlayableGame();
        mStats = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buildRecyclerView(view);
        generateStats(mPlayableGame.getGame().getObjectId());
    }

    private void buildRecyclerView(View view) {
        rvLeaderboard = (RecyclerView) view.findViewById(R.id.rvLeaderboard);

        rvLeaderboard.setHasFixedSize(true);
        mAdapter = new LeaderboardAdapter(mStats, getContext());

        rvLeaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLeaderboard.setAdapter(mAdapter);
    }

    private void generateStats(String gameId) {
        ParseQuery<PlacedBet> query = ParseQuery.getQuery(PlacedBet.class);

        query.include(PlacedBet.KEY_USER);
        query.include(PlacedBet.KEY_PAYOUT);
        query.orderByDescending(PlacedBet.KEY_USER);
        query.findInBackground((placedBets, e) -> {
            if (e != null) {
                Log.e("Leaderboard", "Issue getting placed bets", e);
                return;
            }


            HashMap<String, List<PlacedBet>> userBetMap = new HashMap<>();
            for (PlacedBet placedBet: placedBets) {
                if (placedBet.getUser() == null) {
                    continue;
                }

                String username = placedBet.getUser().getUsername();
                if (userBetMap.get(username) == null) {
                    userBetMap.put(username, new ArrayList<>());
                }

                userBetMap.get(username).add(placedBet);
            }

            for (String username: userBetMap.keySet()) {
                Stats stats = new Stats(userBetMap.get(username), username, gameId);
                stats.aggregate();

                if (stats.getGamesPlayed() >= 1) {
                    mStats.add(stats);
                }
            }

            Stats.sortDescending(mStats);
            mAdapter.notifyDataSetChanged();
        });
    }
}