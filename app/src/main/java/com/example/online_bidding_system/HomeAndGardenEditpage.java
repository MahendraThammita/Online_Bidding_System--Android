package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    EditText txtTitle,txtPrice,txtContact,txtDescription,txtEnvironment,date;

    Button PublishNow,Delete;
    DatabaseReference DbRef1,homeref;

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
        date=findViewById(R.id.setDate);
        txtContact = findViewById(R.id.setContact);
        txtDescription = findViewById(R.id.setDescription);
        txtEnvironment= findViewById(R.id.setEnvironment);

        adverticement = new Adverticement();
        DbRef1 = FirebaseDatabase.getInstance().getReference().child("Adverticement").child("HAG6");
        homeref= FirebaseDatabase.getInstance().getReference().child("HomeAndGarden").child("HAG6");
        DbRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    txtTitle.setText(dataSnapshot.child("title").getValue().toString());
                    txtPrice.setText(dataSnapshot.child("price").getValue().toString());
                    txtContact.setText(dataSnapshot.child("contact").getValue().toString());
                    date.setText(dataSnapshot.child("date").getValue().toString());
                    txtDescription.setText(dataSnapshot.child("description").getValue().toString());
                    homeref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChildren()){

                                txtEnvironment.setText(dataSnapshot.child("Environment").getValue().toString());
                            }
                            else
                                Toast.makeText(getApplicationContext() , "Cannot Find Add" , Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }});
                }
                else
                    Toast.makeText(getApplicationContext() , "Cannot Find Add" , Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbRef1 = FirebaseDatabase.getInstance().getReference().child("Adverticement").child("HAG1");
                DbRef1.removeValue();
                Toast.makeText(getApplicationContext() , "Succesfully Deleated" , Toast.LENGTH_SHORT).show();
            }
        });

        }
}