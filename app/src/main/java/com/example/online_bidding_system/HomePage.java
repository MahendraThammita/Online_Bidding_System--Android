package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class HomePage extends AppCompatActivity {

    Button home;
    Button bids;
    Button msg;
    Button profBtn;
    ImageButton addNew;
    ViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        int images[] = {R.drawable.slide1, R.drawable.slide2,R.drawable.slide3};
        flipper = findViewById(R.id.flipper);

        for (int i = 0; i<images.length; i++){

            flipImages(images[i]);
        }

        for (int image:images){

            flipImages(image);
        }


        home  = findViewById(R.id.actionBarHome);
        bids = findViewById(R.id.actionBarBid);
        msg = findViewById(R.id.actionBarMsg);
        profBtn = findViewById(R.id.actionBarProfile);
        addNew = findViewById(R.id.addNew);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext() , Antiques_Edit.class);
                startActivity(homeIntent);
            }
        });

        bids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext() , RegistrationPage.class);
                startActivity(homeIntent);
            }
        });


        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent msgintent = new Intent(getApplicationContext() , Sports_category.class);
                startActivity(msgintent);
            }
        });

        profBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profIntent = new Intent(getApplicationContext() , myBids.class);
                startActivity(profIntent);
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addIntent = new Intent(getApplicationContext() , main_categories.class);
                startActivity(addIntent);

            }
        });


    }

    public void flipImages(int image){

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }

}