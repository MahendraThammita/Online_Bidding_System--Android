package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.ArrayList;

public class Books_Edit extends AppCompatActivity {

    final int REQUEST_EXTERNAL_STORAGE = 100;

    EditText txtTitle,txtPrice,txtTime,txtDate,txtContact,txtType,txtDescription;
    Button PublishNow, update, delete;
    DatabaseReference DbRef;
    DatabaseReference DbRef1;
    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabase1;
    private FirebaseDatabase mFirebaseInstance;
    Adverticement adverticement;
    auction book;
    long maxid=0;
    String idPrefix="BK";
    private ImageSwitcher imageIs;
    private Button preBtn,nxBtn, pickImgbtn;
    private  ArrayList<Uri> imageUris;
    private String userId;
    private static final int PICK_IMAGES_CODE = 1;
    int position = 0;

    TimePicker tp;
    DatePicker dp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_edit);


        txtTitle = findViewById(R.id.setTitle);
        txtPrice = findViewById(R.id.setPrice);
        txtDate = findViewById(R.id.setDate);
        txtTime = findViewById(R.id.setTime);
        txtContact = findViewById(R.id.setContact);
        txtType = findViewById(R.id.setType);
        txtDescription = findViewById(R.id.setDescription);
        PublishNow = findViewById(R.id.publish_now);
        update = findViewById(R.id.Update);
        delete =  findViewById(R.id.Delete);

        book = new auction();
        adverticement=new  Adverticement();

        Intent retriveIntent = getIntent();
        String AuctName = retriveIntent.getStringExtra("AUCT_ID").toString();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Adverticement");
        mFirebaseDatabase1 = mFirebaseInstance.getReference("Books");


        DbRef = FirebaseDatabase.getInstance().getReference().child("Adverticement").child(AuctName);
        DbRef1 = FirebaseDatabase.getInstance().getReference().child("Books").child(AuctName);
        DbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    txtTitle.setText(dataSnapshot.child("title").getValue().toString());
                    txtContact.setText(dataSnapshot.child("contact").getValue().toString());
                    txtPrice.setText(dataSnapshot.child("price").getValue().toString());
                    txtDescription.setText(dataSnapshot.child("description").getValue().toString());
                    txtDate.setText(dataSnapshot.child("date").getValue().toString());
                    txtTime.setText(dataSnapshot.child("duration").getValue().toString());


                }
                else
                    Toast.makeText(getApplicationContext() , "Empty" , Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DbRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){

                    txtType.setText(dataSnapshot.child("type").getValue().toString());

                }
                else
                    Toast.makeText(getApplicationContext() , "Empty" , Toast.LENGTH_SHORT).show();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent retriveIntent = getIntent();
                String AuctName = retriveIntent.getStringExtra("AUCT_ID").toString();

                DbRef = FirebaseDatabase.getInstance().getReference().child("Adverticement").child(AuctName);
                DbRef1 = FirebaseDatabase.getInstance().getReference().child("Books").child(AuctName);
                DbRef.removeValue();
                DbRef1.removeValue();
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

                DbRef = FirebaseDatabase.getInstance().getReference();
                DbRef1 = FirebaseDatabase.getInstance().getReference();
                DbRef.child("Adverticement").child(AuctName).child("title").setValue(txtTitle.getText().toString().trim());
                DbRef.child("Adverticement").child(AuctName).child("contact").setValue(txtContact.getText().toString().trim());
                DbRef.child("Adverticement").child(AuctName).child("price").setValue(txtPrice.getText().toString().trim());
                DbRef.child("Adverticement").child(AuctName).child("description").setValue(txtDescription.getText().toString().trim());
                DbRef.child("Adverticement").child(AuctName).child("date").setValue(txtDate.getText().toString().trim());
                DbRef.child("Adverticement").child(AuctName).child("duration").setValue(txtTime.getText().toString().trim());
                DbRef1.child("Books").child(AuctName).child("type").setValue(txtType.getText().toString().trim());
                Toast.makeText(getApplicationContext() , "Successfully Updated" , Toast.LENGTH_SHORT).show();
                Intent displayIntent = new Intent(getApplicationContext(), TabedAuctions.class);
                startActivity(displayIntent);

            }
        });


        PublishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent retriveIntent = getIntent();
                String AuctName = retriveIntent.getStringExtra("AUCT_ID").toString();

                DbRef = FirebaseDatabase.getInstance().getReference();
                DbRef.child("Adverticement").child(AuctName).child("status").setValue("active");

                Toast.makeText(getApplicationContext() , "Your Ad is now on Live" , Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Books_Edit.this,"Empty",Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(Books_Edit.this,"empty",Toast.LENGTH_SHORT).show();
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
        if(requestCode == PICK_IMAGES_CODE){

            if(resultCode == Activity.RESULT_OK){
                if(data.getClipData() != null){

                    int cout  = data.getClipData().getItemCount();
                    for(int i=0; i<cout; i++){
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imageUris.add(imageUri);
                    }

                    imageIs.setImageURI(imageUris.get(0));
                    position = 0;
                }

                else{
                    Uri imageUri = data.getData();
                    imageUris.add(imageUri);
                    imageIs.setImageURI(imageUris.get(0));
                    position = 0;
                }
            }
        }
    }



}

