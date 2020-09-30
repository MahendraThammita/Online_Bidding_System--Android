package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.DraftAdapter;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyBidsCard;
import com.google.android.material.navigation.NavigationView;
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

public class Draft_Auctions extends AppCompatActivity {

    ListView draftsList;
    DrawerLayout drawer;
    NavigationView navi;
    Toolbar primTool;

    DatabaseReference dbRef;
    List<MyBidsCard> oneDraft;
    DraftAdapter oneDraftCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft__auctions);


        drawer = findViewById(R.id.DrwerLay);
        navi = (NavigationView) findViewById(R.id.nav_view);
        primTool = findViewById(R.id.primaryActbar);

        setSupportActionBar(primTool);


        navi.bringToFront();
        ActionBarDrawerToggle toggle1 = new ActionBarDrawerToggle(this , drawer , primTool , R.string.OpenDrawerDes , R.string.CloseDrawerDes);
        drawer.addDrawerListener(toggle1);
        toggle1.syncState();


//        navi.setNavigationItemSelectedListener(this);


        navi.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.Drawable_myBids:
                        Intent in0 = new Intent(Draft_Auctions.this , myBids.class);
                        startActivity(in0);
                        break;
                    case R.id.Drawable_myWins:
                        Intent in1 = new Intent(Draft_Auctions.this , MyWins.class);
                        startActivity(in1);
                        break;
                    case R.id.Drawable_ViewAuctions:
                        Intent in2 = new Intent(Draft_Auctions.this ,MyAuctions.class);
                        startActivity(in2);
                        break;
                    case R.id.Drawable_myAuctions:
                        Intent in3 = new Intent(getApplicationContext() , HomePage.class);
                        startActivity(in3);
                        break;
                    default:
                        Intent in6 = new Intent(getApplicationContext() , MyAuctions.class);
                        startActivity(in6);
                }
                return true;
            }
        });



        //String[] phoneList = {"Apple Iphone-x" , "Samsung A-60" , "Huwavi Phone"};


        draftsList = findViewById(R.id.DraftAuctionList);
        Query inactiveQuery = FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("status").equalTo("inactive");

        //ArrayAdapter singleDraft = new ArrayAdapter(this , R.layout.draft_auction_card , R.id.DraftAuctionCardAuctionName , phoneList);
        oneDraft = new ArrayList<>();

        inactiveQuery.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String Duration = ds.child("Duration").getValue().toString();
                    String endDate = ds.child("endDate").getValue().toString();
                    String Type = ds.child("Type").getValue().toString();
                    String Title = ds.child("Title").getValue().toString();
                    String ADid = ds.getKey().toString();
                    String ADStatus = ds.child("inactive").getValue().toString();
                    int Startingbid = Integer.parseInt(ds.child("price").getValue().toString());

                    LocalDate datPart = LocalDate.parse(endDate);
                    LocalTime timePart = LocalTime.parse(Duration);
                    LocalDateTime contactDate = LocalDateTime.of(datPart , timePart);
                    String finalDate = contactDate.toString();

                    MyBidsCard singleBidVals = new MyBidsCard();
                    singleBidVals.setDraftAuctionsValues(Title , Duration , endDate , Startingbid , ADid , Type);
                    oneDraft.add(singleBidVals);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        oneDraftCard = new DraftAdapter(getApplicationContext() , R.layout.draft_auction_card , oneDraft);
        draftsList.setAdapter(oneDraftCard);


        //draftsList.setAdapter(singleDraft);

    }
}