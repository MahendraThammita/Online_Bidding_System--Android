package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyWins extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wins);

        String items[] = {"Gaucci Watch" , "Item Name 2" , "Item Name 3" , "Item Name 4" , "Item Name 5" , "Item Name 6" , "Item Name 7"};

        list = findViewById(R.id.myWinsList);

        ArrayAdapter singleWin = new ArrayAdapter(this, R.layout.my_wins_card , R.id.myWinCardTitle , items);

        list.setAdapter(singleWin);
    }



}