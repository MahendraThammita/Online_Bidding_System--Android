package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Draft_Auctions extends AppCompatActivity {

    ListView draftsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft__auctions);

        String[] phoneList = {"Apple Iphone-x" , "Samsung A-60" , "Huwavi Phone"};


        draftsList = findViewById(R.id.DraftAuctionList);

        ArrayAdapter singleDraft = new ArrayAdapter(this , R.layout.draft_auction_card , R.id.DraftAuctionCardAuctionName , phoneList);

        draftsList.setAdapter(singleDraft);

    }
}