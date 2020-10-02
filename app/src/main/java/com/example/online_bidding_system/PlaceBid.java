package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyBidsCard;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlaceBid extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navi;
    Toolbar primTool;
    DatabaseReference getDetailssDbRef;

    TextView bidName , bidID , bidStartPrice , bidMaxBid , bidEndsAt;
    Button btnConfermBid;
    TextInputEditText bidGetter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_bid);

        Intent retriverIntent = getIntent();
        final String auctionId = retriverIntent.getStringExtra("AUCTION_ID").toString();

        bidName = findViewById(R.id.placeBid_ItemName);
        bidID = findViewById(R.id.placeBid_ID);
        bidStartPrice = findViewById(R.id.placeBid_StartPrice);
        bidMaxBid = findViewById(R.id.placeBid_MaxBid);
        bidEndsAt = findViewById(R.id.placeBid_date);
        btnConfermBid = findViewById(R.id.placeBid_ConfirmBidBtn);
        bidGetter = findViewById(R.id.placeBid_newBidValue);

        getDetailssDbRef = FirebaseDatabase.getInstance().getReference("Adverticement").child(auctionId);

        getDetailssDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    String Duration = dataSnapshot.child("duration").getValue().toString();
                    String endDate = dataSnapshot.child("date").getValue().toString();
                    String Type = dataSnapshot.child("type").getValue().toString();
                    String Title = dataSnapshot.child("title").getValue().toString();
                    String ADid = dataSnapshot.getKey().toString();
                    String contact = dataSnapshot.child("contact").getValue().toString();
                    String des = dataSnapshot.child("description").getValue().toString();
                    String sellerId =dataSnapshot.child("seller_ID").getValue().toString();
                    String status =dataSnapshot.child("status").getValue().toString();
                    int MaxBid = Integer.valueOf(dataSnapshot.child("maxBid").getValue().toString());
                    int StartPrice = Integer.valueOf(dataSnapshot.child("price").getValue().toString());

                    MyBidsCard myBidsCard = new MyBidsCard(ADid , contact , des , Duration , Title , Type , endDate , sellerId , status , MaxBid , StartPrice);

                    bidName.setText(myBidsCard.getTitle());
                    bidID.setText(myBidsCard.getAuctionId());
                    bidStartPrice.setText(myBidsCard.getStart_Price()  + ".00 Rs");
                    bidMaxBid.setText(myBidsCard.getMaxBid()  + ".00 Rs");
                    bidEndsAt.setText(String.valueOf(myBidsCard.getStart_Price()));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnConfermBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatabaseReference setBidDbRef = FirebaseDatabase.getInstance().getReference("Adverticement").child(auctionId);
                setBidDbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChildren()) {
                            String Duration = dataSnapshot.child("duration").getValue().toString();
                            String endDate = dataSnapshot.child("date").getValue().toString();
                            String Type = dataSnapshot.child("type").getValue().toString();
                            String Title = dataSnapshot.child("title").getValue().toString();
                            String ADid = dataSnapshot.getKey().toString();
                            String contact = dataSnapshot.child("contact").getValue().toString();
                            String des = dataSnapshot.child("description").getValue().toString();
                            String sellerId = dataSnapshot.child("seller_ID").getValue().toString();
                            String status = dataSnapshot.child("status").getValue().toString();
                            int MaxBid = Integer.valueOf(dataSnapshot.child("maxBid").getValue().toString());
                            int StartPrice = Integer.valueOf(dataSnapshot.child("price").getValue().toString());

                            MyBidsCard myBidsCard = new MyBidsCard(ADid, contact, des, Duration, Title, Type, endDate, sellerId, status, MaxBid, StartPrice);

                            try{

                                float newBid = Float.parseFloat(bidGetter.getText().toString());
                                float oldBid = myBidsCard.getMaxBid();

                                if(TextUtils.isEmpty(bidGetter.getText().toString())){
                                    Toast.makeText(getApplicationContext() , "Please Enter a Bid Before Confirm" , Toast.LENGTH_SHORT);
                                }
                                else if(newBid <= oldBid){
                                    Toast.makeText(getApplicationContext() , "Please Enter a Bid Larger than existing" , Toast.LENGTH_SHORT);
                                }
                                else {
                                    //setBidDbRef.child("maxBid").setValue(newBid)
                                    Adverticement editedAd = new Adverticement();
                                    editedAd.setContact(myBidsCard.getContactNo().toString());
                                    editedAd.setDescription(myBidsCard.getDescription().toString());
                                    editedAd.setDuration(myBidsCard.getDuration().toString());
                                    editedAd.setTitle(myBidsCard.getTitle().toString());
                                    editedAd.setType(myBidsCard.getType().toString());
                                    editedAd.setDate(myBidsCard.getEndDate().toString());
                                    editedAd.setStatus(myBidsCard.getStatus().toString());
                                    editedAd.setMaxBid(String.valueOf(newBid));
                                    editedAd.setPrice(String.valueOf(myBidsCard.getStart_Price()));

                                    setBidDbRef.setValue(editedAd);
                                    setBidDbRef.child("precious_Bid").setValue(myBidsCard.getMaxBid());

                                    setUserAuction(editedAd , newBid);

                                }

                            }
                            catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext() , "Please Enter a valid bid" , Toast.LENGTH_SHORT);
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            private void setUserAuction(Adverticement editedAd, float newBid) {
                DatabaseReference userBidRef = FirebaseDatabase.getInstance().getReference("User_Bids").child("CUS1");
                userBidRef.setValue(editedAd);
                userBidRef.child("previous_Bid").setValue(newBid);
                userBidRef.child("previous_Bid").setValue(newBid);
                userBidRef.child("mybid").setValue(newBid);
                Toast.makeText(getApplicationContext() , "Bid Placed Successfully" , Toast.LENGTH_SHORT);
            }
        });








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
                        Intent in0 = new Intent(PlaceBid.this , myBids.class);
                        startActivity(in0);
                        break;
                    case R.id.Drawable_myWins:
                        Intent in1 = new Intent(PlaceBid.this , MyWins.class);
                        startActivity(in1);
                        break;
                    case R.id.Drawable_ViewAuctions:
                        Intent in2 = new Intent(PlaceBid.this ,MyAuctions.class);
                        startActivity(in2);
                        break;
                    case R.id.Drawable_myAuctions:
                        Intent in3 = new Intent(getApplicationContext() , MyAuctions.class);
                        startActivity(in3);
                        break;
                    default:
                        Intent in6 = new Intent(getApplicationContext() , MyAuctions.class);
                        startActivity(in6);
                }
                return true;
            }
        });


    }
}