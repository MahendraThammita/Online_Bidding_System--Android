package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePage extends AppCompatActivity {

    Button home;
    Button bids;
    Button msg;
    ImageButton antique, book;
    Button profBtn;
    ImageButton addNew;
    ViewFlipper flipper;
    DatabaseReference DBRef;
    TextView title;


    ListView listView;
    SearchView searchView;
    DatabaseReference dbRef;
    List<HomeCard> HomeCards;
    HomeAdapter singleCard;


    public HomePage() {
    }


    public void FilterOnClick(Query query){

        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String Title = (ds.child("title").getValue().toString());
                    int MaxBid =  Integer.parseInt(ds.child("maxBid").getValue().toString());
                    String AucID = ds.getKey().toString();
                    String Duration = ds.child("duration").getValue().toString();
                    String endDate = ds.child("date").getValue().toString();


                    LocalDate datPart = LocalDate.parse(endDate);
                    LocalTime timePart = LocalTime.parse(Duration);
                    LocalDateTime contactDate = LocalDateTime.of(datPart , timePart);
                    String finalDate = contactDate.toString();

                    LocalDateTime currenttime = LocalDateTime.now();
                    String time = currenttime.toString();

                    Log.i("finalDate" , "ADD No : " + finalDate);
                    Log.i("currentTime" , "ADD No : " + time);


                    long minutes = ChronoUnit.MINUTES.between(currenttime , contactDate);

                    String MinDifference = Long.toString(minutes);

                    //LocalDateTime calculatedTime = LocalDateTime.MIN.plusMinutes(minutes);
                    String strCalculatedStrhOUR = Long.toString(minutes/60);
                    String strCalculatedStrhMin = Long.toString(minutes%60);

                    //Change status if time exceed

                    int EndMin = Integer.parseInt(strCalculatedStrhMin);
                    //int EndHr = Integer.parseInt(strCalculatedStrhOUR);


                    if (EndMin < 0 ){

                        DBRef = FirebaseDatabase.getInstance().getReference();
                        DBRef.child("Adverticement").child(AucID).child("status").setValue("Ended");
                        //DBRef.child("Student/std1/add").setValue(txtAdd.getText().toString().trim());

                    }


                    Log.i("Difference" , "ADD No : " + MinDifference);
                    Log.i("Difference" , "difference in calculated format : " + strCalculatedStrhOUR + ":" + strCalculatedStrhMin);

                    String duration = (strCalculatedStrhOUR +" hr " + strCalculatedStrhMin + " min" );

                    HomeCard my_Bid = new HomeCard(AucID , Title, MaxBid,duration);
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
        antique = findViewById(R.id.btnAntique);
        book = findViewById(R.id.btnBook);


       /* home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(), Antiques_Edit.class);
                startActivity(homeIntent);
            }
        });

        bids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(), Other_category.class);
                startActivity(homeIntent);
            }
        });


        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent msgintent = new Intent(getApplicationContext(), OtherEditpage.class);
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
*/
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addIntent = new Intent(getApplicationContext(), main_categories.class);
                startActivity(addIntent);

            }
        });




        listView = this.findViewById(R.id.HomeCardsList);
        HomeCards = new ArrayList<>();
        Query FilterHomeAds = FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("status").equalTo("inactive");
        final Query FilterAntiqueAds =  FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("status").equalTo("Ended");

        FilterHomeAds.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
           @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    String Title = (ds.child("title").getValue().toString());
                    int MaxBid =  Integer.parseInt(ds.child("maxBid").getValue().toString());
                    String AucID = ds.getKey().toString();
                    String Duration = ds.child("duration").getValue().toString();
                    String endDate = ds.child("date").getValue().toString();


                    LocalDate datPart = LocalDate.parse(endDate);
                    LocalTime timePart = LocalTime.parse(Duration);
                    LocalDateTime contactDate = LocalDateTime.of(datPart , timePart);
                    String finalDate = contactDate.toString();

                    LocalDateTime currenttime = LocalDateTime.now();
                    String time = currenttime.toString();

                    Log.i("finalDate" , "ADD No : " + finalDate);
                    Log.i("currentTime" , "ADD No : " + time);


                    long minutes = ChronoUnit.MINUTES.between(currenttime , contactDate);

                    String MinDifference = Long.toString(minutes);

                    //LocalDateTime calculatedTime = LocalDateTime.MIN.plusMinutes(minutes);
                    String strCalculatedStrhOUR = Long.toString(minutes/60);
                    String strCalculatedStrhMin = Long.toString(minutes%60);

                    //Change status if time exceed

                    int EndMin = Integer.parseInt(String.valueOf(minutes));
                    //int EndHr = Integer.parseInt(strCalculatedStrhOUR);


                    if (EndMin < 0 ){

                        DBRef = FirebaseDatabase.getInstance().getReference();
                        DBRef.child("Adverticement").child(AucID).child("status").setValue("Ended");

                    }


                    Log.i("Difference" , "ADD No : " + MinDifference);
                    Log.i("Difference" , "difference in calculated format : " + strCalculatedStrhOUR + ":" + strCalculatedStrhMin);

                    String duration = (strCalculatedStrhOUR +" hr " + strCalculatedStrhMin + " min" );

                    HomeCard my_Bid = new HomeCard(AucID , Title, MaxBid,duration);
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




       antique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeCards.clear();
                FilterOnClick(FilterAntiqueAds);

            }
        });


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeCards.clear();
                FilterOnClick(FilterAntiqueAds);

            }
        });






        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String AuctionId = HomeCards.get(i).getAuctId();
                Intent showPAgeIntent = new Intent(getApplicationContext() , displayAds.class);
                showPAgeIntent.putExtra("BidId" , AuctionId);
                listView.getContext().startActivity(showPAgeIntent);

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


