package com.example.online_bidding_system;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button profBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profBtn = findViewById(R.id.actionBarProfile);

        profBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profIntent = new Intent(getApplicationContext() , PlaceBid.class);
                startActivity(profIntent);
            }
        });




        Toolbar profTop = findViewById(R.id.profActionbar);
        profTop.setTitle("");
        setSupportActionBar(profTop);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.profile_top_nav,menu);
//        return true;
//    }
}