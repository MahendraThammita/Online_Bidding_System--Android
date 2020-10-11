package com.example.online_bidding_system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyAuctionsAdapter;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyBidsCard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentMyAuctions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentMyAuctions extends Fragment {

    ListView auctList;
    DatabaseReference bySellerRef;
    List<MyBidsCard> myAuctionCards;
    MyAuctionsAdapter singleAuction;
    private SharedPreferences shareP;
    private String loged_UID;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentMyAuctions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentMyAuctions.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentMyAuctions newInstance(String param1, String param2) {
        fragmentMyAuctions fragment = new fragmentMyAuctions();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        shareP = this.getActivity().getSharedPreferences("sharedPrefName", Context.MODE_PRIVATE);
        String logEmail = shareP.getString("UserEmail" , null);
        loged_UID = shareP.getString("USER_ID" , "");
        if(loged_UID.equals("")){
            Intent toLogin = new Intent(getActivity() , LogIn_Page.class);
            startActivity(toLogin);
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_auctions, container, false);
        //bySellerRef = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("seller_id").equalTo("CUS2");
        Query bySellerQuery = FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("seller_ID").equalTo(loged_UID);

        myAuctionCards = new ArrayList<>();
        auctList = view.findViewById(R.id.AuctionCardsList);

        bySellerQuery.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    int MaxBid = 0;
                    String Duration = Objects.requireNonNull(ds.child("duration").getValue()).toString();
                    String endDate = Objects.requireNonNull(ds.child("date").getValue()).toString();
                    String Type = Objects.requireNonNull(ds.child("type").getValue()).toString();
                    String Title = Objects.requireNonNull(ds.child("title").getValue()).toString();
                    Log.i("My Auction Fragment" , "Auction ID : " + Title );
                    String status = Objects.requireNonNull(ds.child("status").getValue()).toString();
                    String img = Objects.requireNonNull(ds.child("Img").child("0").getValue()).toString();
                    String ADid = ds.getKey();
                    MaxBid = Integer.valueOf(Objects.requireNonNull(ds.child("maxBid").getValue()).toString());


                    LocalDate datPart = LocalDate.parse(endDate);
                    LocalTime timePart = LocalTime.parse(Duration);
                    LocalDateTime contactDate = LocalDateTime.of(datPart , timePart);
                    String finalDate = contactDate.toString();

                    MyBidsCard myAuction = new MyBidsCard();
                    myAuction.setMyAuctionCardValues(Title , Duration , finalDate , MaxBid , ADid , Type , status , img);
                    myAuctionCards.add(myAuction);
                }
                if(myAuctionCards != null){
                    singleAuction = new MyAuctionsAdapter(getActivity() , R.layout.my_auction_card , myAuctionCards);
                    auctList.setAdapter(singleAuction);
                    //singleAuction.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}