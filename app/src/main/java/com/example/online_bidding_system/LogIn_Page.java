package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LogIn_Page extends AppCompatActivity {

    Button uslogin;
    EditText txtEmail, txtPwd;
    TextView mCreateBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    SharedPreferences sharedPreferences;
    Query dbRef;
    String email;


    private static final String sharedPrefName = "myPref";
    private static final String keyName = "name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in__page);


        txtEmail = findViewById(R.id.setUsername);
        txtPwd = findViewById(R.id.setPassword);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        uslogin = findViewById(R.id.signin);

        sharedPreferences = getSharedPreferences("sharedPrefName", Context.MODE_PRIVATE);
        String UID = sharedPreferences.getString("USER_ID", null);

        if (UID != null) {
            Intent toHome = new Intent(getApplicationContext(), HomePage.class);
            startActivity(toHome);
        } else {
            uslogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    email = txtEmail.getText().toString().trim();
                    String password = txtPwd.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {

                        txtEmail.setError("Email is Required !");
                        return;

                    }
                    if (TextUtils.isEmpty(password)) {

                        txtPwd.setError("Password is Required !");
                        return;

                    }

                    if (password.length() < 6) {

                        txtPwd.setError("Password should be more than SIX characters");
                        return;


                    }

                    progressBar.setVisibility(View.VISIBLE);


                    //Authenticate The Users

                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {


                                getData(new FirebaseCallback() {
                                    @Override
                                    public void onCallback(String uid, String uname) {

                                        //Temporarily this has been set to Home page page

                                        Intent intent = new Intent(LogIn_Page.this, HomePage.class);
                                        startActivity(intent);
                                    }
                                });

                                Log.i("After callback" , "After" );
                                Toast.makeText(LogIn_Page.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(getApplicationContext(),HomeItem.class));


                            } else {


                                Toast.makeText(LogIn_Page.this, "Login Failed", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });


                }
            });
        }

    }

    private void getData(final FirebaseCallback firebaseCallback) {

        final String[] uID = new String[1];
        final String[] uName = new String[1];

        dbRef = FirebaseDatabase.getInstance().getReference("User").orderByChild("email").equalTo(email);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    uID[0] = dataSnapshot.getKey();
                    Log.i("KEy" , "KEY IS :" + uID[0]);
                    uName[0] = dataSnapshot.child("fullName").getValue().toString();
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("UserEmail", txtEmail.getText().toString());
                editor.putString("USER_ID", uID[0]);
                editor.putString("USER_NAME", uName[0]);
                editor.commit();
                firebaseCallback.onCallback(uID[0], uName[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private interface FirebaseCallback {
        void onCallback(String uid, String uname);
    }


}

