/*
 * author: Garrett
 * date: 4/20/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.garrett.wiredgamble.R;
import com.garrett.wiredgamble.models.Game;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private List<Game> mGames;
    private OnGameClickListener mOnGameClickListener;

    public static class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivGameImage;
        public TextView tvGameName;
        public TextView tvPayouts;

        public OnGameClickListener mGameClickListener;

        public GameViewHolder (@NonNull View itemView, OnGameClickListener listener) {
            super(itemView);

            ivGameImage = (ImageView) itemView.findViewById(R.id.ivGameImage);
            tvGameName = (TextView) itemView.findViewById(R.id.tvGameName);
            tvPayouts = (TextView) itemView.findViewById(R.id.tvPayouts);

            mGameClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick (View v) {
            mGameClickListener.onGameClick(getAdapterPosition());
        }

        public void bind(Game game) {
            ivGameImage.setImageResource(R.drawable.ic_launcher_foreground);
            ivGameImage.setBackgroundResource(R.drawable.ic_launcher_background);
            tvGameName.setText(game.getName());
            tvPayouts.setText("Pays: 2x, 14x");
        }
    }

    public interface OnGameClickListener {
        void onGameClick(int position);
    }

    public GameAdapter(List<Game> games, OnGameClickListener listener) {
        mGames = games;
        mOnGameClickListener = listener;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view, mOnGameClickListener);
    }

    @Override
    public void onBindViewHolder (@NonNull GameViewHolder holder, int position) {
        Game game = mGames.get(position);
        holder.bind(game);
    }

    @Override
    public int getItemCount () {
        return mGames.size();
    }
}
