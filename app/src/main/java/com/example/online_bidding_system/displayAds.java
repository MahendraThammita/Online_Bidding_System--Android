package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class displayAds extends AppCompatActivity {
//BidId

    TextView nametext;
    LinearLayout dveLenier;
    private DrawerLayout drawer;
    private NavigationView navi;
    private Toolbar primTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ads);
        Intent retriveIntent = getIntent();
        String AuctName = retriveIntent.getStringExtra("BidId").toString();
        //String AuctName = retriveIntent.getStringExtra("PassAuctId").toString();

        dveLenier = findViewById(R.id.subCate_Dvd_Movie);
        dveLenier.removeAllViews();

        nametext = findViewById(R.id.displayAuct_ItemName);
        nametext.setText(AuctName.toString());







        //Drawer Section
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
                        Intent in0 = new Intent(displayAds.this , Draft_Auctions.class);
                        startActivity(in0);
                        break;
                    case R.id.Drawable_myWins:
                        Intent in1 = new Intent(displayAds.this , MyWins.class);
                        startActivity(in1);
                        break;
                    case R.id.Drawable_ViewAuctions:
                        Intent in2 = new Intent(displayAds.this ,TabedAuctions.class);
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
}