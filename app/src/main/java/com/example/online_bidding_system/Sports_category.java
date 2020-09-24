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
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Sports_category extends AppCompatActivity {
    EditText txtTitle,txtPrice,txtDuration,txtContact,txtBrand,txtCondition,txtDescription;
    Button PublishNow;
    DatabaseReference DbRef,DbRef1;
    HomeItem homeitem;
    Adverticement adverticement;
    long maxid=0;
    String idPrefix="SAH";
    private ImageSwitcher imageIs;
    private Button preBtn,nxBtn, pickImgbtn;
    private ArrayList<Uri> imageUris;
    private static final int PICK_IMAGES_CODE = 1;
    int position = 0;
    //data ref
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_category);

        txtTitle = findViewById(R.id.setTitle);
        txtPrice = findViewById(R.id.setPrice);
        txtDuration = findViewById(R.id.setDuration);
        txtContact = findViewById(R.id.setContact);
        txtBrand = findViewById(R.id.setBrand);
        txtCondition = findViewById(R.id.setCondition);
        txtDescription = findViewById(R.id.setDescription);
        PublishNow = findViewById(R.id.publish_now);


        //object
        homeitem = new HomeItem();
        adverticement=new  Adverticement();



        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'RepositoryName' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Adverticement");

        // get reference to 'Bookmarks' node
        mFirebaseDatabase1 = mFirebaseInstance.getReference("Hobbies&Sports");


        PublishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbRef = FirebaseDatabase.getInstance().getReference().child("Hobbies&Sports");
                DbRef1 = FirebaseDatabase.getInstance().getReference().child("Adverticement");
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
                        adverticement.setTitle(txtTitle.getText().toString().trim());
                        adverticement.setPrice(txtPrice.getText().toString().trim());
                        adverticement.setDuration(txtDuration.getText().toString().trim());
                        adverticement.setContact(txtContact.getText().toString().trim());
                        homeitem.setCondition(txtCondition.getText().toString().trim());
                        adverticement.setDescription(txtDescription.getText().toString().trim());
                        homeitem.setBrand(txtBrand.getText().toString().trim());
                        // DbRef.child("user").setValue(user);
                        String strNumber= idPrefix+String.valueOf(maxid+1);
                        DbRef.child(String.valueOf(strNumber)).setValue(homeitem);
                        DbRef1.child(String.valueOf(strNumber)).setValue(adverticement);
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
                txtCondition.setText("");
                txtBrand.setText("");
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
        Toast.makeText(Sports_category.this,"Empty",Toast.LENGTH_SHORT).show();
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

        Toast.makeText(Sports_category.this,"empty",Toast.LENGTH_SHORT).show();
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




