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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class RegistrationPage extends AppCompatActivity {

    EditText txtFullName,txtNIC,txtEmail,txtAddress,txtPwd;
    Button butSave;
    DatabaseReference DbRef;
    FirebaseAuth firebaseAuth;
    User user;
    long maxid=0;
    String idPrefix="CUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        txtFullName = findViewById(R.id.FName);
        txtEmail =  findViewById(R.id.email);
        txtPwd  =  findViewById(R.id.pwd);
        txtAddress =  findViewById(R.id.address);
        txtNIC =  findViewById(R.id.NIC);
        butSave = findViewById(R.id.savebtn);

        user = new User();

        //NEW
        firebaseAuth    = FirebaseAuth.getInstance();






        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DbRef = FirebaseDatabase.getInstance().getReference().child("User");



                //Sending info to Firebase Authentication
                String email = txtEmail.getText().toString().trim();
                String password = txtPwd.getText().toString().trim();


                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(RegistrationPage.this, "User Account Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else{


                            Toast.makeText(RegistrationPage.this, "Error Occurred !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();



                        }

                    }
                });



                DbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                            maxid = (dataSnapshot.getChildrenCount());
                        savedata();
                    }








                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
                //id auto increment
                private void savedata(){
                    try {
                        if (TextUtils.isEmpty(txtFullName.getText().toString()))
                            Toast.makeText(getApplicationContext(), "Your Name is Required!", Toast.LENGTH_SHORT).show();
                        else if (TextUtils.isEmpty(txtNIC.getText().toString()))
                            Toast.makeText(getApplicationContext(), " NIC Number Is Required!", Toast.LENGTH_SHORT).show();
                        else if (TextUtils.isEmpty(txtEmail.getText().toString()))
                            Toast.makeText(getApplicationContext(), "Email is Required!", Toast.LENGTH_SHORT).show();
                        else if (TextUtils.isEmpty(txtAddress.getText().toString()))
                            Toast.makeText(getApplicationContext(), "Address is Required!", Toast.LENGTH_SHORT).show();
                        else {
                            user.setFullName(txtFullName.getText().toString().trim());
                            if (!ValidateNIC(txtNIC.getText().toString().trim())){
                                Toast.makeText(getApplicationContext(), "NIC is invalid!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            user.setNIC(txtNIC.getText().toString().trim());
                            //user.setEmail(txtEmail.getText().toString().trim());
                            //user.setPwd(txtPwd.getText().toString().trim());
                            user.setAddress(txtAddress.getText().toString().trim());
                            // DbRef.child("user").setValue(user);
                            String strNumber= idPrefix+String.valueOf(maxid+1);
                            DbRef.child(String.valueOf(strNumber)).setValue(user);
                            Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                            clearControl();

                            Intent i = new Intent(getApplicationContext(),LogIn_Page.class);
                            startActivity(i);


                        }


                    } catch (NumberFormatException e) {

                        Toast.makeText(getApplicationContext(), " wrong Inserted", Toast.LENGTH_SHORT).show();


                    }



                }




            public void clearControl() {
                txtFullName.setText("");
                txtNIC.setText("");
                txtEmail.setText("");
                txtAddress.setText("");
                txtPwd.setText("");
            }





        });}






    public boolean ValidateNIC(String nic){
        if (!nic.trim().matches("^[0-9]{9}[vVxX]$"))
            return false;
        return true;
    }
}