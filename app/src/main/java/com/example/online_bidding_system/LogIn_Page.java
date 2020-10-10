package com.example.online_bidding_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LogIn_Page extends AppCompatActivity {

    Button uslogin;
    EditText txtEmail,txtPwd;
    TextView mCreateBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    SharedPreferences sharedPreferences;

    private static final String sharedPrefName = "myPref";
    private static final String keyName = "name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in__page);




        txtEmail = findViewById(R.id.setUsername);
        txtPwd   = findViewById(R.id.setPassword);
        progressBar =   findViewById(R.id.progressBar);
        fAuth       =   FirebaseAuth.getInstance();
        uslogin     =   findViewById(R.id.signin);

        sharedPreferences   =    getSharedPreferences(sharedPrefName,MODE_PRIVATE);
        String name = sharedPreferences.getString(keyName,null);


        uslogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = txtEmail.getText().toString().trim();
                String password = txtPwd.getText().toString().trim();

                if(TextUtils.isEmpty(email)){

                    txtEmail.setError("Email is Required !");
                    return;

                }
                if (TextUtils.isEmpty(password)){

                    txtPwd.setError("Password is Required !");
                    return;

                }

                if(password.length() < 6){

                    txtPwd.setError("Password should be more than SIX characters");
                    return;


                }

                progressBar.setVisibility(View.VISIBLE);



                //Authenticate The Users

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful()){


                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(keyName,txtEmail.getText().toString());
                            editor.apply();


                            //Temporarily this has been set to Home page page



                            Intent intent = new Intent(LogIn_Page.this,HomePage.class);
                            startActivity(intent);



                            Toast.makeText(LogIn_Page.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(),HomeItem.class));



                        }else {


                            Toast.makeText(LogIn_Page.this,"Login Failed",Toast.LENGTH_SHORT).show();

                        }


                    }
                });


            }
        });

    }


   
    

}