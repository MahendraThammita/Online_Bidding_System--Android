package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.online_bidding_system.PaymentPage;
import com.example.online_bidding_system.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyWinAdapter extends ArrayAdapter<MyBidsCard> {

    Context context;
    List<MyBidsCard> cardList;

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
        Button myWinsPayBtn = winCard.findViewById(R.id.myWinsPayBtn);

        final MyBidsCard winRow = cardList.get(position);
        itemNane.setText(winRow.getTitle());
        myBid.setText(winRow.getMybid() + " Rs");
        contNo.setText(winRow.getContactNo());
        sellerId.setText(winRow.getSeller_id());
        endingDate.setText(winRow.getEndDate());
        Picasso.get().load(winRow.getImg()).into(mywinImg);

        myWinsPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPay = new Intent(context , PaymentPage.class);
                toPay.putExtra("AUCTION_ID" , winRow.getAuctionId());
                context.startActivity(toPay);
            }
        });


        return winCard;
    }
}
