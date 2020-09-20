package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;


public class myBids extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    ListView lv;
    DrawerLayout drawer;
    NavigationView navi;
    Toolbar primTool;
    DatabaseReference DBRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bids);


        drawer = findViewById(R.id.DrwerLay);
        navi = (NavigationView) findViewById(R.id.nav_view);
        primTool = findViewById(R.id.primaryActbar);

        setSupportActionBar(primTool);


        navi.bringToFront();
        ActionBarDrawerToggle toggle1 = new ActionBarDrawerToggle(this , drawer , primTool , R.string.OpenDrawerDes , R.string.CloseDrawerDes);
        drawer.addDrawerListener(toggle1);
        toggle1.syncState();


        navi.setNavigationItemSelectedListener(this);


        navi.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.Drawable_myBids:
                        Toast.makeText(myBids.this , "You are in My Bids page" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Drawable_myWins:
                        Intent in1 = new Intent(myBids.this , MyWins.class);
                        startActivity(in1);
                        break;
                    case R.id.Drawable_ViewAuctions:
                        Intent in2 = new Intent(myBids.this ,MyAuctions.class);
                        startActivity(in2);
                        break;
                    case R.id.Drawable_myAuctions:
                        Intent in3 = new Intent(getApplicationContext() , PlaceBid.class);
                        startActivity(in3);
                        break;
                    default:
                        Intent in6 = new Intent(getApplicationContext() , MyAuctions.class);
                        startActivity(in6);
                }
                return true;
            }
        });







        String items[] = {"Item Name 1" , "Item Name 2" , "Item Name 3" , "Item Name 4" , "Item Name 5" , "Item Name 6" , "Item Name 7"};

        ArrayAdapter arradpt = new ArrayAdapter<String>(this,R.layout.my_bid_card , R.id.myBidCardTitle, items);

        lv = findViewById(R.id.myBidsList);

        lv.setAdapter(arradpt);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent in = new Intent(myBids.this , main_categories.class);
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int i, long l) {

                startActivity(in);
            }
       });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
//        switch (menuItem.getItemId()){
//            case R.id.Drawable_myBids:
//                break;
//            case R.id.Drawable_myWins:
//                Intent in1 = new Intent(this , MyWins.class);
//                startActivity(in1);
//                break;
//            case R.id.Drawable_ViewAuctions:
//                Intent in2 = new Intent(this , HomePage.class);
//                startActivity(in2);
//                break;
//        }
//        return true;
//    }






}