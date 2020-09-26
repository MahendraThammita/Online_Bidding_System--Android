package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class Edit_User extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private DrawerLayout drawer;
    private NavigationView navi;
    private Toolbar primTool;
    private ShapeableImageView roundedProfilePic;
    private Button imageChanger;
    private Button saveData;
    private static final int PICK_IMAGE = 1;
    private Uri profPicUri;
    private DatabaseReference retriveDbRef;
    private User currentUser;
    private StorageReference storageRef;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__user);

        retriveDbRef = FirebaseDatabase.getInstance().getReference().child("User").child("CUS1");
        
        retriveDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    String userId = dataSnapshot.getKey().toString();
                    String userName =  dataSnapshot.child("fullName").getValue().toString();
                    String userEmail =  dataSnapshot.child("email").getValue().toString();
                    //String userContact =  dataSnapshot.child("").getValue().toString();
                    String userAddress =  dataSnapshot.child("address").getValue().toString();
                    
                    currentUser = new User(userId , userName , userEmail , userAddress , "07525815");
                    setToViewHints(currentUser);
                    
                }
                else
                    Toast.makeText(getApplicationContext() , "Cannot Find the User" , Toast.LENGTH_SHORT);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //getting Image from Galary
        roundedProfilePic = findViewById(R.id.UserEditUserImage);
        imageChanger = findViewById(R.id.profImgeChangeBtn);

        imageChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent srchGallery = new Intent();
                srchGallery.setType("image/*");
                srchGallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(srchGallery , "Select Your Profile Picture") , PICK_IMAGE);
            }
        });

        //Image Uploading

        saveData = findViewById(R.id.editUserSaveBtn);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roundedProfilePic = findViewById(R.id.UserEditUserImage);
                Bitmap bitmap = ((BitmapDrawable)roundedProfilePic.getDrawable()).getBitmap();


            }
        });



//Drawer Section
        drawer = findViewById(R.id.DrwerLay);
        navi = (NavigationView) findViewById(R.id.nav_view);
        primTool = findViewById(R.id.primaryActbar);

        setSupportActionBar(primTool);


        navi.bringToFront();
        ActionBarDrawerToggle toggle1 = new ActionBarDrawerToggle(this , drawer , primTool , R.string.OpenDrawerDes , R.string.CloseDrawerDes);
        drawer.addDrawerListener(toggle1);
        toggle1.syncState();


//        navi.setNavigationItemSelectedListener(this);


        navi.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.Drawable_myBids:
                        Intent in0 = new Intent(Edit_User.this , myBids.class);
                        startActivity(in0);
                        break;
                    case R.id.Drawable_myWins:
                        Intent in1 = new Intent(Edit_User.this , MyWins.class);
                        startActivity(in1);
                        break;
                    case R.id.Drawable_ViewAuctions:
                        Intent in2 = new Intent(Edit_User.this ,MyAuctions.class);
                        startActivity(in2);
                        break;
                    case R.id.Drawable_myAuctions:
                        Intent in3 = new Intent(getApplicationContext() , HomePage.class);
                        startActivity(in3);
                        break;
                    default:
                        Intent in6 = new Intent(getApplicationContext() , MyAuctions.class);
                        startActivity(in6);
                }
                return true;
            }
        });

    }

    private void setToViewHints(User currentUser) {

        TextInputEditText uName = findViewById(R.id.editUserName);
        TextInputEditText uEmail = findViewById(R.id.editUserEmail);
        TextInputEditText uAddress = findViewById(R.id.editUserAddress);
        TextInputEditText uPhone = findViewById(R.id.editUserPhone);

        uName.setHint(currentUser.getFullName().toString());
        uEmail.setHint(currentUser.getEmail().toString());
        uAddress.setHint(currentUser.getAddress().toString());
        uPhone.setHint(currentUser.getContactNo().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            profPicUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , profPicUri);
                roundedProfilePic.setImageBitmap(bitmap);
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }

    }

    private String getFileExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }



}