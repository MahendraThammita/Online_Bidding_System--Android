package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.online_bidding_system.R;

import java.util.List;

public class MyAuctionsAdapter extends ArrayAdapter<MyBidsCard> {

    Context context;
    List<MyBidsCard> myAuctList;
    int[] imgs = {R.drawable.compass , R.drawable.dreamcatcher , R.drawable.auction1 , R.drawable.auction3 , R.drawable.cat_book , R.drawable.cat_dvd , R.drawable.cat_fashion , R.drawable.cat_garden , R.drawable.cat_handmade , R.drawable.cat_hobby , R.drawable.cat_handmade };

    public MyAuctionsAdapter(@NonNull Context context, int resource, @NonNull List<MyBidsCard> objects) {
        super(context, resource, objects);
        this.context = context;
        this.myAuctList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View auctCard = layoutInflater.inflate(R.layout.my_auction_card , parent , false);

        TextView auctTitle = auctCard.findViewById(R.id.AuctionCardAuctionName);
        TextView auctdMaxBid = auctCard.findViewById(R.id.auctionhignbidVal);
        TextView auctId = auctCard.findViewById(R.id.myAuctCardAuctId);
        ImageView auctcardImg = auctCard.findViewById(R.id.AuctioCrdImg);
        TextView auctTime = auctCard.findViewById(R.id.auctionhignbidEndsAtVal);
        TextView auctStatus = auctCard.findViewById(R.id.auctionStatudVal);
        TextView auctionCateVal = auctCard.findViewById(R.id.auctionCateVal);

        MyBidsCard auction = myAuctList.get(position);
        auctTitle.setText(auction.getTitle());
        auctdMaxBid.setText(auction.getMaxBid() + " Rs");
        auctId.setText(auction.getAuctionId());
        auctTime.setText(auction.getDuration());
        auctStatus.setText(auction.getStatus());
        auctionCateVal.setText(auction.getType());
        auctcardImg.setImageResource(imgs[position]);


        return auctCard;
    }
}
