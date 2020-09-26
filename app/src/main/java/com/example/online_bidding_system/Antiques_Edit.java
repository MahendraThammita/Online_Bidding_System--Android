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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Antiques_Edit extends AppCompatActivity{


    EditText txtTitle,txtPrice,txtDuration,txtContact,txtPeriod,txtDescription;
    Spinner period;
    Button update, delete,PublishNow;
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


        txtTitle = findViewById(R.id.setTitle);
        txtPrice = findViewById(R.id.setPrice);
        txtContact = findViewById(R.id.setContact);
        dp = findViewById(R.id.setDate);
        tp = findViewById(R.id.setTime);
        period = (Spinner)findViewById(R.id.setPeriod);
        txtDescription = findViewById(R.id.setDescription);
        update = findViewById(R.id.Update);
        delete =  findViewById(R.id.Delete);

        add = new auction();
        adverticement=new  Adverticement();

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference("Adverticement");
        mFirebaseDatabase1 = mFirebaseInstance.getReference("Antiques");

        DbRef = FirebaseDatabase.getInstance().getReference().child("Adverticement").child("AN1");
        DbRef1 = FirebaseDatabase.getInstance().getReference().child("Antiques").child("AN1");
        DbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    txtTitle.setText(dataSnapshot.child("title").getValue().toString());
                    txtContact.setText(dataSnapshot.child("contact").getValue().toString());
                    txtPrice.setText(dataSnapshot.child("price").getValue().toString());
                    txtDescription.setText(dataSnapshot.child("description").getValue().toString());


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
                DbRef = FirebaseDatabase.getInstance().getReference().child("Adverticement").child("AN1");
                DbRef.removeValue();
                Toast.makeText(getApplicationContext() , "Succesfully Deleated" , Toast.LENGTH_SHORT).show();
            }
        });



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbRef = FirebaseDatabase.getInstance().getReference().child("Antiques");
                DbRef1= FirebaseDatabase.getInstance().getReference().child("Adverticement");
                userId = mFirebaseDatabase1.push().getKey();
                mFirebaseDatabase.child(userId).setValue(adverticement);

                mFirebaseDatabase1.child(userId).setValue(add);
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
                        Toast.makeText(getApplicationContext(), "Title is Required!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtPrice.getText().toString()))
                        Toast.makeText(getApplicationContext(), " Price Is Required!", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtContact.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Contact Number is Required!", Toast.LENGTH_SHORT).show();
                    else {


                        String strTime = tp.getHour() + ":" + tp.getMinute();
                        adverticement.setDuration(strTime);

                        String strDate =  dp.getYear() + "-" + (dp.getMonth() + 1) + "-" + dp.getDayOfMonth();
                        adverticement.setDate(strDate);

                        String strNumber= idPrefix+String.valueOf(maxid+1);
                        adverticement.setTitle(txtTitle.getText().toString().trim());
                        adverticement.setPrice(txtPrice.getText().toString().trim());
                        adverticement.setContact(txtContact.getText().toString().trim());
                        adverticement.setDescription(txtDescription.getText().toString().trim());
                        adverticement.setMaxBid("0");
                        add.setTime_period(period.getSelectedItem().toString());
                        DbRef.child(String.valueOf(strNumber)).setValue(add);
                        DbRef1.child(String.valueOf(strNumber)).setValue(adverticement);
                        Toast.makeText(getApplicationContext(), "Successfully saved", Toast.LENGTH_SHORT).show();
                        maxid  = maxid+1;
                        clearControl();

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
                    Toast.makeText(Antiques_Edit.this,"Empty",Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(Antiques_Edit.this,"empty",Toast.LENGTH_SHORT).show();
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

