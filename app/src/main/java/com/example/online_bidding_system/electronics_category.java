package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class electronics_category extends AppCompatActivity {

    final int REQUEST_EXTERNAL_STORAGE = 100;

    EditText Brand,Condition,ContactNo,Description,Duration,Title,Type,Start_Price;
    Button publishNow,publishLater;
    DatabaseReference fAuth;
    FdeHelper fCat;
    long maxid=0;
    String idPrefix="ELEC";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics_category);


        Title       =   findViewById(R.id.setTitle);
        Brand       =   findViewById(R.id.setBrand);
        Type        =   findViewById(R.id.setType);
        Condition   =   findViewById(R.id.setCondition);
        Start_Price =   findViewById(R.id.setPrize);
        Duration    =   findViewById(R.id.setDuration);
        ContactNo     =   findViewById(R.id.setContact);
        Description =   findViewById(R.id.setDescription);

        publishNow   =      findViewById(R.id.publish_now);


        fCat = new FdeHelper();


        publishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fAuth = FirebaseDatabase.getInstance().getReference().child("DVDandMovies");
                fAuth = FirebaseDatabase.getInstance().getReference().child("Adverticement");

                fAuth.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                            maxid=(dataSnapshot.getChildrenCount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                try {
                    if(TextUtils.isEmpty(Title.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Title Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Type.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Type Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }

                    else if(TextUtils.isEmpty(Condition.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Condition Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Start_Price.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Starting at Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Duration.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Duration Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(ContactNo.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Contact Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Description.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Description Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }


                    else {
                        fCat.setTitle(Title.getText().toString().trim());
                        fCat.setType(Type.getText().toString().trim());
                        fCat.setCondition(Condition.getText().toString().trim());
                        fCat.setStart_Price(Start_Price.getText().toString().trim());
                        fCat.setDuration(Duration.getText().toString().trim());
                        fCat.setContactNo(ContactNo.getText().toString().trim());
                        fCat.setDescription(Description.getText().toString().trim());

                        String strNumber= idPrefix+String.valueOf(maxid+1);
                        fAuth.child(String.valueOf(strNumber)).setValue(fCat);


                        Toast.makeText(getApplicationContext() , "Data Inserted Successfully" , Toast.LENGTH_SHORT).show();
                        clearControl();

                    }
                }
                catch (NumberFormatException err){
                    Toast.makeText(getApplicationContext(), "Invalid Contact No" , Toast.LENGTH_SHORT).show();

                }



            }

            public void clearControl() {
                Title.setText("");
                Type.setText("");
                Condition.setText("");
                Start_Price.setText("");
                Duration.setText("");
                ContactNo.setText("");
                Description.setText("");

            }



        });



    }



}






