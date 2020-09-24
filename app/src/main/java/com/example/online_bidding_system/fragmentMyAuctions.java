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
 * Use the {@link fragmentMyAuctions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentMyAuctions extends Fragment {

    ListView auctList;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_auctions, container, false);

        String items[] = {"Auction Name 1" , "Auction Name 2" , "Auction Name 3" , "Auction Name 4" , "Auction Name 5" , "Auction Name 6" , "Auction Name 7"};

        ArrayAdapter singleAuction = new ArrayAdapter(getActivity() , R.layout.my_auction_card , R.id.AuctionCardAuctionName, items);

        auctList = view.findViewById(R.id.AuctionCardsList);

        auctList.setAdapter(singleAuction);

        return view;
    }
}