package com.example.quickbusiness.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickbusiness.Models.User;
import com.example.quickbusiness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SignUp_Activity extends AppCompatActivity {

    Button SignUp;
    EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    TextView loginText;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();


        SignUp = findViewById(R.id.btnSignUp);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });

        editTextEmail = (EditText) findViewById(R.id.etEmailAddress);
        editTextPassword = (EditText) findViewById(R.id.etPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        loginText = findViewById(R.id.btnLogin);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp_Activity.this, LogIn_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signUpUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please, Enter Valid Email!");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Minimum password length should be 6!");
            editTextPassword.requestFocus();
            return;
        }
        if(!password.equals(confirmPassword)){
            editTextConfirmPassword.setError("Both Password section must be Same!!");
            editTextConfirmPassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
//                            User user = new User(email, password);
                            sendDataToRealTimeDB(email, password);
//                            FirebaseDatabase.getInstance().getReference("Users")
////                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .setValue(user);
                            Toast.makeText(SignUp_Activity.this, "Signed Up successfully!", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(SignUp_Activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(SignUp_Activity.this, "Sign Up failed..Try again!", Toast.LENGTH_LONG).show();
                        }
                    }

                    private void sendDataToRealTimeDB(String email, String password) {
                        // Write a message to the database
//                        FirebaseDatabase database = FirebaseDatabase.getInstance();
//                        DatabaseReference myRef = database.getReference("Users");
//                        User user = new User(email,password);
//                        myRef.child().setValue(user);
//                        myRef.setValue("Hello, World!");
//                        FirebaseAuth mAuth;
//                        mAuth =
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String timeStamp  = dateFormat.format(new Date());

//                        String uid = mAuth.getUid();
                        String uid = password + "_" + timeStamp;
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("UID", uid);
                        hashMap.put("Email", email);
                        hashMap.put("Password", password);

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                        if(uid != null){
                            reference.child(uid)
                                    .setValue(hashMap);
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        progressDialog.dismiss();
//                                        Toast.makeText(RegisterActivity.this, "Account created Successfully", Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(RegisterActivity.this, DashboardUserActivity.class));
//                                        finish();
//
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(Exception e) {
//                                        progressDialog.dismiss();
//                                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                        }
                    }
                });
    }
}