package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeAndGarden extends AppCompatActivity {

    EditText txtTitle,txtPrice,txtDuration,txtContact,txtEnvironment,txtDescription;
    Button PublishNow;
    DatabaseReference DbRef;
    HomeItem homeitem;
    long maxid=0;
    String idPrefix="HAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_and_garden);

        txtTitle = findViewById(R.id.setTitle);
        txtPrice = findViewById(R.id.setPrice);
        txtDuration = findViewById(R.id.setDuration);
        txtContact = findViewById(R.id.setContact);
        txtEnvironment = findViewById(R.id.setEnvironment);
        txtDescription = findViewById(R.id.setDescription);
        PublishNow = findViewById(R.id.publish_now);

        homeitem = new HomeItem();


        PublishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbRef = FirebaseDatabase.getInstance().getReference().child("Home&Garden");
                DbRef = FirebaseDatabase.getInstance().getReference().child("Adverticement");

                DbRef.addValueEventListener(new ValueEventListener() {
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
                    if (TextUtils.isEmpty(txtTitle.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Your Title is Required!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtPrice.getText().toString()))
                        Toast.makeText(getApplicationContext(), " NIC Price Is Required!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtDuration.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Duration is Required!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtContact.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Contact Number is Required!", Toast.LENGTH_SHORT).show();
                    else {
                        homeitem.setTitle(txtTitle.getText().toString().trim());
                        homeitem.setPrice(txtPrice.getText().toString().trim());
                        homeitem.setDuration(txtDuration.getText().toString().trim());
                        homeitem.setContact(txtContact.getText().toString().trim());
                        homeitem.setEnvironment(txtEnvironment.getText().toString().trim());
                        homeitem.setDescription(txtDescription.getText().toString().trim());
                        // DbRef.child("user").setValue(user);
                        String strNumber= idPrefix+String.valueOf(maxid+1);
                        DbRef.child(String.valueOf(strNumber)).setValue(homeitem);
                        Toast.makeText(getApplicationContext(), "Successfully Published", Toast.LENGTH_SHORT).show();
                        clearControl();

                    }


                } catch (NumberFormatException e) {

                    Toast.makeText(getApplicationContext(), " wrong Inserted", Toast.LENGTH_SHORT).show();


                }
            }


            public void clearControl() {
                txtTitle.setText("");
                txtPrice.setText("");
                txtDuration.setText("");
                txtContact.setText("");
                txtEnvironment.setText("");
                txtDescription.setText("");
            }


        });}}









