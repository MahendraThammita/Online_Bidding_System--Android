package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class main_categories extends AppCompatActivity implements View.OnClickListener {


    CardView cardView1;
    CardView cardView2;
    CardView cardView3;
    CardView cardView4;
    CardView cardView5;
    CardView cardView6;
    CardView cardView7;
    CardView cardView8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_categories);


        cardView1 = (CardView) findViewById(R.id.cat1);
        cardView2 = (CardView) findViewById(R.id.cat2);
        cardView3 = (CardView) findViewById(R.id.cat3);
        cardView4 = (CardView) findViewById(R.id.cat4);
        cardView5 = (CardView) findViewById(R.id.cat5);
        cardView6 = (CardView) findViewById(R.id.cat6);
        cardView7 = (CardView) findViewById(R.id.cat7);
        cardView8 = (CardView) findViewById(R.id.cat8);

        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
        cardView5.setOnClickListener(this);
        cardView6.setOnClickListener(this);
        cardView7.setOnClickListener(this);
        cardView8.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.cat1: i = new Intent(this,handmade_category.class);startActivity(i); break;
            case R.id.cat2: i = new Intent(this,Antiques_Category.class);startActivity(i); break;
            case R.id.cat3: i = new Intent(this,books_category.class);startActivity(i); break;
            case R.id.cat4: i = new Intent(this,DVDnMovies_category.class);startActivity(i); break;
            case R.id.cat5: i = new Intent(this,Fashion_category.class);startActivity(i); break;
            case R.id.cat6: i = new Intent(this,handmade_category.class);startActivity(i); break;
            case R.id.cat7: i = new Intent(this,handmade_category.class);startActivity(i); break;
            case R.id.cat8: i = new Intent(this,handmade_category.class);startActivity(i); break;
            default:break;

        }
    }
}