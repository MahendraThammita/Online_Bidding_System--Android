package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.HomeAdapter;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.HomeCard;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyAdapter;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyBidsCard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePage extends AppCompatActivity {

    Button home;
    Button bids;
    Button msg;
    Button profBtn;
    ImageButton addNew;
    ViewFlipper flipper;
    DatabaseReference DBRef;
    TextView title;


    ListView listView;
    DatabaseReference dbRef;
    List<HomeCard> HomeCards;
    HomeAdapter singleCard;


    public HomePage() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        int images[] = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
        flipper = findViewById(R.id.flipper);

        for (int i = 0; i < images.length; i++) {

            flipImages(images[i]);
        }

        for (int image : images) {

            flipImages(image);
        }


        home = findViewById(R.id.actionBarHome);
        bids = findViewById(R.id.actionBarBid);
        msg = findViewById(R.id.actionBarMsg);
        profBtn = findViewById(R.id.actionBarProfile);
        addNew = findViewById(R.id.addNew);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(), Antiques_Edit.class);
                startActivity(homeIntent);
            }
        });

        bids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(), OtherEditpage.class);
                startActivity(homeIntent);
            }
        });


        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent msgintent = new Intent(getApplicationContext(), Other_category.class);
                startActivity(msgintent);
            }
        });

        profBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profIntent = new Intent(getApplicationContext(), myBids.class);
                startActivity(profIntent);
            }
        });

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addIntent = new Intent(getApplicationContext(), main_categories.class);
                startActivity(addIntent);

            }
        });


        listView = this.findViewById(R.id.HomeCardsList);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Adverticement").child("AN9");
        HomeCards = new ArrayList<>();


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    String Title = (dataSnapshot.child("title").getValue().toString());
                    int MaxBid =  Integer.parseInt(dataSnapshot.child("maxBid").getValue().toString());



                    HomeCard my_Bid = new HomeCard(Title, MaxBid);

                    HomeCards.add(my_Bid);
                }
                if (HomeCards != null) {

                    singleCard = new HomeAdapter(HomePage.this, R.layout.homepage_card, HomeCards);
                    listView.setAdapter(singleCard);
                    singleCard.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void flipImages(int image) {

        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);

    }

}


