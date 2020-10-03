package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.online_bidding_system.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetAntiques {
    private String time_period;
    DatabaseReference dbRef;
    Context context;

    public GetAntiques(Context context) {
        this.time_period = time_period;
        this.dbRef = FirebaseDatabase.getInstance().getReference("Antiques");
    }

    public void setValuesByAuctId(String auctionID){
        dbRef.child(auctionID);


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                    //View view = layoutInflater.inflate(R.layout.my_auction_card , false);
                    String period = dataSnapshot.child("time_period").getValue().toString();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public String getTime_period() {
        return time_period;
    }

    public void setTime_period(String time_period) {
        this.time_period = time_period;
    }
}
