package com.example.online_bidding_system;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyBidsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyBidsFragment extends Fragment {

    ListView bidList;


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

        String items[] = {"Item Name 1" , "Item Name 2" , "Item Name 3" , "Item Name 4" , "Item Name 5" , "Item Name 6" , "Item Name 7"};

        ArrayAdapter arradpt = new ArrayAdapter<String>(getActivity(), R.layout.my_bid_card , R.id.myBidCardTitle, items);

        bidList = view.findViewById(R.id.myBidsList);

        bidList.setAdapter(arradpt);
        return view;

    }
}