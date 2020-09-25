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

public class MyAdapter extends ArrayAdapter<MyBidsCard> {
        Context context;
        List<MyBidsCard> myBidList;



    public MyAdapter(@NonNull Context context, int resource, List<MyBidsCard> myBidList) {
        super(context, resource , myBidList);
        this.context = context;
        //this.addImg = addImg;
        this.myBidList = myBidList;
//        Log.i("ShowData" , "data Returned to adapter");
//        Log.i("ShowData" , "data Returned to adapter" + Arrays.toString(myBidList.toArray()));
    }


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
            bidTitle.setText(bid.getTitle());
            cardMaxBid.setText(bid.getMaxBid() + " Rs");
            cardMyBid.setText(bid.getMybid() + " Rs");

//           Log.i("ShowData" , "data Returned to adapter values" + bid.getMybid());

            return bidCard;
        }
    }
