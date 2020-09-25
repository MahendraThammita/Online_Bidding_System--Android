package com.example.online_bidding_system;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyAdapter;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyBidsCard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyBidsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyBidsFragment extends Fragment {

    ListView bidList;
    DatabaseReference dbRef;
    List<MyBidsCard> myBidsCards;
    MyAdapter singleCard;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyBidsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyBidsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyBidsFragment newInstance(String param1, String param2) {
        MyBidsFragment fragment = new MyBidsFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_bids, container, false);

        bidList = view.findViewById(R.id.myBidsList);
        dbRef = FirebaseDatabase.getInstance().getReference().child("User_Bids").child("CUS1");
        MyBidsCard my_Bid = new MyBidsCard();
        myBidsCards = new ArrayList<>();


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String ContactNo = ds.child("ContactNo").getValue().toString();
                    String Description = ds.child("Description").getValue().toString();
                    String Duration = ds.child("Duration").getValue().toString();
                    String Title = ds.child("Title").getValue().toString();
                    String Type = ds.child("Type").getValue().toString();
                    String endDate = ds.child("endDate").getValue().toString();
                    int MaxBid = Integer.valueOf(ds.child("MaxBid").getValue().toString());
                    int Mybid = Integer.valueOf(ds.child("Mybid").getValue().toString());
                    int Start_Price = Integer.valueOf(ds.child("Start_Price").getValue().toString());

                    Log.i("ShowData" , "data Returned" + ContactNo + " - " + Description  + " - " + MaxBid);
                    MyBidsCard my_Bid = new MyBidsCard(ContactNo , Description , Duration , Title , Type , endDate , MaxBid , Mybid , Start_Price);
                    //MyBidsCard my_Bid = ds.getValue(MyBidsCard.class);
                    myBidsCards.add(my_Bid);
                }
                if(myBidsCards != null){
                    Log.i("ShowData" , "data Returned to list" + Arrays.toString(myBidsCards.toArray()));
                    singleCard = new MyAdapter(getActivity() , R.layout.my_bid_card , myBidsCards );
                    bidList.setAdapter(singleCard);
                    Log.i("ShowData" , "data Returned setAdapter called");
                    singleCard.notifyDataSetChanged();
                }

                //singleCard.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        ArrayAdapter arradpt = new ArrayAdapter<String>(getActivity(), R.layout.my_bid_card , R.id.myBidCardTitle, items);
//          Log.i("ShowData" , "data Returned to list" + Arrays.toString(myBidsCards.toArray()));

//
//        bidList.setAdapter(arradpt);
//        return view;

        //MyAdapter singleCard = new MyAdapter(getActivity() , items , times , maxbid , mybid , img);




        return view;
    }




}