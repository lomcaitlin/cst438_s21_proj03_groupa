package com.garrett.wiredgamble.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.garrett.wiredgamble.R;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {

    private List<ParseUser> users;
    private OnAdminUserListener onAdminUserListener;

    public AdminUserAdapter(List<ParseUser> users, OnAdminUserListener onAdminUserListener) {
        this.users = users;
        this.onAdminUserListener = onAdminUserListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_user_item, parent, false);
        return new ViewHolder(view, onAdminUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(users.get(position).getUsername());
        holder.coins.setText(String.valueOf(users.get(position).get("balance")));
        if ((Boolean)users.get(position).get("isAdmin")) {
            holder.admin.setText("Admin");
        } else {
            holder.admin.setText("Player");
        }
    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name, coins, admin;
        OnAdminUserListener onAdminUserListener;

        public ViewHolder(@NonNull View itemView, OnAdminUserListener onAdminUserListener) {
            super(itemView);
            name = itemView.findViewById(R.id.admin_user_name);
            coins = itemView.findViewById(R.id.admin_user_coins);
            admin = itemView.findViewById(R.id.admin_user_status);
            this.onAdminUserListener = onAdminUserListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAdminUserListener.onAdminUserClick(getAdapterPosition());
        }
    }

    public interface OnAdminUserListener {
        void onAdminUserClick(int position);
    }
}
