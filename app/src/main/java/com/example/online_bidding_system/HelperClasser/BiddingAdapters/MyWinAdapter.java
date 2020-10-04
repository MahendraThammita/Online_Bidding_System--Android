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

public class MyWinAdapter extends ArrayAdapter<MyBidsCard> {

    Context context;
    List<MyBidsCard> cardList;
    int[] imgs = {R.drawable.books , R.drawable.dreamcatcher , R.drawable.auction1 , R.drawable.auction3 , R.drawable.cat_book , R.drawable.cat_dvd , R.drawable.cat_fashion , R.drawable.cat_garden , R.drawable.cat_handmade , R.drawable.cat_hobby , R.drawable.cat_handmade };

    public MyWinAdapter(@NonNull Context context, int resource, @NonNull List<MyBidsCard> objects) {
        super(context, resource, objects);
        this.context = context;
        this.cardList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View winCard = layoutInflater.inflate(R.layout.my_wins_card , parent , false);

        TextView itemNane = winCard.findViewById(R.id.myWinCardTitle);
        TextView myBid = winCard.findViewById(R.id.CardMywinmyBid);
        TextView contNo = winCard.findViewById(R.id.myWinContactNo);
        TextView sellerId = winCard.findViewById(R.id.myWinSellerIdVal);
        TextView endingDate = winCard.findViewById(R.id.myWinDate);
        ImageView mywinImg = winCard.findViewById(R.id.myWinCardImg);

        MyBidsCard winRow = cardList.get(position);
        itemNane.setText(winRow.getTitle());
        myBid.setText(winRow.getMybid() + " Rs");
        //myBid.setText(String.valueOf(winRow.getMybid()));
        //setting int values to text fields can caused to throw Resources$NotFoundException
        //Use above syntax in those scenarios
        contNo.setText(winRow.getContactNo());
        sellerId.setText(winRow.getSeller_id());
        endingDate.setText(winRow.getEndDate());
        mywinImg.setImageResource(imgs[position]);

        return winCard;
    }
}
