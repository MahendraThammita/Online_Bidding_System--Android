package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
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

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.TimeCalculations;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class electronics_category extends AppCompatActivity {

    final int REQUEST_EXTERNAL_STORAGE = 100;

    EditText Brand,Condition,Size,ContactNo,Description,Title,Start_Price;
    Button publishNow,publishLater;
    DatabaseReference fAuth;
    DatabaseReference fAuth1;
    FdeHelper fCat;
    long maxid=0;
    String idPrefix="ELECT";
    private String userId;

    private ImageSwitcher imageIs;
    private Button preBtn, nxBtn, pickImgbtn;
    private ArrayList<Uri> imageUris;
    private ArrayList<String> filenameList;
    private HashMap<String, String> hashMap;
    private String AdId;
    private static final int PICK_IMAGES_CODE = 3;
    int position = 0;
    int noOfImages = 0;



    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabase1;
    private FirebaseDatabase mFirebaseInstance;

    //Timepicker object
    TimePicker tp;
    //Datapicker object
    DatePicker dp;
    Adverticement adverticement;

    StorageReference fbStorageRef;

    SharedPreferences sp;
    private String uID;
    SharedPreferences shareP;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics_category);


        shareP = getSharedPreferences("sharedPrefName", Context.MODE_PRIVATE);
        String logEmail = shareP.getString("UserEmail" , null);
        uID = shareP.getString("USER_ID" , null);


        Brand        =      findViewById(R.id.setBrand);
        Condition    =      findViewById(R.id.setCondition);
        Size         =      findViewById(R.id.setSize);
        ContactNo    =      findViewById(R.id.setContact);
        Description  =      findViewById(R.id.setDescription);
        Title        =      findViewById(R.id.setTitle);
        Start_Price  =      findViewById(R.id.setPrice);

        //DatePicker Value
        dp = findViewById(R.id.setDate);

        //TimePicker Value
        tp = findViewById(R.id.setTime);

        Description  =      findViewById(R.id.setDescription);
        publishLater =      findViewById(R.id.publish_later);
        publishNow   =      findViewById(R.id.publish_now);
        pickImgbtn =        findViewById(R.id.pickImg);




        fCat = new FdeHelper();
        adverticement= new  Adverticement();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Adverticement");
        mFirebaseDatabase1 = mFirebaseInstance.getReference("Electronics");




        fbStorageRef = FirebaseStorage.getInstance().getReference().child("AntiqueImages");


        //Setting image picker intents
        pickImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgsIntent = new Intent();
                imgsIntent.setType("image/*");
                imgsIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                imgsIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imgsIntent, "Select Multiple Images"), PICK_IMAGES_CODE);
            }
        });


        publishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth = FirebaseDatabase.getInstance().getReference().child("Electronics");
                fAuth1 = FirebaseDatabase.getInstance().getReference().child("Adverticement");
                userId = mFirebaseDatabase1.push().getKey();
                mFirebaseDatabase.child(userId).setValue(adverticement);
                mFirebaseDatabase1.child(userId).setValue(fCat);

                fAuth.addValueEventListener(new ValueEventListener() {
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
                    if (TextUtils.isEmpty(Title.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Title is Required!", Toast.LENGTH_LONG).show();
                    else if (TextUtils.isEmpty(Start_Price.getText().toString()))
                        Toast.makeText(getApplicationContext(), " Price Is Required!", Toast.LENGTH_LONG).show();
                    else if (TextUtils.isEmpty(ContactNo.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Contact Number is Required!", Toast.LENGTH_LONG).show();
                    else if (TextUtils.isEmpty(Brand.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Brand is Required!", Toast.LENGTH_LONG).show();
                    else if (TextUtils.isEmpty(Condition.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Condition is Required!", Toast.LENGTH_LONG).show();
                    else {

                        SimpleDateFormat fm = new SimpleDateFormat("HH:mm:ss");
                        String hour = String.valueOf(tp.getHour());
                        String min = String.valueOf(tp.getMinute());
                        if(tp.getHour() < 10){
                            hour = "0" + hour;
                        }
                        if(tp.getMinute() < 10){
                            min = "0" + min;
                        }
                        String strTime = hour + ":" + min + ":" + "00";
                        adverticement.setDuration(strTime);


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
                        } else {
                            adverticement.setTitle(Title.getText().toString().trim());
                            adverticement.setPrice(Start_Price.getText().toString().trim());
                            adverticement.setDuration(strTime);
                            adverticement.setDate(strDate);
                            adverticement.setContact(ContactNo.getText().toString().trim());
                            fCat.setBrand(Brand.getText().toString().trim());
                            fCat.setCondition(Condition.getText().toString().trim());
                            adverticement.setDescription(Description.getText().toString().trim());
                            adverticement.setMaxBid("0");
                            adverticement.setStatus("active");
                            adverticement.setType("Electronics");
                            adverticement.setSeller_ID(uID);
                            final String strNumber = idPrefix + String.valueOf(maxid + 1);
                            fAuth.child(String.valueOf(strNumber)).setValue(fCat);
                            fAuth1.child(String.valueOf(strNumber)).setValue(adverticement);

                            for(int  i = 0 ; i < imageUris.size() ; i ++){
                                final StorageReference imageSrorageRef = fbStorageRef.child(String.valueOf(strNumber) + "." + String.valueOf(i));
                                imageSrorageRef.putFile(imageUris.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        imageSrorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String url = String.valueOf(uri);
                                                setDownLink(url , strNumber);
                                                Log.i("URL" , "Url Id : " + url);
                                                String num = String.valueOf(noOfImages);
                                                noOfImages++;
                                                fAuth1.child(strNumber).child("Img").child(num).setValue(url);
                                            }
                                        });

                                    }

                                });

                            }
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

            private void setDownLink(String url, String strNumber) {

                String key = String.valueOf(hashMap.size());
                hashMap.put(key , url);

            }


            public void clearControl() {
                Title.setText("");
                Start_Price.setText("");
                ContactNo.setText("");
                Description.setText("");
                Brand.setText("");
                Condition.setText("");


            }


        });


        publishLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth = FirebaseDatabase.getInstance().getReference().child("Electronics");
                fAuth1 = FirebaseDatabase.getInstance().getReference().child("Adverticement");
                userId = mFirebaseDatabase1.push().getKey();
                mFirebaseDatabase.child(userId).setValue(adverticement);
                mFirebaseDatabase1.child(userId).setValue(fCat);

                fAuth.addValueEventListener(new ValueEventListener() {
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
                    if (TextUtils.isEmpty(Title.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Title is Required!", Toast.LENGTH_LONG).show();
                    else if (TextUtils.isEmpty(Start_Price.getText().toString()))
                        Toast.makeText(getApplicationContext(), " Price Is Required!", Toast.LENGTH_LONG).show();
                    else if (TextUtils.isEmpty(ContactNo.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Contact Number is Required!", Toast.LENGTH_LONG).show();
                    else if (TextUtils.isEmpty(Brand.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Brand is Required!", Toast.LENGTH_LONG).show();
                    else if (TextUtils.isEmpty(Condition.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Condition is Required!", Toast.LENGTH_LONG).show();
                    else {

                        SimpleDateFormat fm = new SimpleDateFormat("HH:mm:ss");
                        String hour = String.valueOf(tp.getHour());
                        String min = String.valueOf(tp.getMinute());
                        if(tp.getHour() < 10){
                            hour = "0" + hour;
                        }
                        if(tp.getMinute() < 10){
                            min = "0" + min;
                        }

                        String strTime = hour + ":" + min + ":" + "00";


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
                        } else {
                            adverticement.setTitle(Title.getText().toString().trim());
                            adverticement.setPrice(Start_Price.getText().toString().trim());
                            adverticement.setDate(strDate);
                            adverticement.setDuration(strTime);
                            adverticement.setContact(ContactNo.getText().toString().trim());
                            adverticement.setDescription(Description.getText().toString().trim());
                            fCat.setBrand(Brand.getText().toString().trim());
                            fCat.setCondition(Condition.getText().toString().trim());
                            adverticement.setMaxBid("0");
                            adverticement.setStatus("inactive");
                            adverticement.setType("Electronics");
                            adverticement.setSeller_ID(uID);
                            final String strNumber = idPrefix + String.valueOf(maxid + 1);
                            fAuth.child(String.valueOf(strNumber)).setValue(fCat);
                            fAuth1.child(String.valueOf(strNumber)).setValue(adverticement);

                            for(int  i = 0 ; i < imageUris.size() ; i ++){
                                final StorageReference imageSrorageRef = fbStorageRef.child(String.valueOf(strNumber) + "." + String.valueOf(i));
                                imageSrorageRef.putFile(imageUris.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        imageSrorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String url = String.valueOf(uri);
                                                setDownLink(url , strNumber);
                                                Log.i("URL" , "Url Id : " + url);
                                                String num = String.valueOf(noOfImages);
                                                noOfImages++;
                                                fAuth1.child(strNumber).child("Img").child(num).setValue(url);
                                            }
                                        });

                                    }

                                });

                            }
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

            private void setDownLink(String url, String strNumber) {

                String key = String.valueOf(hashMap.size());
                hashMap.put(key , url);

            }


            public void clearControl() {
                Title.setText("");
                Start_Price.setText("");
                ContactNo.setText("");
                Description.setText("");
                Condition.setText("");
                Brand.setText("");
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
                    Toast.makeText(electronics_category.this,"Empty",Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(electronics_category.this,"empty",Toast.LENGTH_SHORT).show();
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

                    int noOfItems = data.getClipData().getItemCount();
                    for(int i = 0 ; i < noOfItems ; i++){
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imageUris.add(imageUri);
                        String FileNAme = getFileNameByURI(imageUri);
                        filenameList.add(FileNAme);

                    }


                    imageIs.setImageURI(imageUris.get(1));
                    Toast.makeText(getApplicationContext(), "Multiple Items Selected", Toast.LENGTH_SHORT).show();

                } else if (data.getData() != null) {
                    Toast.makeText(getApplicationContext(), "Single Item Selected", Toast.LENGTH_SHORT).show();
                }



            }
        }
    }


    public  String getFileNameByURI(Uri uri){
        String filename = null;

        if(uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri , null , null , null , null);
            try{
                if(cursor != null && cursor.moveToFirst()){
                    filename = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }
        }
        if(filename == null){
            filename = uri.getPath();
            int rem = filename.lastIndexOf('/');
            if(rem != -1){
                filename = filename.substring(rem +1);
            }
        }

        return filename;
    }

}
