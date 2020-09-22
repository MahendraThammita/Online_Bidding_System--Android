package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TabedAuctions extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabedLay;

    private fragmentMyWins myWinsFrag;
    private  MyBidsFragment myBidsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabed_auctions);


        viewPager = findViewById(R.id.pager1);
        tabedLay = findViewById(R.id.tablay1);

        myWinsFrag = new fragmentMyWins();
        myBidsFrag = new MyBidsFragment();

        tabedLay.setupWithViewPager(viewPager);
        ViewPagerAdapter view_pagera_dapter = new ViewPagerAdapter(getSupportFragmentManager() , 0);
        view_pagera_dapter.AddFrag(myWinsFrag , "My Wins");
        view_pagera_dapter.AddFrag(myBidsFrag , "My Bids");
        viewPager.setAdapter(view_pagera_dapter);


    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {


        private List<Fragment> frags = new ArrayList<>();
        private  List<String> titles = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void AddFrag(Fragment fragment , String title){
            frags.add(fragment);
            titles.add(title);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return frags.get(position);
        }

        @Override
        public int getCount() {
            return frags.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}