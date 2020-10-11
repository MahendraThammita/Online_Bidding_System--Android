package com.example.online_bidding_system.HelperClasser.BiddingAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.online_bidding_system.Antiques_Category;
import com.example.online_bidding_system.Draft_Auctions;
import com.example.online_bidding_system.Edit_User;
import com.example.online_bidding_system.HomePage;
import com.example.online_bidding_system.LogIn_Page;
import com.example.online_bidding_system.MyAuctions;
import com.example.online_bidding_system.R;
import com.example.online_bidding_system.TabedAuctions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RegisteredUser {

    private String userID;
    private String ProfilePic;
    private String address;
    private String email;
    private String fullName;
    private String nic;
    private String phone;

    public RegisteredUser() {
    }

    public RegisteredUser(String userID) {
        this.userID = userID;
    }

    public RegisteredUser(String userID, String profilePic, String address, String email, String fullName, String nic) {
        this.userID = userID;
        ProfilePic = profilePic;
        this.address = address;
        this.email = email;
        this.fullName = fullName;
        this.nic = nic;
    }

    public void navigater(MenuItem item , Context context){
        switch (item.getItemId()){
            case R.id.Drawable_myBids:
                Intent in0 = new Intent(context.getApplicationContext() , TabedAuctions.class);
                context.startActivity(in0);
                break;
            case R.id.Drawable_myWins:
                Intent in1 = new Intent(context.getApplicationContext() , LogIn_Page.class);
                context.startActivity(in1);
                break;
            case R.id.Drawable_ViewAuctions:
                Intent in2 = new Intent(context.getApplicationContext() , Edit_User.class);
                context.startActivity(in2);
                break;
            case R.id.Drawable_myAuctions:
                Intent in3 = new Intent(context.getApplicationContext() , Draft_Auctions.class);
                context.startActivity(in3);
                break;
            case R.id.Drawable_logout:
                Intent in4 = new Intent(context.getApplicationContext() , Antiques_Category.class);
                context.startActivity(in4);
                break;
            default:
                Intent in6 = new Intent(context.getApplicationContext() , MyAuctions.class);
                context.startActivity(in6);
        }
    }

    public RegisteredUser getUserByUID(String userID){
        final RegisteredUser[] user = new RegisteredUser[1];
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("User").child(userID);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String uid = dataSnapshot.getKey();
                    String uEmail = dataSnapshot.child("email").getValue().toString();
                    String address = dataSnapshot.child("address").getValue().toString();
                    String fullName = dataSnapshot.child("fullName").getValue().toString();
                    String nic = dataSnapshot.child("nic").getValue().toString();
                    String uImage;
                    try{
                        uImage  = dataSnapshot.child("ProfilePic").toString();
                    }
                    catch (NullPointerException ex){
                        uImage = "Default.png";
                    }

                    StorageReference storageRef = FirebaseStorage.getInstance().getReference("userImages");
                    uImage = storageRef.child(uImage).getDownloadUrl().toString();
                    user[0] = new RegisteredUser(uid , uImage , address , uEmail , fullName , nic);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return user[0];
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
