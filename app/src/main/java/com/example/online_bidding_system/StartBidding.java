package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartBidding extends AppCompatActivity {

    Button bid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_bidding);

        bid = findViewById(R.id.placebid);
    }


    @Override
    protected void onResume() {
        super.onResume();

        bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent placeBid = new Intent(StartBidding.this, PlaceBid.class);

            }
        });

    }
}