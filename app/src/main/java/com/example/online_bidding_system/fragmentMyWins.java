package com.example.online_bidding_system;

import android.icu.util.LocaleData;
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

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyBidsCard;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyWinAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wins, container, false);

        DbRefWins = FirebaseDatabase.getInstance().getReference().child("User_Bids").child("CUS1");
        //MyBidsCard winOb = new MyBidsCard();
        myWinCards = new ArrayList<>();
        list = view.findViewById(R.id.myWinsList);


        DbRefWins.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String Duration = ds.child("Duration").getValue().toString();
                    String endDate = ds.child("endDate").getValue().toString();
                    String seller_id = ds.child("seller_id").getValue().toString();
                    String ContactNo = ds.child("ContactNo").getValue().toString();
                    int MaxBid = Integer.valueOf(ds.child("MaxBid").getValue().toString());
                    int Mybid = Integer.valueOf(ds.child("Mybid").getValue().toString());

//                    LocaleDataTime datePart = LocaleData.parse(endDate);
                    LocalDate datPart = LocalDate.parse(endDate);
                    LocalTime timePart = LocalTime.parse(Duration);
                    LocalDateTime contactDate = LocalDateTime.of(datPart , timePart);
                    String finalDate = contactDate.toString();

                    ZoneId zid = ZoneId.of("Asia/Colombo");
                    LocalDateTime now = LocalDateTime.now(zid);

                    Log.i("datetest" , "Edited Date : " + finalDate);
                    Log.i("today" , "This Time : " + now);

                    if(contactDate.isBefore(now) || contactDate.isEqual(now)){
                        //Change the status of AD
                        String adID = ds.getKey().toString();
                        Log.i("EndAuction" , "Ended Auctions ID : " + adID);

                        if(MaxBid == Mybid){
                            //This is a bid won
                            Log.i("WonBid" , "Won Auctions ID : " + adID);
                            MyBidsCard winningBid = new MyBidsCard();
                            winningBid.setMywinCardValues(ContactNo , Duration , endDate , MaxBid , Mybid , seller_id);
                            myWinCards.add(winningBid);
                        }
                    }



                }
                if(myWinCards != null){
                    singleWinCard = new MyWinAdapter(getActivity() , R.layout.my_wins_card , myWinCards);
                    list.setAdapter(singleWinCard);
                    singleWinCard.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        String items[] = {"Gaucci Watch" , "Item Name 2" , "Item Name 3" , "Item Name 4" , "Item Name 5" , "Item Name 6" , "Item Name 7"};


        ArrayAdapter<String> singleWin = new ArrayAdapter<String>(getActivity(), R.layout.my_wins_card , R.id.myWinCardTitle , items);

        list.setAdapter(singleWin);


        return view;


    }
}