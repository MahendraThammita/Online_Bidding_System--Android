package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class DraftAdapter extends ArrayAdapter<MyBidsCard> {

    List<MyBidsCard> myDrafts;
    Context context;


    public DraftAdapter(@NonNull Context context, int resource, @NonNull List<MyBidsCard> objects) {
        super(context, resource, objects);
        this.context = context;
        this.myDrafts = objects;
    }




}
