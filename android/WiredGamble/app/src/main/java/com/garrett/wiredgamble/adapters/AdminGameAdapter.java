package com.garrett.wiredgamble.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.garrett.wiredgamble.R;
import com.garrett.wiredgamble.models.Game;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

public class AdminGameAdapter extends RecyclerView.Adapter<AdminGameAdapter.ViewHolder> {

    private List<Game> games;
    private Context context;
    private OnAdminGameListener onAdminGameListener;

    public AdminGameAdapter(List<Game> games, Context context, OnAdminGameListener onAdminGameListener) {
        this.games = games;
        this.context = context;
        this.onAdminGameListener = onAdminGameListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_game_item, parent, false);
        return new ViewHolder(view, onAdminGameListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(games.get(position).getName());
        ParseFile img = games.get(position).getImage();
        if (img != null) {
            Glide.with(context).load(img.getUrl()).into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView image;
        OnAdminGameListener onAdminGameListener;

        public ViewHolder(@NonNull View itemView, OnAdminGameListener onAdminGameListener) {
            super(itemView);
            name = itemView.findViewById(R.id.admin_game_name);
            image = itemView.findViewById(R.id.admin_game_img);
            this.onAdminGameListener = onAdminGameListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAdminGameListener.onAdminGameClick(getAdapterPosition());
        }
    }

    public interface OnAdminGameListener {
        void onAdminGameClick(int position);
    }
}
