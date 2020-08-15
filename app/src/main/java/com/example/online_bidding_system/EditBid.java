package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.BidSwiperAdapter;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.BidSwiperClass;

import java.util.ArrayList;

public class EditBid extends AppCompatActivity {

    RecyclerView imgeRecycle;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bid);

        imgeRecycle = findViewById(R.id.bidImagerSwiperRecyclar);
        
        iamgeRecycler();
        
    }

    private void iamgeRecycler() {

        imgeRecycle.setHasFixedSize(true);
        imgeRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        ArrayList<BidSwiperClass> bidsimgAdapter = new ArrayList<>();

        bidsimgAdapter.add(new BidSwiperClass(R.drawable.phone));

//        bidsimgAdapter.add(new BidSwiperClass(R.drawable.phone));
        adapter = new BidSwiperAdapter(bidsimgAdapter);

        imgeRecycle.setAdapter(adapter);
    }
}