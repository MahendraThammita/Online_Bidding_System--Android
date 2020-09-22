package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class TestHome extends AppCompatActivity {

    ViewFlipper flipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_home);

        int images[] = {R.drawable.katana, R.drawable.ring,R.drawable.books};
        flipper = findViewById(R.id.flipper);

        for (int i = 0; i<images.length; i++){

            flipImages(images[i]);
        }

        for (int image:images){

            flipImages(image);
        }
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