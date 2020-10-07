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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.BidSwiperAdapter;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.BidSwiperClass;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.GetAntiques;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyBidsCard;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.TimeCalculations;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URL;
import java.util.ArrayList;

public class displayAds extends AppCompatActivity {
//BidId


    RecyclerView imgeRecycle;
    RecyclerView.Adapter rcAdapter;
    TextView nametext;
    LinearLayout dveLenier;
    private DrawerLayout drawer;
    private NavigationView navi;
    private Toolbar primTool;
    private DatabaseReference dbRef;
    private StorageReference storeRef;

    LinearLayout antiqueLayout, booksLayout, dvdLayout, fdLayout, electronicsLayout, hmLayout, hobbyLayout, homeGardenLayout, additionalInfoLayout;

    TextView itemName;
    TextView itemDes;
    TextView itemSeller;
    TextView EndingTime;
    TextView itemMaxBid;
    TextView itemCurrentBid;
    TextView additionalInfoCap;
    Button toBidButton;

    private ArrayList<BidSwiperClass> bidsimgAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ads);
        Intent retriveIntent = getIntent();
        final String auctID = retriveIntent.getStringExtra("BidId").toString();
        //String AuctName = retriveIntent.getStringExtra("PassAuctId").toString();

        itemName = findViewById(R.id.displayAuct_ItemName);
        itemDes = findViewById(R.id.displayAuct__Description);
        itemSeller = findViewById(R.id.UseeIdDispBid);
        EndingTime = findViewById(R.id.displayAuct_EndTime);
        itemMaxBid = findViewById(R.id.displayAuct_maxBidVal);
        itemCurrentBid = findViewById(R.id.displayAuct_myBidVal);
        additionalInfoCap = findViewById(R.id.AdditionalInfocap);

        additionalInfoLayout = findViewById(R.id.additional_Info_Area);
        antiqueLayout = findViewById(R.id.subCate_Antique);
        booksLayout = findViewById(R.id.subCate_Books);
        dvdLayout = findViewById(R.id.subCate_Dvd_Movie);
        electronicsLayout = findViewById(R.id.subCate_Electronic);
        fdLayout = findViewById(R.id.subCate_Fashion);
        hmLayout = findViewById(R.id.subCate_HandMades);
        hobbyLayout = findViewById(R.id.subCate_Hobbies);
        homeGardenLayout = findViewById(R.id.subCate_Home_garden);

        imgeRecycle = findViewById(R.id.bidImagerSwiperRecyclar);
        //iamgeRecycler();

        dbRef = FirebaseDatabase.getInstance().getReference("Adverticement").child(auctID);
        bidsimgAdapter = new ArrayList<>();


        dbRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren()) {
                    String Duration = dataSnapshot.child("duration").getValue().toString();
                    String endDate = dataSnapshot.child("date").getValue().toString();
                    String Type = dataSnapshot.child("type").getValue().toString();
                    String Title = dataSnapshot.child("title").getValue().toString();
                    String ADid = dataSnapshot.getKey().toString();
                    String contact = dataSnapshot.child("contact").getValue().toString();
                    String des = dataSnapshot.child("description").getValue().toString();
                    String sellerId = dataSnapshot.child("seller_ID").getValue().toString();
                    String status = dataSnapshot.child("status").getValue().toString();
                    Long imgcount = dataSnapshot.child("Img").getChildrenCount();
                    for (int i = 0; i < imgcount; i++) {
                        String image = dataSnapshot.child("Img").child(String.valueOf(i)).getValue().toString();
                        bidsimgAdapter.add(new BidSwiperClass(image));
                    }
                    iamgeRecycler();
                    int MaxBid = Integer.valueOf(dataSnapshot.child("maxBid").getValue().toString());
                    int StartPrice = Integer.valueOf(dataSnapshot.child("price").getValue().toString());

                    Log.i("Values recieved", "ADD No : " + ADid + " " + Title + " " + Type);

                    MyBidsCard myBidsCard = new MyBidsCard(ADid, contact, des, Duration, Title, Type, endDate, sellerId, status, MaxBid, StartPrice);
                    setBasicDetails(myBidsCard);
                    //removeAllChildListviewa();
                    additionalInfoLayout.removeAllViews();

                    if (!myBidsCard.getType().equals("Other")) {
                        if (myBidsCard.getType().toString().equals("Antiques")) {
                            additionalInfoLayout.addView(additionalInfoCap);
                            additionalInfoLayout.addView(antiqueLayout);
                            setAdditionalDetailsAntiqur(myBidsCard.getAuctionId());
                        } else if (myBidsCard.getType().toString().equals("Books")) {
                            additionalInfoLayout.addView(additionalInfoCap);
                            additionalInfoLayout.addView(booksLayout);
                            setAdditionalDetailsBooks(myBidsCard.getAuctionId());
                        } else if (myBidsCard.getType().toString().equals("HomeAndGarden")) {
                            additionalInfoLayout.addView(additionalInfoCap);
                            additionalInfoLayout.addView(homeGardenLayout);
                            setAdditionalDetailsHG(myBidsCard.getAuctionId());
                        } else if (myBidsCard.getType().toString().equals("SportsAndHobbies")) {
                            additionalInfoLayout.addView(additionalInfoCap);
                            additionalInfoLayout.addView(hobbyLayout);
                            setAdditionalDetailsSH(myBidsCard.getAuctionId());
                        } else if (myBidsCard.getType().toString().equals("Electronics")) {
                            additionalInfoLayout.addView(additionalInfoCap);
                            additionalInfoLayout.addView(electronicsLayout);
                            setAdditionalDetailsElectronic(myBidsCard.getAuctionId());
                        } else if (myBidsCard.getType().toString().equals("FashionAndDesign")) {
                            additionalInfoLayout.addView(additionalInfoCap);
                            additionalInfoLayout.addView(fdLayout);
                            setAdditionalDetailsFD(myBidsCard.getAuctionId());
                        } else if (myBidsCard.getType().toString().equals("DVDandMovies")) {
                            additionalInfoLayout.addView(additionalInfoCap);
                            additionalInfoLayout.addView(dvdLayout);
                            setAdditionalDetailsDVD(myBidsCard.getAuctionId());
                        } else if (myBidsCard.getType().toString().equals("HandMades")) {
                            additionalInfoLayout.addView(additionalInfoCap);
                            additionalInfoLayout.addView(hmLayout);
                            setAdditionalDetailsHM(myBidsCard.getAuctionId());
                        }

                    }


                }

            }

            private void setAdditionalDetailsHM(String auctionId) {
                DatabaseReference antiqueDbRef = FirebaseDatabase.getInstance().getReference("HandMades").child(auctionId);
                antiqueDbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            String Genere = dataSnapshot.child("materials").getValue(String.class);
                            TextView handmade_Materials = findViewById(R.id.handmade_Materials);
                            handmade_Materials.setText(Genere);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            private void setAdditionalDetailsDVD(String auctionId) {
                DatabaseReference antiqueDbRef = FirebaseDatabase.getInstance().getReference("DVDandMovies").child(auctionId);
                antiqueDbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            String Genere = dataSnapshot.child("Genere").getValue(String.class);
                            TextView dvdGenere = findViewById(R.id.dvdGenere);
                            dvdGenere.setText(Genere);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            private void setAdditionalDetailsFD(String auctionId) {
                DatabaseReference antiqueDbRef = FirebaseDatabase.getInstance().getReference("FashionAndDesign").child(auctionId);
                antiqueDbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            String brand = dataSnapshot.child("brand").getValue(String.class);
                            String condition = dataSnapshot.child("condition").getValue(String.class);
                            String material = dataSnapshot.child("material").getValue(String.class);
                            TextView FD_Brand = findViewById(R.id.FD_Brand);
                            TextView FD_Condition = findViewById(R.id.FD_Condition);
                            TextView FD_Material = findViewById(R.id.FD_Material);
                            FD_Brand.setText(brand);
                            FD_Condition.setText(condition);
                            FD_Material.setText(material);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            private void setAdditionalDetailsElectronic(String auctionId) {
                DatabaseReference antiqueDbRef = FirebaseDatabase.getInstance().getReference("Electronics").child(auctionId);
                antiqueDbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            String brand = dataSnapshot.child("Brand").getValue(String.class);
                            String condition = dataSnapshot.child("Condition").getValue(String.class);
                            TextView E_Brand = findViewById(R.id.E_Brand);
                            TextView E_Condition = findViewById(R.id.E_Condition);
                            E_Brand.setText(brand);
                            E_Condition.setText(condition);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            private void setAdditionalDetailsSH(String auctionId) {
                DatabaseReference antiqueDbRef = FirebaseDatabase.getInstance().getReference("Hobbies&Sports").child(auctionId);
                antiqueDbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            String brand = dataSnapshot.child("brand").getValue(String.class);
                            String condition = dataSnapshot.child("condition").getValue(String.class);
                            TextView HobbyCateBrand = findViewById(R.id.HobbyCateBrand);
                            TextView HobbyCateCondition = findViewById(R.id.HobbyCateCondition);
                            HobbyCateBrand.setText(brand);
                            HobbyCateCondition.setText(condition);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            private void setAdditionalDetailsHG(String auctionId) {
                DatabaseReference antiqueDbRef = FirebaseDatabase.getInstance().getReference("Home&Garden").child(auctionId);
                antiqueDbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            String environment = dataSnapshot.child("environment").getValue(String.class);
                            TextView Home_Garden_environment = findViewById(R.id.Home_Garden_environment);
                            Home_Garden_environment.setText(environment);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            private void setAdditionalDetailsBooks(String auctionId) {
                DatabaseReference antiqueDbRef = FirebaseDatabase.getInstance().getReference("Books").child(auctionId);
                antiqueDbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            String Books = dataSnapshot.child("time_period").getValue(String.class);
                            TextView Book_Type = findViewById(R.id.Book_Type);
                            Book_Type.setText(Books);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


            private void setAdditionalDetailsAntiqur(String auctionId) {
                DatabaseReference antiqueDbRef = FirebaseDatabase.getInstance().getReference("Antiques").child(auctionId);
                antiqueDbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            String timrP = dataSnapshot.child("time_period").getValue(String.class);
                            TextView AN_Time = findViewById(R.id.AN_Time);
                            AN_Time.setText(timrP);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        dveLenier = findViewById(R.id.subCate_Dvd_Movie);
//        dveLenier.removeAllViews();
        //dveLenier.addView();

//        nametext = findViewById(R.id.displayAuct_ItemName);
//        nametext.setText(auctID.toString());


        //Drawer Section
        drawer = findViewById(R.id.DrwerLay);
        navi = (NavigationView) findViewById(R.id.nav_view);
        primTool = findViewById(R.id.primaryActbar);

        setSupportActionBar(primTool);


        navi.bringToFront();
        ActionBarDrawerToggle toggle1 = new ActionBarDrawerToggle(this, drawer, primTool, R.string.OpenDrawerDes, R.string.CloseDrawerDes);
        drawer.addDrawerListener(toggle1);
        toggle1.syncState();




        navi.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.Drawable_myBids:
                        Intent in0 = new Intent(displayAds.this, Draft_Auctions.class);
                        startActivity(in0);
                        break;
                    case R.id.Drawable_myWins:
                        Intent in1 = new Intent(displayAds.this, MyWins.class);
                        startActivity(in1);
                        break;
                    case R.id.Drawable_ViewAuctions:
                        Intent in2 = new Intent(displayAds.this, TabedAuctions.class);
                        startActivity(in2);
                        break;
                    case R.id.Drawable_myAuctions:
                        Intent in3 = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(in3);
                        break;
                    default:
                        Intent in6 = new Intent(getApplicationContext(), MyAuctions.class);
                        startActivity(in6);
                }
                return true;
            }
        });

        toBidButton = findViewById(R.id.displatAuctToBidButton);
        toBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toBidIntent = new Intent(getApplicationContext(), PlaceBid.class);
                toBidIntent.putExtra("AUCTION_ID", auctID);
                startActivity(toBidIntent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setBasicDetails(MyBidsCard myBidsCard) {
        TimeCalculations Tc = new TimeCalculations(myBidsCard.getDuration(), myBidsCard.getEndDate());

        itemName.setText(myBidsCard.getTitle().toString());
        itemDes.setText(myBidsCard.getDescription().toString());
        itemSeller.setText(myBidsCard.getSeller_id().toString());
        //EndingTime.setText(Tc.calcFinalDateTime());
        EndingTime.setText(myBidsCard.getEndDate() + " - " + myBidsCard.getDuration());
        itemMaxBid.setText(String.valueOf(myBidsCard.getMaxBid()) + ".00 Rs");
        itemCurrentBid.setText(String.valueOf(myBidsCard.getStart_Price()) + ".00 Rs");


    }

    private void iamgeRecycler() {

        imgeRecycle.setHasFixedSize(true);
        imgeRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        rcAdapter = new BidSwiperAdapter(bidsimgAdapter);

        imgeRecycle.setAdapter(rcAdapter);
    }
}