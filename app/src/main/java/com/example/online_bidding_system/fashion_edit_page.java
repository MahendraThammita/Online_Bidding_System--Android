package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class fashion_edit_page extends AppCompatActivity {


    EditText editTitle, editPrice, editDuration, editTime, editDate, editContact, editMaterial, editDescription, editCondition, editSize;
    Spinner period;
    Button PublishNow, update, delete;

    DatabaseReference fAuth;
    DatabaseReference fAuth1;

    private DatabaseReference mFirebaseDatabase;
    private DatabaseReference mFirebaseDatabase1;
    private FirebaseDatabase mFirebaseInstance;
    Adverticement adverticement;
    FdeHelper fCat;
    long maxid;
    String MaxBid;
    String idPrefix = "AN";
    private ImageSwitcher imageIs;
    private Button preBtn, nxBtn, pickImgbtn;
    private ArrayList<Uri> imageUris;
    private String userId;
    private static final int PICK_IMAGES_CODE = 1;
    int position = 0;


    TimePicker tp;
    DatePicker dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antiques_edit);





        Spinner spinner = findViewById(R.id.setPeriod);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TimePeriod, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        editTitle = findViewById(R.id.setTitle);
        editPrice = findViewById(R.id.setPrice);
        editDate = findViewById(R.id.setDate);
        editTime = findViewById(R.id.setTime);
        editContact = findViewById(R.id.setContact);
        editCondition = findViewById(R.id.setCondition);
        editDescription = findViewById(R.id.setDescription);
        editMaterial.findViewById(R.id.setMaterials);
        editSize.findViewById(R.id.setSize);
        PublishNow = findViewById(R.id.publish_now);
        update = findViewById(R.id.Update);
        delete = findViewById(R.id.Delete);


        fCat = new FdeHelper();
        adverticement = new Adverticement();

        Intent retriveIntent = getIntent();
        String AuctName = retriveIntent.getStringExtra("AUCT_ID").toString();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Adverticement");
        mFirebaseDatabase1 = mFirebaseInstance.getReference("FashionAndDesign");




        fAuth = FirebaseDatabase.getInstance().getReference().child("Adverticement").child(AuctName);
        fAuth1 = FirebaseDatabase.getInstance().getReference().child("FashionAndDesign").child(AuctName);
        fAuth.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {


                    editTitle.setText(dataSnapshot.child("title").getValue().toString());
                    editContact.setText(dataSnapshot.child("contact").getValue().toString());
                    editPrice.setText(dataSnapshot.child("price").getValue().toString());
                    editDescription.setText(dataSnapshot.child("description").getValue().toString());
                    editMaterial.setText(dataSnapshot.child("material").getValue().toString());
                    editSize.setText(dataSnapshot.child("size").getValue().toString());
                    editCondition.setText(dataSnapshot.child("condition").getValue().toString());


                } else
                    Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
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

                fAuth = FirebaseDatabase.getInstance().getReference().child("Adverticement").child(AuctName);
                fAuth1 = FirebaseDatabase.getInstance().getReference().child("FashionAndDesign").child(AuctName);
                fAuth.removeValue();
                fAuth1.removeValue();
                Toast.makeText(getApplicationContext(), "Succesfully Deleated", Toast.LENGTH_SHORT).show();
                Intent displayIntent = new Intent(getApplicationContext(), TabedAuctions.class);
                startActivity(displayIntent);
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent retriveIntent = getIntent();
                String AuctName = retriveIntent.getStringExtra("AUCT_ID").toString();


                fAuth = FirebaseDatabase.getInstance().getReference();
                fAuth1 = FirebaseDatabase.getInstance().getReference();
                fAuth1.child("Adverticement").child(AuctName).child("title").setValue(editTitle.getText().toString().trim());
                fAuth1.child("Adverticement").child(AuctName).child("contact").setValue(editContact.getText().toString().trim());
                fAuth1.child("Adverticement").child(AuctName).child("price").setValue(editPrice.getText().toString().trim());
                fAuth1.child("Adverticement").child(AuctName).child("description").setValue(editDescription.getText().toString().trim());
                fAuth1.child("Adverticement").child(AuctName).child("date").setValue(editDate.getText().toString().trim());
                fAuth1.child("Adverticement").child(AuctName).child("duration").setValue(editDuration.getText().toString().trim());

                fAuth.child("FashionAndDesign").child(AuctName).child("material").setValue(editMaterial.getText().toString());
                fAuth.child("FashionAndDesign").child(AuctName).child("size").setValue(editSize.getText().toString());
                fAuth.child("FashionAndDesign").child(AuctName).child("condition").setValue(editCondition.getText().toString());


                Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                Intent displayIntent = new Intent(getApplicationContext(), TabedAuctions.class);
                startActivity(displayIntent);

            }
        });


        PublishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent retriveIntent = getIntent();
                String AuctName = retriveIntent.getStringExtra("AUCT_ID").toString();

                fAuth = FirebaseDatabase.getInstance().getReference();
                fAuth.child("Adverticement").child(AuctName).child("status").setValue("active");

                Toast.makeText(getApplicationContext(), "Your Ad is now on Live", Toast.LENGTH_SHORT).show();
                Intent displayIntent = new Intent(getApplicationContext(), TabedAuctions.class);
                startActivity(displayIntent);

            }
        });

        imageIs = findViewById(R.id.imageIs);
        preBtn = findViewById(R.id.preButton);
        nxBtn = findViewById(R.id.nextButton);
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
                if (position > 0) {
                    position--;
                    imageIs.setImageURI(imageUris.get(position));
                } else {
                    Toast.makeText(fashion_edit_page.this, "Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nxBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (position < imageUris.size() - 1) {

                    position++;
                    imageIs.setImageURI(imageUris.get(position));
                } else {

                    Toast.makeText(fashion_edit_page.this, "empty", Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }


    private void pickImagesIntent() {


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
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
    }


    //Testing time for validation
    public boolean ValidateTime(String time) {
        if (!time.trim().matches("(([0-1][0-9])|([2][0-3])):([0-5][0-9]):([0-5][0-9])"))
            return false;
        return true;


    }
}




