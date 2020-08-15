package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class myBids extends AppCompatActivity {

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bids);


        String items[] = {"Item Name 1" , "Item Name 2" , "Item Name 3" , "Item Name 4" , "Item Name 5" , "Item Name 6" , "Item Name 7"};

        ArrayAdapter arradpt = new ArrayAdapter<String>(this,R.layout.my_bid_card , R.id.myBidCardTitle, items);

        lv = findViewById(R.id.myBidsList);

        lv.setAdapter(arradpt);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent in = new Intent(myBids.this , main_categories.class);
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int i, long l) {

                startActivity(in);
            }
       });
    }

//    public void gotoMore(){
//        Intent in2 = new Intent(this , MainActivity.class);
//        startActivity(in2);
//    }



}