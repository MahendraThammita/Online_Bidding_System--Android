package com.example.online_bidding_system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyAdapter;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyBidsCard;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.TimeCalculations;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    private SharedPreferences shareP;
    private String loged_UID;


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

        shareP = this.getActivity().getSharedPreferences("sharedPrefName", Context.MODE_PRIVATE);
        String logEmail = shareP.getString("UserEmail" , null);
        loged_UID = shareP.getString("USER_ID" , "");
        if(loged_UID.equals("")){
            Intent toLogin = new Intent(getActivity() , LogIn_Page.class);
            startActivity(toLogin);
        }


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_bids, container, false);

        bidList = view.findViewById(R.id.myBidsList);
        dbRef = FirebaseDatabase.getInstance().getReference().child("User_Bids").child(loged_UID);
        //MyBidsCard my_Bid = new MyBidsCard();
        myBidsCards = new ArrayList<>();



        dbRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    String AuctId = ds.getKey().toString();
                    String ContactNo = ds.child("contact").getValue().toString();
                    String Description = ds.child("description").getValue().toString();
                    String Duration = ds.child("duration").getValue().toString();
                    String Title = ds.child("title").getValue().toString();
                    String Type = ds.child("type").getValue().toString();
                    String endDate = ds.child("date").getValue().toString();
                    String img = ds.child("img").getValue().toString();
                    int MaxBid = Integer.valueOf(ds.child("maxBid").getValue().toString());
                    int Mybid = 0;
                    if(ds.child("mybid").getValue() != null){
                        Mybid = Integer.parseInt(Objects.requireNonNull(ds.child("mybid").getValue()).toString());
                    }
                    int Start_Price = Integer.valueOf(ds.child("price").getValue().toString());

                    Log.i("ShowData" , "data Returned" + ContactNo + " - " + Description  + " - " + MaxBid);
                    TimeCalculations tc = new TimeCalculations(Duration , endDate);
                    if(! tc.isExpired()){
                        MyBidsCard my_Bid = new MyBidsCard(AuctId , ContactNo , Description , Duration , Title , Type , endDate , MaxBid , Mybid , Start_Price , img);
                        //MyBidsCard my_Bid = ds.getValue(MyBidsCard.class);
                        myBidsCards.add(my_Bid);
                    }

                }
                if(myBidsCards != null){
                    singleCard = new MyAdapter(getActivity() , R.layout.my_bid_card , myBidsCards );
                    bidList.setAdapter(singleCard);
                    //singleCard.notifyDataSetChanged();
                }

                //singleCard.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item_ID = myBidsCards.get(i).getAuctionId();
                Intent toEdit = new Intent(getActivity() , displayAds.class);
                toEdit.putExtra("BidId" , item_ID);
                bidList.getContext().startActivity(toEdit);
            }
        });



        bidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String auctionId = myBidsCards.get(i).getAuctionId();
                Intent toEditIntent = new Intent(getActivity() , EditBid.class);
                toEditIntent.putExtra("AUCT_ID" , auctionId);
                bidList.getContext().startActivity(toEditIntent);
            }
        });




        return view;
    }




}