package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.HomeCard;
import com.example.online_bidding_system.HelperClasser.BiddingAdapters.TimeCalculations;
import com.example.online_bidding_system.auction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class Antiques_Category extends AppCompatActivity{


    EditText txtTitle,txtPrice,txtDuration,txtContact,txtPeriod,txtDescription;
    Spinner period;
    Button PublishLater,PublishNow;
    DatabaseReference DbRef;
    DatabaseReference DbRef1;
    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabase1;
    private FirebaseDatabase mFirebaseInstance;
    Adverticement adverticement;
    auction add;
    long maxid;
    String MaxBid;
    String idPrefix="AN";
    private ImageSwitcher imageIs;
    private Button preBtn,nxBtn, pickImgbtn;
    private  ArrayList<Uri> imageUris;
    private String AdId;
    private static final int PICK_IMAGES_CODE = 1;
    int position = 0;

     TimePicker tp;
     DatePicker dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antiques__category);

        Spinner spinner = findViewById(R.id.setPeriod);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TimePeriod, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        txtTitle = findViewById(R.id.setTitle);
        txtPrice = findViewById(R.id.setPrice);
        txtContact = findViewById(R.id.setContact);
        dp = findViewById(R.id.setDate);
        tp = findViewById(R.id.setTime);
        period = (Spinner)findViewById(R.id.setPeriod);
        txtDescription = findViewById(R.id.setDescription);
        PublishLater = findViewById(R.id.publish_later);
        PublishNow = findViewById(R.id.publish_now);

        add = new auction();
        adverticement=new  Adverticement();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Adverticement");
        mFirebaseDatabase1 = mFirebaseInstance.getReference("Antiques");


        PublishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbRef = FirebaseDatabase.getInstance().getReference().child("Antiques");
                DbRef1 = FirebaseDatabase.getInstance().getReference().child("Adverticement");
                AdId = mFirebaseDatabase1.push().getKey();
                mFirebaseDatabase.child(AdId).setValue(adverticement);

                mFirebaseDatabase1.child(AdId).setValue(add);
                DbRef.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                            maxid = (dataSnapshot.getChildrenCount());
                        savedata();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

                @RequiresApi(api = Build.VERSION_CODES.O)
                public void savedata(){
                try {
                    if (TextUtils.isEmpty(txtTitle.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Title is Required!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtPrice.getText().toString()))
                        Toast.makeText(getApplicationContext(), " Price Is Required!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtContact.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Contact Number is Required!", Toast.LENGTH_SHORT).show();
                    else {

                        String strTime = tp.getHour() + ":" + tp.getMinute() + ":" + "00";
                        adverticement.setDuration(strTime);

                        // String strDate =  dp.getYear() + "-" + (dp.getMonth() + 1) + "-" + dp.getDayOfMonth();
                        //adverticement.setDate(strDate);

                        int year = dp.getYear();
                        int month = dp.getMonth();
                        int day = dp.getDayOfMonth();

                        Calendar myCal = Calendar.getInstance();
                        myCal.set(year, month, day);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
                        String strDate = dateFormat.format(myCal.getTime());

                        TimeCalculations timeCalculations = new TimeCalculations(strTime, strDate);
                        boolean flag = timeCalculations.isExpired();

                        if (flag == true) {
                            clearControl();
                            Toast.makeText(getApplicationContext(), "Please Enter a valid date", Toast.LENGTH_LONG).show();
                        }

                        else {
                            adverticement.setDate(strDate);
                            adverticement.setTitle(txtTitle.getText().toString().trim());
                            adverticement.setPrice(txtPrice.getText().toString().trim());
                            adverticement.setContact(txtContact.getText().toString().trim());
                            adverticement.setDescription(txtDescription.getText().toString().trim());
                            adverticement.setMaxBid("0");
                            adverticement.setStatus("active");
                            adverticement.setType("Antiques");
                            adverticement.setSeller_ID("CUS1");
                            add.setTime_period(period.getSelectedItem().toString());
                            String strNumber = idPrefix + String.valueOf(maxid + 1);
                            DbRef.child(String.valueOf(strNumber)).setValue(add);
                            DbRef1.child(String.valueOf(strNumber)).setValue(adverticement);
                            Toast.makeText(getApplicationContext(), "Successfully saved", Toast.LENGTH_SHORT).show();
                            clearControl();
                            Intent displayIntent = new Intent(getApplicationContext(), TabedAuctions.class);
                            startActivity(displayIntent);
                        }


                    }



                } catch (NumberFormatException e) {

                    Toast.makeText(getApplicationContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();


                }
            }




            public void clearControl() {
                txtTitle.setText("");
                txtPrice.setText("");
                txtContact.setText("");
                txtDescription.setText("");
            }


        });


        PublishLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbRef = FirebaseDatabase.getInstance().getReference().child("Antiques");
                DbRef1 = FirebaseDatabase.getInstance().getReference().child("Adverticement");
                AdId = mFirebaseDatabase1.push().getKey();
                mFirebaseDatabase.child(AdId).setValue(adverticement);

                mFirebaseDatabase1.child(AdId).setValue(add);
                DbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                            maxid = (dataSnapshot.getChildrenCount());
                        savedata();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            public void savedata(){
                try {
                    if (TextUtils.isEmpty(txtTitle.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Title is Required!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtPrice.getText().toString()))
                        Toast.makeText(getApplicationContext(), " Price Is Required!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtContact.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Contact Number is Required!", Toast.LENGTH_SHORT).show();
                    else {

                        String strTime = tp.getHour() + ":" + tp.getMinute() + ":" + "00";
                        adverticement.setDuration(strTime);

                        // String strDate =  dp.getYear() + "-" + (dp.getMonth() + 1) + "-" + dp.getDayOfMonth();
                        //adverticement.setDate(strDate);

                        int year = dp.getYear();
                        int month = dp.getMonth();
                        int day = dp.getDayOfMonth();

                        Calendar myCal = Calendar.getInstance();
                        myCal.set(year , month , day);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
                        String strDate = dateFormat.format(myCal.getTime());
                        adverticement.setDate(strDate);


                        adverticement.setTitle(txtTitle.getText().toString().trim());
                        adverticement.setPrice(txtPrice.getText().toString().trim());
                        adverticement.setContact(txtContact.getText().toString().trim());
                        adverticement.setDescription(txtDescription.getText().toString().trim());
                        adverticement.setMaxBid("0");
                        adverticement.setStatus("inactive");
                        adverticement.setType("Antiques");
                        adverticement.setSeller_ID("CUS1");
                        add.setTime_period(period.getSelectedItem().toString());
                        String strNumber= idPrefix+String.valueOf(maxid+1);
                        DbRef.child(String.valueOf(strNumber)).setValue(add);
                        DbRef1.child(String.valueOf(strNumber)).setValue(adverticement);
                        Toast.makeText(getApplicationContext(), "Successfully saved", Toast.LENGTH_SHORT).show();
                        clearControl();
                        Intent displayIntent = new Intent(getApplicationContext(), TabedAuctions.class);
                        startActivity(displayIntent);
                    }


                } catch (NumberFormatException e) {

                    Toast.makeText(getApplicationContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();


                }
            }




            public void clearControl() {
                txtTitle.setText("");
                txtPrice.setText("");
                txtContact.setText("");
                txtDescription.setText("");
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
                    Toast.makeText(Antiques_Category.this,"Empty",Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(Antiques_Category.this,"empty",Toast.LENGTH_SHORT).show();
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

