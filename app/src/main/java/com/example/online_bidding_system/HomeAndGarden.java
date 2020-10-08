package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class HomeAndGarden extends AppCompatActivity {
    final int REQUEST_EXTERNAL_STORAGE = 100;
    EditText txtTitle,txtPrice,txtDuration,txtContact,txtEnvironment,txtDescription;
    Button PublishNow,PublishLater;
    DatabaseReference DbRef,DbRef1;
    HomeItem homeitem;
    Adverticement adverticement;
    long maxid=0;
    private String userId;
    String idPrefix="HAG";
    private ImageSwitcher imageIs;
    private Button preBtn,nxBtn,pickImgbtn;
    private ArrayList<Uri> imageUris;
    private ArrayList<String> filenameList;
    private HashMap<String , String> hashMap;
    private static final int PICK_IMAGES_CODE = 1;
    int position = 0;

    int noOfImages = 0;
    StorageReference fbStorageRef;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    //Create an object of DatabaseReference to create second table
    private DatabaseReference mFirebaseDatabase1;
    //Timepicker object
    TimePicker tp;
    //Datapicker object
    DatePicker dp;

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
        PublishLater = findViewById(R.id.publish_later);
        pickImgbtn = findViewById(R.id.pickImg);
        imageUris = new ArrayList<>();
        filenameList = new ArrayList<>();
        hashMap = new HashMap<>();

        dp = findViewById(R.id.setDate);

        tp = findViewById(R.id.setTime);


        //object
        homeitem = new HomeItem();
        adverticement=new  Adverticement();




        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'Adverticement' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Adverticement");

        // get reference to 'Home&Garden' node
        mFirebaseDatabase1 = mFirebaseInstance.getReference("Home&Garden");

        fbStorageRef = FirebaseStorage.getInstance().getReference().child("AntiqueImages");
        //Setting image picker intents

        pickImgbtn.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgsIntent = new Intent();
                imgsIntent.setType("image/*");
                imgsIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                imgsIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imgsIntent, "Select Multiple Images"), PICK_IMAGES_CODE);
            }
        });


        PublishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbRef = FirebaseDatabase.getInstance().getReference().child("Home&Garden");
                DbRef1 = FirebaseDatabase.getInstance().getReference().child("Adverticement");
                //use id
                userId = mFirebaseDatabase1.push().getKey();
                //insert data in firebase database Adverticement
                mFirebaseDatabase.child(userId).setValue(adverticement);

                //insert data in firebase database Home&Garden
                mFirebaseDatabase1.child(userId).setValue(homeitem);
                DbRef.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                            maxid=(dataSnapshot.getChildrenCount());
                        savedata();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
            //new
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void savedata(){
                try {
                    if (TextUtils.isEmpty(txtTitle.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Your Title is Required!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtPrice.getText().toString()))
                        Toast.makeText(getApplicationContext(), " NIC Price Is Required!", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(txtContact.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Contact Number is Required!", Toast.LENGTH_SHORT).show();
                    else {
                        adverticement.setTitle(txtTitle.getText().toString().trim());
                        adverticement.setPrice(txtPrice.getText().toString().trim());

                        adverticement.setContact(txtContact.getText().toString().trim());
                        homeitem.setEnvironment(txtEnvironment.getText().toString().trim());
                        adverticement.setDescription(txtDescription.getText().toString().trim());
                        adverticement.setStatus("active");
                        adverticement.setType("HomeAndGarden");
                        adverticement.setSeller_ID("CUS1");
                        adverticement.setMaxBid("0");
                        //set timepicker value
                        String strTime = tp.getHour() + ":" + tp.getMinute() + ":" + "00";
                        adverticement.setDuration(strTime);
                        //set datapicker value
                        // String strDate =  dp.getYear() + "-" + (dp.getMonth() + 1) + "-" + dp.getDayOfMonth();
                        //adverticement.setDate(strDate);


                        int year = dp.getYear();
                        int month = dp.getMonth();
                        int day = dp.getDayOfMonth();

                        Calendar myCal = Calendar.getInstance();
                        myCal.set(year, month, day);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
                        String strDate = dateFormat.format(myCal.getTime());
                        adverticement.setDate(strDate);



                        final String strNumber = idPrefix + String.valueOf(maxid + 1);
                        DbRef.child(String.valueOf(strNumber)).setValue(homeitem);
                        DbRef1.child(String.valueOf(strNumber)).setValue(adverticement);

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
                                            DbRef1.child(strNumber).child("Img").child(num).setValue(url);
                                        }
                                    });

                                }});}


                        //Toast.makeText(getApplicationContext() , "Images Uploaded" , Toast.LENGTH_SHORT);


                        Toast.makeText(getApplicationContext(), "Successfully Published", Toast.LENGTH_SHORT).show();
                        clearControl();
                        Intent displayIntent = new Intent(getApplicationContext(), TabedAuctions.class);
                        startActivity(displayIntent);

                    }


                } catch (NumberFormatException e) {

                    Toast.makeText(getApplicationContext(), " wrong Inserted", Toast.LENGTH_SHORT).show();


                }
            }

            private void setDownLink(String url, String strNumber) {

                String key = String.valueOf(hashMap.size());
                hashMap.put(key , url);

            }
            public void clearControl() {
                txtTitle.setText("");
                txtPrice.setText("");
                //txtDuration.setText("");
                txtContact.setText("");
                txtEnvironment.setText("");
                txtDescription.setText("");
            }


        });


        PublishLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbRef = FirebaseDatabase.getInstance().getReference().child("Home&Garden");
                DbRef1 = FirebaseDatabase.getInstance().getReference().child("Adverticement");
                //use id
                userId = mFirebaseDatabase1.push().getKey();
                //insert data in firebase database Adverticement
                mFirebaseDatabase.child(userId).setValue(adverticement);

                //insert data in firebase database Home&Garden
                mFirebaseDatabase1.child(userId).setValue(homeitem);
                DbRef.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                            maxid=(dataSnapshot.getChildrenCount());
                            savedata();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
            //new
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void savedata(){
                try {
                    if (TextUtils.isEmpty(txtTitle.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Your Title is Required!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtPrice.getText().toString()))
                        Toast.makeText(getApplicationContext(), " NIC Price Is Required!", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(txtContact.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Contact Number is Required!", Toast.LENGTH_SHORT).show();
                    else {
                        adverticement.setTitle(txtTitle.getText().toString().trim());
                        adverticement.setPrice(txtPrice.getText().toString().trim());

                        adverticement.setContact(txtContact.getText().toString().trim());
                        homeitem.setEnvironment(txtEnvironment.getText().toString().trim());
                        adverticement.setDescription(txtDescription.getText().toString().trim());
                        adverticement.setStatus("inactive");
                        adverticement.setType("HomeAndGarden");
                        adverticement.setSeller_ID("CUS1");
                        adverticement.setMaxBid("0");


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

                        //TimeCalculations timeCalculations = new TimeCalculations(strTime, strDate);
                       // boolean flag = timeCalculations.isExpired();

                        //if (flag == true) {
                           // clearControl();
                            //Toast.makeText(getApplicationContext(), "Please Enter a valid date", Toast.LENGTH_LONG).show();
                        //}
                        adverticement.setDate(strDate);



                        final String strNumber = idPrefix + String.valueOf(maxid + 1);
                        DbRef.child(String.valueOf(strNumber)).setValue(homeitem);
                        DbRef1.child(String.valueOf(strNumber)).setValue(adverticement);
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
                                            DbRef1.child(strNumber).child("Img").child(num).setValue(url);
                                        }
                                    });

                                }

                            });

                        }
                        Toast.makeText(getApplicationContext() , "Images Uploaded" , Toast.LENGTH_SHORT);


                        Toast.makeText(getApplicationContext(), "Successfully Published", Toast.LENGTH_SHORT).show();
                        clearControl();
                        Intent displayIntent = new Intent(getApplicationContext(), TabedAuctions.class);
                        startActivity(displayIntent);

                    }


                } catch (NumberFormatException e) {

                    Toast.makeText(getApplicationContext(), " wrong Inserted", Toast.LENGTH_SHORT).show();


                }
            }
            private void setDownLink(String url, String strNumber) {

                String key = String.valueOf(hashMap.size());
                hashMap.put(key , url);

            }

            public void clearControl() {
                txtTitle.setText("");
                txtPrice.setText("");
                //txtDuration.setText("");
                txtContact.setText("");
                txtEnvironment.setText("");
                txtDescription.setText("");
            }


        });
        imageIs = findViewById(R.id.imageIs);
        preBtn = findViewById(R.id.preButton);
        nxBtn =  findViewById(R.id.nextButton);
        pickImgbtn = findViewById(R.id.pickImg);
        imageUris = new ArrayList<>();
        imageIs.setFactory(new ViewSwitcher.ViewFactory() {
            public ImageView makeView() {
                ImageView imageView = new ImageView((getApplicationContext()));
                return imageView;
            }
        });

//        pickImgbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                pickImagesIntent();
//
//            }
//        });



        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position>0){
                    position--;
                    imageIs.setImageURI(imageUris.get(position));
                }

                else{
                    Toast.makeText(HomeAndGarden.this,"Empty",Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(HomeAndGarden.this,"empty",Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

//    private void pickImagesIntent(){
//
//
//
//        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
//        startActivityForResult(intent, PICK_IMAGES_CODE);
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGES_CODE){

            if(resultCode == Activity.RESULT_OK){
                if(data.getClipData() != null){

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

//                Uditha Statements
//                if(data.getClipData() != null){
//
//                    int cout  = data.getClipData().getItemCount();
//                    for(int i=0; i<cout; i++){
//                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                        imageUris.add(imageUri);
//                    }
//
//                    imageIs.setImageURI(imageUris.get(0));
//                    position = 0;
//                }
//
//                else{
//                    Uri imageUri = data.getData();
//                    imageUris.add(imageUri);
//                    imageIs.setImageURI(imageUris.get(0));
//                    position = 0;
//                }
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








