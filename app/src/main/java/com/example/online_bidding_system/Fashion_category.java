package com.example.online_bidding_system;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Fashion_category extends AppCompatActivity {

    final int REQUEST_EXTERNAL_STORAGE = 100;

    EditText Brand,Condition,Material,Size,ContactNo,Description,Duration,Title,Type,Start_Price;
    Button publishNow,publishLater;
    DatabaseReference fAuth;
    Fashion fCat;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fashion_category);

        Button button = findViewById(R.id.button);

        Brand        =      findViewById(R.id.fashionBrand);
        Condition    =      findViewById(R.id.fashionCondition);
        Material     =      findViewById(R.id.fashionMaterial);
        Size         =      findViewById(R.id.fashionSize);
        ContactNo    =      findViewById(R.id.fashionContact);
        Description  =      findViewById(R.id.fashionDescription);
        Duration     =      findViewById(R.id.fashionDuration);
        Title        =      findViewById(R.id.fashionTitle);
        Type         =      findViewById(R.id.fashionType);
        Start_Price  =      findViewById(R.id.fashionPrize);

        publishNow   =      findViewById(R.id.publish_now);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Fashion_category.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Fashion_category.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
//                    return;
                } else {
                    GetdisplayIntent();
                }
            }
        });

        fCat = new Fashion();

        publishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fAuth = FirebaseDatabase.getInstance().getReference().child("Fashion_category");


                try {
                    if(TextUtils.isEmpty(Brand.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Brand Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Condition.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Condition Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Material.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Material Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Size.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Size Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(ContactNo.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Contact Number Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Description.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Description Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Duration.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Duration Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Title.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Title Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Type.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Type Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Start_Price.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Start Price Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }


                    else {
                        fCat.setBrand(Brand.getText().toString().trim());
                        fCat.setCondition(Condition.getText().toString().trim());
                        fCat.setMaterial(Material.getText().toString().trim());
                        fCat.setSize(Size.getText().toString().trim());
                        fCat.setContactNo(ContactNo.getText().toString().trim());
                        fCat.setDescription(Description.getText().toString().trim());
                        fCat.setDuration(Duration.getText().toString().trim());
                        fCat.setTitle(Title.getText().toString().trim());
                        fCat.setTitle(Type.getText().toString().trim());


                        fAuth.child("fashion1").setValue(fCat);
                        Toast.makeText(getApplicationContext() , "Data Inserted Successfully" , Toast.LENGTH_SHORT).show();

                    }
                }
                catch (NumberFormatException err){
                    Toast.makeText(getApplicationContext(), "Invalid Contact No" , Toast.LENGTH_SHORT).show();
                }




            }
        });





    }

    public void  GetdisplayIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    GetdisplayIntent();
                } else {

                }
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EXTERNAL_STORAGE && resultCode == RESULT_OK) {

            final ImageView imageView = findViewById(R.id.image_view);
            final List<Bitmap> bitmaps = new ArrayList<>();
            ClipData clipData = data.getClipData();

            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    Log.d("URI", imageUri.toString());
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        bitmaps.add(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {

                Uri imageUri = data.getData();
                Log.d("URI", imageUri.toString());
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.add(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (final Bitmap b : bitmaps) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(b);
                            }
                        });

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

}