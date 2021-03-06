package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeAndGardenEditpage extends AppCompatActivity {
    EditText txtTitle,txtPrice,txtContact,txtDescription,txtEnvironment,time,date;

    Button PublishNow,Delete,update;

    private DatabaseReference DbRef1;
    private DatabaseReference homeref;

    Adverticement adverticement;
    String MaxBid;
    long maxid=0;
    String idPrefix="HAG";
    private ImageSwitcher imageIs;
    private Button preBtn,nxBtn, pickImgbtn;
    private ArrayList<Uri> imageUris;
    private static final int PICK_IMAGES_CODE = 1;
    int position = 0;
    //Timepicker object
    TimePicker tp;
    //Datapicker object
    DatePicker dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_and_garden_editpage);
        txtTitle = findViewById(R.id.setTitle);
        txtPrice = findViewById(R.id.setPrice);
        Delete = findViewById(R.id.Delete);
        update = findViewById(R.id.Update);
        date=findViewById(R.id.setDate);
        time=findViewById(R.id.setTime);
        txtContact = findViewById(R.id.setContact);
        txtDescription = findViewById(R.id.setDescription);
        txtEnvironment= findViewById(R.id.setEnvironment);

        adverticement = new Adverticement();
        Intent retriveIntent = getIntent();
        final String AuctName = retriveIntent.getStringExtra("AUCT_ID").toString();

        DbRef1 = FirebaseDatabase.getInstance().getReference().child("Adverticement").child(AuctName);
        homeref= FirebaseDatabase.getInstance().getReference().child("Home&Garden").child(AuctName);
        DbRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    txtTitle.setText(dataSnapshot.child("title").getValue().toString());
                    txtPrice.setText(dataSnapshot.child("price").getValue().toString());
                    txtContact.setText(dataSnapshot.child("contact").getValue().toString());
                    date.setText(dataSnapshot.child("date").getValue().toString());
                    time.setText(dataSnapshot.child("duration").getValue().toString());
                    //txtEnvironment.setText(dataSnapshot.child("environment").getValue().toString());
                    txtDescription.setText(dataSnapshot.child("description").getValue().toString());
                    //System.out.println(homeref.child(dataSnapshot.getKey()));
                    //txtEnvironment.setText(homeref .child("environment").getValue().toString());

//                    homeref.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if(dataSnapshot.hasChildren()){
//
//                                txtEnvironment.setText(dataSnapshot.child("Environment").getValue().toString());
//                            }
//                            else
//                                Toast.makeText(getApplicationContext() , "wait few second" , Toast.LENGTH_SHORT).show();
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }});
                }
                else
                    Toast.makeText(getApplicationContext() , "wait few second" , Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});
        homeref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){


        txtEnvironment.setText(dataSnapshot.child("environment").getValue().toString());

                            }
                           else
                                Toast.makeText(getApplicationContext() , "wait few second" , Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }});

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent retriveIntent = getIntent();
                String AuctName = retriveIntent.getStringExtra("AUCT_ID").toString();

                DbRef1 = FirebaseDatabase.getInstance().getReference().child("Adverticement").child(AuctName);
                homeref = FirebaseDatabase.getInstance().getReference().child("Home&Garden").child(AuctName);
                DbRef1.removeValue();
                homeref.removeValue();
                Toast.makeText(getApplicationContext() , "Succesfully Deleated" , Toast.LENGTH_SHORT).show();
                Intent displayIntent = new Intent(getApplicationContext(), TabedAuctions.class);
                startActivity(displayIntent);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent retriveIntent = getIntent();
                String AuctName = retriveIntent.getStringExtra("AUCT_ID").toString();

                DbRef1 = FirebaseDatabase.getInstance().getReference();
                homeref = FirebaseDatabase.getInstance().getReference();
                DbRef1.child("Adverticement").child(AuctName).child("title").setValue(txtTitle.getText().toString().trim());
                DbRef1.child("Adverticement").child(AuctName).child("contact").setValue(txtContact.getText().toString().trim());
                DbRef1.child("Adverticement").child(AuctName).child("price").setValue(txtPrice.getText().toString().trim());
                DbRef1.child("Adverticement").child(AuctName).child("description").setValue(txtDescription.getText().toString().trim());
                DbRef1.child("Adverticement").child(AuctName).child("date").setValue(date.getText().toString().trim());
                DbRef1.child("Adverticement").child(AuctName).child("duration").setValue(time.getText().toString().trim());
                homeref.child("Home&Garden").child(AuctName).child("environment").setValue(txtEnvironment.getText().toString().trim());
                Toast.makeText(getApplicationContext() , "Successfully Updated" , Toast.LENGTH_SHORT).show();
                Intent displayIntent = new Intent(getApplicationContext(), TabedAuctions.class);
                startActivity(displayIntent);

            }
        });
        }
}