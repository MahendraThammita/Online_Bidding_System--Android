package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogIn_Page extends AppCompatActivity {

    Button uslogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in__page);
    }


    @Override
    protected void onResume() {
        super.onResume();

        uslogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent logintent = new Intent(LogIn_Page.this, HomePage.class);
                startActivity(logintent);
            }
        });
    }
}