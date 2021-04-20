package com.garrett.wiredgamble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.garrett.wiredgamble.adapters.LoginAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager2 mViewPager;

    private LoginAdapter mAdapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hide the action bar for more space at the top
        Objects.requireNonNull(getSupportActionBar()).hide();
        setupTabs();
    }

    private void setupTabs() {
        mTabLayout = (TabLayout) findViewById(R.id.login_tl);
        mViewPager = (ViewPager2) findViewById(R.id.login_vp);

        mTabLayout.addTab(mTabLayout.newTab().setText("Login"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Signup"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mAdapter = new LoginAdapter(getSupportFragmentManager(),
                getLifecycle(), this, mTabLayout.getTabCount());

        mViewPager.setAdapter(mAdapter);

        // change fragments on tab click
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // change fragments on screen swipe
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTabLayout.selectTab(mTabLayout.getTabAt(position));
            }
        });
    }
}