/*
 * author: Garrett
 * date: 4/20/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.garrett.wiredgamble.R;
import com.garrett.wiredgamble.models.Game;
import com.garrett.wiredgamble.models.Payout;
import com.parse.ParseFile;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private List<Game> mGames;
    private OnGameClickListener mOnGameClickListener;
    private Context mContext;

    public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

        @SuppressLint ("DefaultLocale")
        public void bind(Game game) {
            // set the image drawable and bg
            ParseFile image = game.getImage();
            if (image != null) {
                Glide.with(mContext).load(image.getUrl()).into(ivGameImage);
            } else {
                // default if we fail loading an image
                ivGameImage.setImageResource(R.drawable.ic_launcher_foreground);
            }
            ivGameImage.setBackgroundResource(R.drawable.game_img_bg);

            tvGameName.setText(game.getName());

            // build and set the list of possible payouts
            if (game.getPayouts().size() > 0) {
                StringBuilder payoutStr = new StringBuilder();
                payoutStr.append("Pays: ");

                for (Payout payout : game.getPayouts()) {
                    payoutStr.append(String.format("%.1fx, ", payout.getMultiplier()));
                }

                tvPayouts.setText(payoutStr.substring(0, payoutStr.length() - 2));
            }
        }
    }

    /**
     * Interface allowing items in the recycler view to be clicked
     */
    public interface OnGameClickListener {
        void onGameClick(int position);
    }

    public GameAdapter(List<Game> games, OnGameClickListener listener, Context context) {
        mGames = games;
        mOnGameClickListener = listener;
        mContext = context;
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
