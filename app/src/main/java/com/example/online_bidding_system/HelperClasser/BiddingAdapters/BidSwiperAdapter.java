package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.online_bidding_system.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BidSwiperAdapter extends RecyclerView.Adapter<BidSwiperAdapter.BidSwiperViewHoldes> {


    ArrayList<BidSwiperClass> bidSwiperLocation;

    public BidSwiperAdapter(ArrayList<BidSwiperClass> bidSwiperClass) {
        this.bidSwiperLocation = bidSwiperClass;
    }

    @NonNull
    @Override
    public BidSwiperViewHoldes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewDesign = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_swipe , parent , false);
        BidSwiperViewHoldes bidSwiperViewHoldes = new BidSwiperViewHoldes(viewDesign);
        return bidSwiperViewHoldes;
    }

    @Override
    public void onBindViewHolder(@NonNull BidSwiperViewHoldes holder, int position) {

        BidSwiperClass bidSwiperClass = bidSwiperLocation.get(position);
        //holder.image.setImageResource(bidSwiperClass.getImage());
        Picasso.get().load(bidSwiperClass.getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {

        return bidSwiperLocation.size();
    }


    public static class BidSwiperViewHoldes extends RecyclerView.ViewHolder{

        ImageView image;

        public BidSwiperViewHoldes(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.swiper_single_img);
        }
    }
}
