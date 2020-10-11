package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.HomeAdapter;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.HomeCard;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyAdapter;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.MyBidsCard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
    ImageButton antique, book,fashion,elec,hobby,handmade,garden,dvd;
    Button profBtn;
    ImageButton addNew;
    ViewFlipper flipper;
    DatabaseReference DBRef;
    TextView title;
    ImageView homeAdImg;


    ListView listView;
    androidx.appcompat.widget.SearchView searchView;
    DatabaseReference dbRef;
    List<HomeCard> HomeCards;
    HomeAdapter singleCard;

    private DrawerLayout drawer;
    private NavigationView navi;
    private Toolbar primTool;
    private String userid;
    private StorageReference AdStorageRef;


    SharedPreferences sp;
    private String uID;
//    private static final String spn = "mypref";
//    private static final String kn = "name";
//    private static final String ke = "name";

    SharedPreferences shareP;


    public HomePage() {
    }




    public void FilterOnClick(Query query){

        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    String Title = (ds.child("title").getValue().toString());
                    String MaxBid = (ds.child("maxBid").getValue().toString());
                    String AucID = ds.getKey().toString();
                    String Duration = ds.child("duration").getValue().toString();
                    String endDate = ds.child("date").getValue().toString();
                //    String AdImage = ds.child("Img").getValue().toString();


                    LocalDate datPart = LocalDate.parse(endDate);
                    LocalTime timePart = LocalTime.parse(Duration);
                    LocalDateTime contactDate = LocalDateTime.of(datPart , timePart);
                    String finalDate = contactDate.toString();

                    LocalDateTime currenttime = LocalDateTime.now();
                    String time = currenttime.toString();




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


                    String duration = (strCalculatedStrhOUR +" hr " + strCalculatedStrhMin + " min" );

                    HomeCard ad = new HomeCard(AucID, Title, MaxBid,duration);

                    if(EndMin > 0) {
                        HomeCards.add(ad);
                    }
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


        shareP = getSharedPreferences("sharedPrefName", Context.MODE_PRIVATE);
        String logEmail = shareP.getString("UserEmail" , null);
        uID = shareP.getString("USER_ID" , null);
        Log.i("Loged" , "Loged By : " + logEmail);
        Toast.makeText(getApplicationContext() , "Logged as" + logEmail , Toast.LENGTH_SHORT).show();
        if(uID == null){
            Intent toLogin = new Intent(getApplicationContext() , LogIn_Page.class);
            startActivity(toLogin);
        }


//
//        sp  =   getSharedPreferences(spn,MODE_PRIVATE);
//        final String name = sp.getString(kn,null);


       AdStorageRef = FirebaseStorage.getInstance().getReference("AntiqueImages");




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
        homeAdImg = findViewById(R.id.HomeAdImg);

        addNew = findViewById(R.id.addNew);
        antique = findViewById(R.id.btnAntique);
        book = findViewById(R.id.btnBook);
        elec = findViewById(R.id.btnElec);
        fashion = findViewById(R.id.btnFashion);
        hobby = findViewById(R.id.btnHobby);
        handmade = findViewById(R.id.btnHandmade);
        garden = findViewById(R.id.btnGarden);
        dvd = findViewById(R.id.btnMovie);



        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
              userid = uID;
                if (userid == null){

                    Intent redirectIntent = new Intent(getApplicationContext(), RegistrationPage.class);
                    startActivity(redirectIntent);
                }
                else {
                    Intent addIntent = new Intent(getApplicationContext(), main_categories.class);
                    startActivity(addIntent);
                }
            }
        });




        listView = this.findViewById(R.id.HomeCardsList);
        HomeCards = new ArrayList<>();
        Query FilterHomeAds = FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("status").equalTo("active");
        final Query FilterAntiqueAds =  FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("type").equalTo("Antiques");
        final Query FilterElectricsAds =  FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("type").equalTo("Electronics");
        final Query FilterBookAds =  FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("type").equalTo("Books");
        final Query FilterDVDAds =  FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("type").equalTo("DVDandMovies");
        final Query FilterFashionAds =  FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("type").equalTo("FashionAndDesign");
        final Query FilterHandmadeAds =  FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("type").equalTo("HandMades");
        final Query FilterHobbyAds =  FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("type").equalTo("SportsAndHobbies");
        final Query FilterGardenAds =  FirebaseDatabase.getInstance().getReference("Adverticement").orderByChild("type").equalTo("HomeAndGarden");



        FilterHomeAds.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
           @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    String Title = (ds.child("title").getValue().toString());
                    String MaxBid =  (ds.child("maxBid").getValue().toString());
                    String AucID = ds.getKey().toString();
                    String Duration = ds.child("duration").getValue().toString();
                    String endDate = ds.child("date").getValue().toString();
                    String AdImage = ds.child("Img").getValue().toString();


           /*         AdStorageRef.child(AdImage).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Picasso.get()
                                    .load(uri)
                                    .placeholder(R.drawable.movie)
                                    .fit()
                                    .centerCrop()
                                    .into(homeAdImg);
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });*/

                    LocalDate datPart = LocalDate.parse(endDate);
                    LocalTime timePart = LocalTime.parse(Duration);
                    LocalDateTime contactDate = LocalDateTime.of(datPart , timePart);
                    String finalDate = contactDate.toString();

                    LocalDateTime currenttime = LocalDateTime.now();
                    String time = currenttime.toString();


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

                    String duration = (strCalculatedStrhOUR +" hr " + strCalculatedStrhMin + " min" );

                    HomeCard ad = new HomeCard(AucID, Title, MaxBid, duration, AdImage);
                    HomeCards.add(ad);
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



        drawer = findViewById(R.id.DrwerLay);
        navi = (NavigationView) findViewById(R.id.nav_view);
        primTool = findViewById(R.id.profActionbar);

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
                        Intent in0 = new Intent(HomePage.this , TabedAuctions.class);
                        startActivity(in0);
                        break;
                    case R.id.Drawable_myWins:
                        Intent in1 = new Intent(HomePage.this , LogIn_Page.class);
                        startActivity(in1);
                        break;
                    case R.id.Drawable_ViewAuctions:
                        Intent in2 = new Intent(HomePage.this ,Edit_User.class);
                        startActivity(in2);
                        break;
                    case R.id.Drawable_myAuctions:
                        Intent in3 = new Intent(getApplicationContext() , Draft_Auctions.class);
                        startActivity(in3);
                        break;
                    case R.id.Drawable_logout:
                        Intent in4 = new Intent(getApplicationContext() , Antiques_Category.class);
                        startActivity(in4);
                        break;
                    default:
                        Intent in6 = new Intent(getApplicationContext() , MyAuctions.class);
                        startActivity(in6);
                }
                return true;
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
                FilterOnClick(FilterBookAds);

            }
        });

        handmade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeCards.clear();
                FilterOnClick(FilterHandmadeAds);

            }
        });

        elec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeCards.clear();
                FilterOnClick(FilterElectricsAds);

            }
        });

        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeCards.clear();
                FilterOnClick(FilterFashionAds);

            }
        });


        dvd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeCards.clear();
                FilterOnClick(FilterDVDAds);

            }
        });

        garden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeCards.clear();
                FilterOnClick(FilterGardenAds);

            }
        });

        hobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeCards.clear();
                FilterOnClick(FilterHobbyAds);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_top_nav , menu);
        return true;
    }

}


