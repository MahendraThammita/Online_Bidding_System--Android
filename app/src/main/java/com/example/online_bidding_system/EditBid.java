package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.BidSwiperAdapter;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.BidSwiperClass;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class EditBid extends AppCompatActivity {

    RecyclerView imgeRecycle;
    RecyclerView.Adapter adapter;
    DrawerLayout drawer;
    NavigationView navi;
    Toolbar primTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bid);

        imgeRecycle = findViewById(R.id.bidImagerSwiperRecyclar);
        iamgeRecycler();

        drawer = findViewById(R.id.DrwerLay);
        navi = (NavigationView) findViewById(R.id.nav_view);
        primTool = findViewById(R.id.primaryActbar);

        setSupportActionBar(primTool);


        navi.bringToFront();
        ActionBarDrawerToggle toggle1 = new ActionBarDrawerToggle(this , drawer , primTool , R.string.OpenDrawerDes , R.string.CloseDrawerDes);
        drawer.addDrawerListener(toggle1);
        toggle1.syncState();


//        navi.setNavigationItemSelectedListener(this);


        navi.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.Drawable_myBids:
                        Intent in0 = new Intent(EditBid.this , myBids.class);
                        startActivity(in0);
                        break;
                    case R.id.Drawable_myWins:
                        Intent in1 = new Intent(EditBid.this , MyWins.class);
                        startActivity(in1);
                        break;
                    case R.id.Drawable_ViewAuctions:
                        Intent in2 = new Intent(EditBid.this ,MyAuctions.class);
                        startActivity(in2);
                        break;
                    case R.id.Drawable_myAuctions:
                        Intent in3 = new Intent(getApplicationContext() , HomePage.class);
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

    private void iamgeRecycler() {

        imgeRecycle.setHasFixedSize(true);
        imgeRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        ArrayList<BidSwiperClass> bidsimgAdapter = new ArrayList<>();

        bidsimgAdapter.add(new BidSwiperClass(R.drawable.phone));
        bidsimgAdapter.add(new BidSwiperClass(R.drawable.rings));
        bidsimgAdapter.add(new BidSwiperClass(R.drawable.books));
        bidsimgAdapter.add(new BidSwiperClass(R.drawable.phone));
        bidsimgAdapter.add(new BidSwiperClass(R.drawable.rings));


//        bidsimgAdapter.add(new BidSwiperClass(R.drawable.phone));
        adapter = new BidSwiperAdapter(bidsimgAdapter);

        imgeRecycle.setAdapter(adapter);
    }
}