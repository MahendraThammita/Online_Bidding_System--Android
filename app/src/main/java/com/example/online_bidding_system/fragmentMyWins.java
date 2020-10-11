package com.example.online_bidding_system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyBidsCard;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyWinAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentMyWins#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentMyWins extends Fragment {

    ListView list;
    DatabaseReference DbRefWins;
    List<MyBidsCard> myWinCards;
    MyWinAdapter singleWinCard;
    private SharedPreferences shareP;
    private String loged_UID;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentMyWins() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentMyWins.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentMyWins newInstance(String param1, String param2) {
        fragmentMyWins fragment = new fragmentMyWins();
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
        Log.i("sharepref " , "pREF : "  + loged_UID );
        if(loged_UID.equals("")){
            Intent toLogin = new Intent(getActivity() , LogIn_Page.class);
            startActivity(toLogin);
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wins, container, false);

        DbRefWins = FirebaseDatabase.getInstance().getReference().child("User_Bids").child(loged_UID);
        //MyBidsCard winOb = new MyBidsCard();
        myWinCards = new ArrayList<>();
        list = view.findViewById(R.id.myWinsList);


        DbRefWins.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Duration = Objects.requireNonNull(ds.child("duration").getValue()).toString();
                    String endDate = Objects.requireNonNull(ds.child("date").getValue()).toString();
                    String seller_id = Objects.requireNonNull(ds.child("seller_ID").getValue()).toString();
                    String ContactNo = Objects.requireNonNull(ds.child("contact").getValue()).toString();
                    String Title = Objects.requireNonNull(ds.child("title").getValue()).toString();
                    String img = Objects.requireNonNull(ds.child("img").getValue()).toString();
                    int MaxBid = Integer.parseInt(Objects.requireNonNull(ds.child("maxBid").getValue()).toString());
                    int Mybid = 0;
                    if(ds.child("mybid").getValue() != null){
                        Mybid = Integer.parseInt(Objects.requireNonNull(ds.child("mybid").getValue()).toString());
                    }

                    Log.i("Fragment My wins" , "Auction ID : " + Title);

//                    LocaleDataTime datePart = LocaleData.parse(endDate);
                    LocalDate datPart = LocalDate.parse(endDate);
                    LocalTime timePart = LocalTime.parse(Duration);
                    LocalDateTime contactDate = LocalDateTime.of(datPart, timePart);
                    String finalDate = contactDate.toString();

                    ZoneId zid = ZoneId.of("Asia/Colombo");
                    LocalDateTime now = LocalDateTime.now(zid);


                    if (contactDate.isBefore(now) || contactDate.isEqual(now)) {
                        //Change the status of AD
                        String adID = ds.getKey().toString();

                        if (MaxBid == Mybid) {
                            //This is a bid won
                            MyBidsCard winningBid = new MyBidsCard();
                            winningBid.setMywinCardValues(Title, ContactNo, Duration, endDate, MaxBid, Mybid, seller_id , img);
                            myWinCards.add(winningBid);
                        }
                    }


                }
                if (myWinCards != null) {
                    singleWinCard = new MyWinAdapter(getActivity(), R.layout.my_wins_card, myWinCards);
                    list.setAdapter(singleWinCard);
                    //singleWinCard.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return view;


    }
}