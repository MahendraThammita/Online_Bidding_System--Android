package com.example.online_bidding_system;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyBidsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyBidsFragment extends Fragment {

    ListView bidList;
    DatabaseReference dbRef;


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

        dbRef = FirebaseDatabase.getInstance().getReference();

        String items[] = {"Item Name 1" , "Item Name 2" , "Item Name 3" , "Item Name 4" , "Item Name 5" , "Item Name 6" , "Item Name 7"};
        String times[] = {"08:00:00 12/05/2020" , "09:00:00 12/05/2020" , "10:00:00 12/05/2020" , "08:00:00 12/05/2020" , "09:00:00 12/05/2020" , "08:00:00 12/05/2020" , "08:00:00 12/05/2020"};
        int maxbid[] = {2500 , 5400 , 4852 , 6588 , 254 , 1547 , 1200};
        int mybid[] = {4500 , 800 , 2852 , 6500 , 2540 , 157 , 2000};
        int img[] = {R.drawable.sampleuser , R.drawable.books , R.drawable.watch , R.drawable.sampleuser , R.drawable.books , R.drawable.watch ,  R.drawable.books};

//        ArrayAdapter arradpt = new ArrayAdapter<String>(getActivity(), R.layout.my_bid_card , R.id.myBidCardTitle, items);
//
        bidList = view.findViewById(R.id.myBidsList);
//
//        bidList.setAdapter(arradpt);
//        return view;

        MyAdapter singleCard = new MyAdapter(getActivity() , items , times , maxbid , mybid , img);
        bidList.setAdapter(singleCard);

        return view;
    }




}