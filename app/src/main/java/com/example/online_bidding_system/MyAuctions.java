package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyAuctions extends AppCompatActivity {

    ListView listV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_auctions);

        String items[] = {"Auction Name 1" , "Auction Name 2" , "Auction Name 3" , "Auction Name 4" , "Auction Name 5" , "Auction Name 6" , "Auction Name 7"};

        ArrayAdapter singleAuction = new ArrayAdapter(this, R.layout.my_auction_card , R.id.AuctionCardAuctionName, items);

        listV = findViewById(R.id.AuctionCardsList);

        listV.setAdapter(singleAuction);

    }
}