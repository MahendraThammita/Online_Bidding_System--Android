package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.online_bidding_system.R;

import java.util.List;

public class DraftAdapter extends ArrayAdapter<MyBidsCard> {

    private List<MyBidsCard> myDrafts;
    private Context context;


    public DraftAdapter(@NonNull Context context, int resource, @NonNull List<MyBidsCard> objects) {
        super(context, resource, objects);
        this.context = context;
        this.myDrafts = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //return super.getView(position, convertView, parent);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View Draftcard = layoutInflater.inflate(R.layout.draft_auction_card , parent , false);

        TextView draftName = Draftcard.findViewById(R.id.DraftAuctionCardAuctionName);
        TextView draftId = Draftcard.findViewById(R.id.DraftAuctionCardAuctionID);
        TextView draftCategory = Draftcard.findViewById(R.id.DraftAuctionCardAuctionCate);
        TextView draftStart = Draftcard.findViewById(R.id.DraftAuctionCardAuctionStBid);
        TextView draftEnds = Draftcard.findViewById(R.id.auctionhignbidEndsAtVal);
        TextView draftStatus = Draftcard.findViewById(R.id.auctionStatudVal);


        MyBidsCard DraftBid = myDrafts.get(position);

        draftName.setText(DraftBid.getTitle());
        draftId.setText(DraftBid.getAuctionId());
        draftCategory.setText(DraftBid.getType());
        draftStart.setText(DraftBid.getStart_Price() + " Rs");
        draftStatus.setText(DraftBid.getStatus());


        return Draftcard;


    }
}
