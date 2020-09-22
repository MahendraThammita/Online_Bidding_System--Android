package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DVDnMovies_category extends AppCompatActivity {

    final int REQUEST_EXTERNAL_STORAGE = 100;

    EditText Title,Type,Genre,Condition,Start_Price,Duration,ContactNo,Description;
    Button publishNow,publishLater;
    DatabaseReference fAuth;
    FdeHelper fCat;
    long maxid=0;
    String idPrefix="DVD";

    private ImageSwitcher imageIs;
    private Button preBtn,nxBtn, pickImgbtn;
    private  ArrayList<Uri> imageUris;
    private static final int PICK_IMAGES_CODE = 1;
    int position = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_v_dn_movies_category);



        Title       =   findViewById(R.id.setTitle);
        Type        =   findViewById(R.id.setType);
        Genre       =   findViewById(R.id.setGenre);
        Condition   =   findViewById(R.id.setCondition);
        Start_Price =   findViewById(R.id.setPrize);
        Duration    =   findViewById(R.id.setDuration);
        ContactNo     =   findViewById(R.id.setContact);
        Description =   findViewById(R.id.setDescription);

        publishNow   =      findViewById(R.id.publish_now);


        fCat = new FdeHelper();


        publishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fAuth = FirebaseDatabase.getInstance().getReference().child("DVDandMovies");
                fAuth = FirebaseDatabase.getInstance().getReference().child("Adverticement");

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
                    if(TextUtils.isEmpty(Title.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Title Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Type.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Type Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Genre.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Genre Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Condition.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Condition Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Start_Price.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Starting at Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Duration.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Duration Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(ContactNo.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Contact Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }
                    else if(TextUtils.isEmpty(Description.getText().toString())){
                        Toast.makeText(getApplicationContext() , "Description Field Is Empty" , Toast.LENGTH_SHORT).show();
                    }


                    else {
                        fCat.setTitle(Title.getText().toString().trim());
                        fCat.setType(Type.getText().toString().trim());
                        fCat.setGenre(Genre.getText().toString().trim());
                        fCat.setCondition(Condition.getText().toString().trim());
                        fCat.setStart_Price(Start_Price.getText().toString().trim());
                        fCat.setDuration(Duration.getText().toString().trim());
                        fCat.setContactNo(ContactNo.getText().toString().trim());
                        fCat.setDescription(Description.getText().toString().trim());

                        String strNumber= idPrefix+String.valueOf(maxid+1);
                        fAuth.child(String.valueOf(strNumber)).setValue(fCat);


                        Toast.makeText(getApplicationContext() , "Data Inserted Successfully" , Toast.LENGTH_SHORT).show();
                        clearControl();

                    }
                }
                catch (NumberFormatException err){
                    Toast.makeText(getApplicationContext(), "Invalid Contact No" , Toast.LENGTH_SHORT).show();

                }



            }

            public void clearControl() {
                Title.setText("");
                Type.setText("");
                Genre.setText("");
                Condition.setText("");
                Start_Price.setText("");
                Duration.setText("");
                ContactNo.setText("");
                Description.setText("");

            }



        });





}

}
