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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
    private SharedPreferences shareP;
    private String loged_UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        shareP = getSharedPreferences("sharedPrefName", Context.MODE_PRIVATE);
        String logEmail = shareP.getString("UserEmail" , null);
        loged_UID = shareP.getString("USER_ID" , null);
        if(loged_UID == null){
            Intent toLogin = new Intent(getApplicationContext() , LogIn_Page.class);
            startActivity(toLogin);
        }


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

        tabedLay.getTabAt(0).setIcon(R.drawable.trouphy);
        tabedLay.getTabAt(1).setIcon(R.drawable.my_bids_icon);
        tabedLay.getTabAt(2).setIcon(R.drawable.my_auctions_icon);

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
                Intent in3 = new Intent(getApplicationContext() , TabedAuctions.class);
                switch (item.getItemId()){
                    case R.id.Drawable_myBids:
                        Intent in10 = new Intent(getApplicationContext() , HomePage.class);
                        stopService(in3);
                        startActivity(in10);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_top_nav , menu);
        return true;
    }
}