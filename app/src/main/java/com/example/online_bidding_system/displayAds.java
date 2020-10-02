package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class displayAds extends AppCompatActivity {
//BidId

    TextView nametext;
    LinearLayout dveLenier;

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

    }
}