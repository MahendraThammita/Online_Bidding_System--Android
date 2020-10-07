package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity<pref_userName> extends AppCompatActivity {


    TextView userEmail;
    Button Logout;
    String mFullName;

    FirebaseAuth fAuth;
    FirebaseUser firebaseUser;

    //new
    SharedPreferences sp;
    private static final String spn = "mypref";
    private static final String kn = "name";
    private static final String ke = "name";
    //#new


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        userEmail = findViewById(R.id.email);




        //new
        sp  =   getSharedPreferences(spn,MODE_PRIVATE);
        String name = sp.getString(kn,null);
        //#new


        if (name != null){

            userEmail.setText("Full mail" + name);

        }





        fAuth = FirebaseAuth.getInstance();
        firebaseUser = fAuth.getCurrentUser();

        //userEmail.setText(firebaseUser.getEmail());

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //new
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                finish();
                //#new

                Toast.makeText(MainActivity.this,"Log out successful",Toast.LENGTH_SHORT).show();

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LogIn_Page.class));

            }
        });


    }




}

