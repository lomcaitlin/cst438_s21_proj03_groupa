/*
 * author: Garrett
 * date: 5/4/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.garrett.wiredgamble.R;
import com.garrett.wiredgamble.models.internal.Stats;
import com.parse.ParseUser;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {
    private List<Stats> mStats;
    private Context mContext;

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername,
                        tvWinPercentage,
                        tvTotalCoinsWon,
                        tvLargestWin;
        public ImageView ivUser;
        public ConstraintLayout constraintLayout;

        private static final String WP_FMT_STR = "Win Percentage: %.2f";
        private static final String TCW_FMT_STR = "Total Won: %.2f coins from %d games";
        private static final String LW_FMT_STR = "Largest Win: %.2f coins";

        public LeaderboardViewHolder (@NonNull View itemView) {
            super(itemView);

            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvWinPercentage = (TextView) itemView.findViewById(R.id.tvWinPercentage);
            tvTotalCoinsWon = (TextView) itemView.findViewById(R.id.tvTotalCoinsWon);
            tvLargestWin = (TextView) itemView.findViewById(R.id.tvLargestWin);
            ivUser = (ImageView) itemView.findViewById(R.id.ivUser);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraintLayout);
        }

        @SuppressLint ({"DefaultLocale", "SetTextI18n"})
        public void bind(Stats stats) {
            if (ParseUser.getCurrentUser().getUsername().equals(stats.getUsername())) {
                ivUser.setColorFilter(Color.parseColor("#51CF4A"));
                constraintLayout.setBackgroundResource(R.drawable.leaderboard_users_bg_curr);
            }

            tvUsername.setText(stats.getUsername());
            tvWinPercentage.setText(String.format(WP_FMT_STR, stats.getWinPercent()) + "%");
            tvTotalCoinsWon.setText(String.format(TCW_FMT_STR, stats.getTotalWon(), stats.getGamesPlayed()));
            tvLargestWin.setText(String.format(LW_FMT_STR, stats.getLargestWin()));
        }
    }

    public LeaderboardAdapter (List<Stats> stats, Context context) {
        mStats = stats;
        mContext = context;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull LeaderboardViewHolder holder, int position) {
        Stats stats = mStats.get(position);
        holder.bind(stats);
    }

    @Override
    public int getItemCount () {
        return mStats.size();
    }
}
