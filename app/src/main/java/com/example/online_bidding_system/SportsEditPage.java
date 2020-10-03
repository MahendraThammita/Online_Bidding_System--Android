package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;

public class SportsEditPage extends AppCompatActivity {
    EditText txtTitle,txtPrice,txtContact,txtDescription,Date,time,txtBrand,txtCondition;
    Button PublishLater,Delete,update;

     DatabaseReference DbRef1;
     DatabaseReference homeref;

    Adverticement adverticement;
    String MaxBid;
    long maxid=0;
    String idPrefix="HAS";
    private ImageSwitcher imageIs;
    private Button preBtn,nxBtn, pickImgbtn;
    private ArrayList<Uri> imageUris;
    private static final int PICK_IMAGES_CODE = 1;
    int position = 0;

    TimePicker tp;

    DatePicker dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_edit_page);
        txtTitle = findViewById(R.id.setTitle);
        txtPrice = findViewById(R.id.setPrice);
        PublishLater=findViewById(R.id.publish_later);
        Delete = findViewById(R.id.Delete);
        txtBrand = findViewById(R.id.setBrand);
        txtCondition = findViewById(R.id.setCondition);
        update=findViewById(R.id.Update);
        Date = findViewById(R.id.setDate);
        time=findViewById(R.id.setTime);

        txtContact = findViewById(R.id.setContact);
        txtDescription = findViewById(R.id.setDescription);

        adverticement = new Adverticement();

        Intent retriveIntent = getIntent();
        String AuctName = retriveIntent.getStringExtra("AUCT_ID").toString();


        DbRef1 = FirebaseDatabase.getInstance().getReference().child("Adverticement").child(AuctName);
        DbRef1 = FirebaseDatabase.getInstance().getReference().child("Hobbies&Sports").child(AuctName);
        DbRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    txtTitle.setText(dataSnapshot.child("title").getValue().toString());
                    txtPrice.setText(dataSnapshot.child("price").getValue().toString());
                    txtContact.setText(dataSnapshot.child("contact").getValue().toString());
                    txtDescription.setText(dataSnapshot.child("description").getValue().toString());
                    Date.setText(dataSnapshot.child("date").getValue().toString());
                    time.setText(dataSnapshot.child("duration").getValue().toString());



                }
                else
                    Toast.makeText(getApplicationContext() , "wait a few second!" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});
        homeref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){

                    txtBrand.setText(dataSnapshot.child("brand").getValue().toString());
                    txtCondition.setText(dataSnapshot.child("condition").getValue().toString());

                }
                else
                    Toast.makeText(getApplicationContext() , "Empty" , Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent retriveIntent = getIntent();
                String AuctName = retriveIntent.getStringExtra("AUCT_ID").toString();

                DbRef1 = FirebaseDatabase.getInstance().getReference().child("Adverticement").child(AuctName);
                homeref = FirebaseDatabase.getInstance().getReference().child("Hobbies&Sports").child(AuctName);
                DbRef1.removeValue();
                homeref.removeValue();
                Toast.makeText(getApplicationContext() , "Succesfully Deleted" , Toast.LENGTH_SHORT).show();
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
                DbRef1.child("Adverticement").child(AuctName).child("date").setValue(Date.getText().toString().trim());
                DbRef1.child("Adverticement").child(AuctName).child("duration").setValue(time.getText().toString().trim());
                homeref.child("Hobbies&Sports").child(AuctName).child("brand").setValue(txtBrand.getText().toString().trim());
                homeref.child("Hobbies&Sports").child(AuctName).child("condition").setValue(txtCondition.getText().toString().trim());

                Toast.makeText(getApplicationContext() , "Successfully Updated" , Toast.LENGTH_SHORT).show();
                Intent displayIntent = new Intent(getApplicationContext(), TabedAuctions.class);
                startActivity(displayIntent);

            }
        });
        imageIs = findViewById(R.id.imageIs);
        preBtn = findViewById(R.id.preButton);
        nxBtn =  findViewById(R.id.nextButton);
        pickImgbtn = findViewById(R.id.pickImg);
        imageUris = new ArrayList<>();


        imageIs.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView((getApplicationContext()));
                return imageView;
            }
        });

        pickImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pickImagesIntent();

            }
        });



        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position>0){
                    position--;
                    imageIs.setImageURI(imageUris.get(position));
                }

                else{
                    Toast.makeText(SportsEditPage.this,"Empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

        nxBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(position<imageUris.size()-1){

                    position++;
                    imageIs.setImageURI(imageUris.get(position));
                }

                else{

                    Toast.makeText(SportsEditPage.this,"empty",Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }




    private void pickImagesIntent(){



        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent, PICK_IMAGES_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES_CODE) {

            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) {

                    int cout = data.getClipData().getItemCount();
                    for (int i = 0; i < cout; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imageUris.add(imageUri);
                    }

                    imageIs.setImageURI(imageUris.get(0));
                    position = 0;
                } else {
                    Uri imageUri = data.getData();
                    imageUris.add(imageUri);
                    imageIs.setImageURI(imageUris.get(0));
                    position = 0;
                }
            }

        }
    }}



