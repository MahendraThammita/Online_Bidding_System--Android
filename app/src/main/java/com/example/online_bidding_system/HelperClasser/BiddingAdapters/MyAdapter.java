package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.online_bidding_system.R;

import java.util.Arrays;
import java.util.List;

public class MyAdapter extends ArrayAdapter<MyBidsCard> {
        Context context;
        List<MyBidsCard> myBidList;
        int[] imgs = {R.drawable.leadguitar , R.drawable.dreamcatcher , R.drawable.auction1 , R.drawable.auction3 , R.drawable.cat_book , R.drawable.cat_dvd , R.drawable.cat_fashion , R.drawable.cat_garden , R.drawable.cat_handmade , R.drawable.cat_hobby , R.drawable.cat_handmade };


    public MyAdapter(@NonNull Context context, int resource, List<MyBidsCard> myBidList) {
        super(context, resource , myBidList);
        this.context = context;
        this.myBidList = myBidList;
    }


        @RequiresApi(api = Build.VERSION_CODES.O)
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Log.i("ShowData" , "data Returned to adapter getView method called");
            //LayoutInflater layoutInflater = (LayoutInflater)getAppli().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View bidCard = layoutInflater.inflate(R.layout.my_bid_card , parent , false);

            TextView bidTitle = bidCard.findViewById(R.id.myBidCardTitle);
            TextView cardMaxBid = bidCard.findViewById(R.id.mybidmaxBidVal);
            TextView cardMyBid = bidCard.findViewById(R.id.mybidmyBidValBidVal);
            ImageView cardImg = bidCard.findViewById(R.id.myBidCardImg);
            TextView cardTime = bidCard.findViewById(R.id.myBidTimeVal);


            MyBidsCard bid = myBidList.get(position);

            TimeCalculations timeCalculations = new TimeCalculations(bid.getDuration() , bid.getEndDate());
            String remTime = timeCalculations.calcFinalDateTime();

            bidTitle.setText(bid.getTitle());
            cardMaxBid.setText(bid.getMaxBid() + " Rs");
            cardMyBid.setText(bid.getMybid() + " Rs");
            cardImg.setImageResource(imgs[position]);
            cardTime.setText(remTime);

//           Log.i("ShowData" , "data Returned to adapter values" + bid.getMybid());

            return bidCard;
        }
    }
