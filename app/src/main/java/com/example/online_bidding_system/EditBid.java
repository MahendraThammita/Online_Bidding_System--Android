package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.BidSwiperAdapter;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.BidSwiperClass;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyBidsCard;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.TimeCalculations;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class EditBid extends AppCompatActivity {

    RecyclerView imgeRecycle;
    RecyclerView.Adapter adapter;
    DrawerLayout drawer;
    NavigationView navi;
    Toolbar primTool;
    DatabaseReference recieverRef;
    TextView bidName , bidID , myBidVal , bidMaxBid , bidEndsAt ,bidDes;
    Button btnConfermBid , btnDeleteBid;
    EditText bidGetter;
    private ArrayList<BidSwiperClass> bidsimgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bid);


        Intent recieverIntent = getIntent();
        final String auction_Id = recieverIntent.getStringExtra("AUCT_ID");

        bidName = findViewById(R.id.editBid_ItemName);
        bidDes = findViewById(R.id.editBid_BidDescription);
        bidEndsAt = findViewById(R.id.myBidTime);
        bidMaxBid = findViewById(R.id.editBid_maxBidVal);
        myBidVal = findViewById(R.id.editBidmyBidVal);
        btnConfermBid = findViewById(R.id.editBid_IncrementBtn);
        btnDeleteBid = findViewById(R.id.deleteBidBtn);
        bidGetter = findViewById(R.id.editBid_IncrementVal);

        bidsimgAdapter  = new ArrayList<>();
        imgeRecycle = findViewById(R.id.bidImagerSwiperRecyclarEditBid);
        iamgeRecycler();

        recieverRef = FirebaseDatabase.getInstance().getReference("Adverticement").child(auction_Id);

        recieverRef.addValueEventListener(new ValueEventListener() {
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
                    Log.i("Images" , "prior to get image count");
                    Long imgCount = dataSnapshot.child("Img").getChildrenCount();
                    Log.i("Images" , "Entered into for loop  " + String.valueOf(imgCount));
                    for(int j = 0 ; j < imgCount ; j++){
                        String image = dataSnapshot.child("Img").child(String.valueOf(j)).getValue().toString();
                        Log.i("Images" , "Image" + String.valueOf(j) + "  " +image);
                        bidsimgAdapter.add(new BidSwiperClass(image));
                    }
                    Log.i("Images" , "Skipped For");
                    iamgeRecycler();
                    Log.i("Images" , "Call to recycler");
                    int MaxBid = Integer.valueOf(dataSnapshot.child("maxBid").getValue().toString());
                    int StartPrice = Integer.valueOf(dataSnapshot.child("price").getValue().toString());

                    MyBidsCard myBidsCard = new MyBidsCard(ADid , contact , des , Duration , Title , Type , endDate , sellerId , status , MaxBid , StartPrice);
                    Log.i("Images" , "first on data change called");
                    bidName.setText(myBidsCard.getTitle());
                    bidDes.setText(myBidsCard.getDescription());
                    bidEndsAt.setText(myBidsCard.getEndDate() + " " + myBidsCard.getDuration());
                    bidMaxBid.setText(String.valueOf(myBidsCard.getMaxBid()) + ".00 Rs");
                    getUserBidValues(myBidsCard);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnConfermBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference editBidMainRef = FirebaseDatabase.getInstance().getReference("Adverticement").child(auction_Id);
                editBidMainRef.addValueEventListener(new ValueEventListener() {
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

                            try{
                                int newBid = Integer.parseInt(bidGetter.getText().toString());
                                int oldMax = myBidsCard.getMaxBid();

                                if(TextUtils.isEmpty(bidGetter.getText().toString())){
                                    Toast.makeText(getApplicationContext() , "Please Enter a Value Before increase" , Toast.LENGTH_SHORT).show();
                                }
                                else if(newBid <= oldMax){
                                    Toast.makeText(getApplicationContext() , "Bids Lower Than Current Bids Are Not Allowed" , Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    editBidMainRef.child("maxBid").setValue(newBid);
                                    editBidMainRef.child("precious_Bid").setValue(oldMax);
                                    updateUserBidchild(myBidsCard , newBid);
                                }

                            }
                            catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext() , "Please Enter a valid input" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnDeleteBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recieverRef = FirebaseDatabase.getInstance().getReference("User_Bids").child("CUS1").child(auction_Id);
                recieverRef.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
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

                            TimeCalculations timeCalculate = new TimeCalculations(Duration , endDate);
                            if(timeCalculate.isDeleteable()){
                                recieverRef.removeValue();
                                Intent intentTotab = new Intent(getApplicationContext() , TabedAuctions.class);
                                startActivity(intentTotab);
                            }
                            else
                                Toast.makeText(getApplicationContext() , "You Are not allowed to delete  your bid in last five hours" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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
                        Intent in0 = new Intent(EditBid.this , myBids.class);
                        startActivity(in0);
                        break;
                    case R.id.Drawable_myWins:
                        Intent in1 = new Intent(EditBid.this , MyWins.class);
                        startActivity(in1);
                        break;
                    case R.id.Drawable_ViewAuctions:
                        Intent in2 = new Intent(EditBid.this ,MyAuctions.class);
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
        
    }

    private void updateUserBidchild(final MyBidsCard myBidsCard, final int newBid) {
        final DatabaseReference refToUserBids = FirebaseDatabase.getInstance().getReference("User_Bids").child("CUS1").child(myBidsCard.getAuctionId());
        refToUserBids.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    refToUserBids.child("maxBid").setValue(newBid);
                    refToUserBids.child("mybid").setValue(newBid);
                    refToUserBids.child("precious_Bid").setValue(myBidsCard.getMaxBid());
                    Toast.makeText(getApplicationContext() , "Your Bid Is Updated Successfully" , Toast.LENGTH_SHORT).show();
                    Intent toTabed = new Intent(getApplicationContext() , TabedAuctions.class);
                    startActivity(toTabed);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getUserBidValues(MyBidsCard myBidsCard) {
        DatabaseReference useBidReference = FirebaseDatabase.getInstance().getReference("User_Bids").child("CUS1").child(myBidsCard.getAuctionId());

        useBidReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    String userBid = "";
                    if(dataSnapshot.child("mybid").getValue() != null){
                        userBid = Objects.requireNonNull(dataSnapshot.child("mybid").getValue()).toString();
                    }
                    //String userBid = dataSnapshot.child("mybid").getValue().toString();
                    myBidVal.setText(userBid + ".00 Rs");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void iamgeRecycler() {

        imgeRecycle.setHasFixedSize(true);
        imgeRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        adapter = new BidSwiperAdapter(bidsimgAdapter);
        Log.i("Adapter" , "Adapter is set");
        imgeRecycle.setAdapter(adapter);
        Log.i("Adapter" , "Set Adapter called");
    }
}