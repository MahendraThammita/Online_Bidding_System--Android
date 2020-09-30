package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class displayAds extends AppCompatActivity {
//BidId

    TextView nametext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ads);
        Intent retriveIntent = getIntent();
        String AuctName = retriveIntent.getStringExtra("BidId").toString();

        nametext = findViewById(R.id.displayAuct_ItemName);
        nametext.setText(AuctName.toString());

    }
}