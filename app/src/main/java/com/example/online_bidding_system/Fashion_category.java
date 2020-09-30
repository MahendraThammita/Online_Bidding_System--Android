package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Fashion_category extends AppCompatActivity {

    final int REQUEST_EXTERNAL_STORAGE = 100;

    EditText Brand,Condition,Material,Size,ContactNo,Description,Duration,Title,Type,Start_Price;
    Button publishNow,publishLater;
    DatabaseReference fAuth;
    DatabaseReference fAuth1;
    FdeHelper fCat;
    long maxid=0;
    String idPrefix="Fa";
    private String userId;

    private ImageSwitcher imageIs;
    private  ArrayList<Uri> imageUris;
    private static final int PICK_IMAGES_CODE = 1;
    int position = 0;

    private Button preBtn,nxBtn, pickImgbtn;
    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabase1;
    private FirebaseDatabase mFirebaseInstance;

    //Timepicker object
    TimePicker tp;
    //Datapicker object
    DatePicker dp;
    Adverticement adverticement;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics_category);


        Brand        =      findViewById(R.id.setBrand);
        Material     =      findViewById(R.id.setMaterials);
        Condition    =      findViewById(R.id.setCondition);
        Size         =      findViewById(R.id.setSize);
        ContactNo    =      findViewById(R.id.setContact);
        Description  =      findViewById(R.id.setDescription);
        Title        =      findViewById(R.id.setTitle);
        Start_Price  =      findViewById(R.id.setPrice);

        //ged datapicker value
        dp = findViewById(R.id.setDate);
        //get Timepicker value
        tp = findViewById(R.id.setTime);



        fCat = new FdeHelper();
        adverticement= new  Adverticement();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Adverticement");
        mFirebaseDatabase1 = mFirebaseInstance.getReference("Electronics");


        publishNow   =      findViewById(R.id.publish_now);


        publishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fAuth = FirebaseDatabase.getInstance().getReference().child("FashionAndDesign");
                fAuth1 = FirebaseDatabase.getInstance().getReference().child("Adverticement");

                userId = mFirebaseDatabase1.push().getKey();

                mFirebaseDatabase.child(userId).setValue(adverticement);

                mFirebaseDatabase1.child(userId).setValue(fCat);


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
                    if(TextUtils.isEmpty(Brand.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Brand Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Material.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Material Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Condition.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Condition Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }

                    else if(TextUtils.isEmpty(ContactNo.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Contact Number Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Description.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Description Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }

                    else if(TextUtils.isEmpty(Title.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Title Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }

                    else if(TextUtils.isEmpty(Start_Price.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Start Price Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }


                    else {
                        fCat.setBrand(Brand.getText().toString().trim());
                        fCat.setMaterial(Material.getText().toString().trim());
                        fCat.setCondition(Condition.getText().toString().trim());
                        adverticement.setContact(ContactNo.getText().toString().trim());
                        adverticement.setDescription(Description.getText().toString().trim());
                        adverticement.setTitle(Title.getText().toString().trim());
                        adverticement.setPrice(Start_Price.getText().toString().trim());

                        //set timepicker value
                        String strTime = tp.getHour() + ":" + tp.getMinute();
                        adverticement.setDuration(strTime);
                        //set datapicker value
                        String strDate =  dp.getYear() + "-" + (dp.getMonth() + 1) + "-" + dp.getDayOfMonth();
                        adverticement.setDate(strDate);

                        adverticement.setMaxBid("0");
                        adverticement.setStatus("inactive");
                        adverticement.setType("Electronics");


                        String strNumber= idPrefix+String.valueOf(maxid+1);
                        fAuth.child(String.valueOf(strNumber)).setValue(fCat);
                        fAuth1.child(String.valueOf(strNumber)).setValue(adverticement);

                        Toast.makeText(getApplicationContext() , "Data Inserted Successfully" , Toast.LENGTH_SHORT).show();
                        clearControl();

                    }
                }
                catch (NumberFormatException err){
                    Toast.makeText(getApplicationContext(), "Invalid Contact No" , Toast.LENGTH_SHORT).show();

                }



            }

            public void clearControl() {
                Brand.setText("");
                Material.setText("");
                Condition.setText("");
                ContactNo.setText("");
                Description.setText("");
                Title.setText("");
                Start_Price.setText("");
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
                    Toast.makeText(Fashion_category.this,"Empty",Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(Fashion_category.this,"empty",Toast.LENGTH_SHORT).show();
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
