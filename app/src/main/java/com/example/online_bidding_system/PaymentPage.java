package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentPage extends AppCompatActivity {


    Button payBtn;
    TextView paymentAuctId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        Intent getID = getIntent();
        String auctId = getID.getStringExtra("AUCTION_ID");

        payBtn  = findViewById(R.id.paynow);
        paymentAuctId = findViewById(R.id.paymentAuctId);

        paymentAuctId.setText(auctId);


        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });


    }
}