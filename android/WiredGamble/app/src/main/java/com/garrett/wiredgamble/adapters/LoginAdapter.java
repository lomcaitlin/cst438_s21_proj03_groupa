/*
 * author: Garrett
 * date: 4/19/2021
 * project: Wired Gamble
 * description:
 */
package com.garrett.wiredgamble.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.garrett.wiredgamble.fragments.LoginTabFragment;
import com.garrett.wiredgamble.fragments.SignupTabFragment;

public class LoginAdapter extends FragmentStateAdapter {
    private Context mContext;
    int mTotalTabs;

    public LoginAdapter (@NonNull FragmentManager fragmentManager,
                         @NonNull Lifecycle lifecycle, Context context, int totalTabs) {
        super(fragmentManager, lifecycle);
        mContext = context;
        mTotalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment (int position) {
        switch (position) {
            case 0:
                return new LoginTabFragment();
            case 1:
                return new SignupTabFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount () {
        return mTotalTabs;
    }
}
