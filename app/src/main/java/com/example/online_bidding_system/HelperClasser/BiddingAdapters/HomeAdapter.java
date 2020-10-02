package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.online_bidding_system.R;

import java.util.Arrays;
import java.util.List;

public class HomeAdapter extends ArrayAdapter<HomeCard> {
    Context context;
    List<HomeCard> AdList;



    public HomeAdapter(@NonNull Context context, int resource, List<HomeCard> AdList) {
        super(context, resource ,  AdList);
        this.context = context;
        this. AdList =  AdList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View HomeCard = layoutInflater.inflate(R.layout.homepage_card , parent , false);

        ImageView cardImg = HomeCard.findViewById(R.id.HomeAdImg);
        TextView AdTitle = HomeCard.findViewById(R.id.HomeAdTitle);
        TextView AdMaxBid = HomeCard.findViewById(R.id.HomeAdBid);
        TextView AdTime = HomeCard.findViewById(R.id.HomeAdTime);


        HomeCard Ad = AdList.get(position);
        AdTitle.setText(Ad.getTitle());
        AdMaxBid.setText( "Rs " + Ad.getMaxBid());
        AdTime.setText(Ad.getDuration());


        return HomeCard;
    }
}
