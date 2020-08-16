package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class main_categories extends AppCompatActivity {

    Button profBtn;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_categories);

        profBtn = findViewById(R.id.actionBarProfile);

        profBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profIntent = new Intent(getApplicationContext() , myBids.class);
                startActivity(profIntent);
            }
        });
    }
}