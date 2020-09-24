package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TabedAuctions extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navi;
    private Toolbar primTool;

    private ViewPager viewPager;
    private TabLayout tabedLay;

    private fragmentMyWins myWinsFrag;
    private  MyBidsFragment myBidsFrag;
    private fragmentMyAuctions myAuctFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabed_auctions);


        viewPager = findViewById(R.id.pager1);
        tabedLay = findViewById(R.id.tablay1);

        myWinsFrag = new fragmentMyWins();
        myBidsFrag = new MyBidsFragment();
        myAuctFrag = new fragmentMyAuctions();

        tabedLay.setupWithViewPager(viewPager);
        ViewPagerAdapter view_pagera_dapter = new ViewPagerAdapter(getSupportFragmentManager() , 0);
        view_pagera_dapter.AddFrag(myWinsFrag , "My Wins");
        view_pagera_dapter.AddFrag(myBidsFrag , "My Bids");
        view_pagera_dapter.AddFrag(myAuctFrag , "My Auctions");
        viewPager.setAdapter(view_pagera_dapter);



        drawer = findViewById(R.id.DrwerLay);
        navi = (NavigationView) findViewById(R.id.nav_view);
        primTool = findViewById(R.id.primaryActbar);

        setSupportActionBar(primTool);


        navi.bringToFront();
        ActionBarDrawerToggle toggle1 = new ActionBarDrawerToggle(this , drawer , primTool , R.string.OpenDrawerDes , R.string.CloseDrawerDes);
        drawer.addDrawerListener(toggle1);
        toggle1.syncState();


        //navi.setNavigationItemSelectedListener(this);


        navi.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.Drawable_myBids:
                        Toast.makeText(getApplicationContext() , "You are in My Bids page" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Drawable_myWins:
                        Intent in1 = new Intent(getApplicationContext() , MyWins.class);
                        startActivity(in1);
                        break;
                    case R.id.Drawable_ViewAuctions:
                        Intent in2 = new Intent(getApplicationContext(),MyAuctions.class);
                        startActivity(in2);
                        break;
                    case R.id.Drawable_myAuctions:
                        Intent in3 = new Intent(getApplicationContext() , TabedAuctions.class);
                        startActivity(in3);
                        break;
                    default:
                        Intent in6 = new Intent(getApplicationContext() , MyAuctions.class);
                        startActivity(in6);
                }
                return true;
            }
        });


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