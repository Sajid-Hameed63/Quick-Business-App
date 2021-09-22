package com.example.quickbusiness.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickbusiness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn_Activity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button logIn;
    private TextView signUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logIn = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.btnSignUpInLogIn);
        editTextEmail = findViewById(R.id.etLogInEmailAddress);
        editTextPassword = findViewById((R.id.etLogInPassword));

        mAuth = FirebaseAuth.getInstance();
        logIn.setOnClickListener(v -> {
            logInUser();
        });
        signUp.setOnClickListener(v -> {
//            startActivity(new intent(LoginActivity.this, SignUpActivity.class));
            Intent intent=new Intent(LogIn_Activity.this, SignUp_Activity.class);
            startActivity(intent);
            finish();
        });
    }
    private void logInUser(){
        // log in code
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            editTextEmail.setError("Email cannot be empty!");
            editTextEmail.requestFocus();
        } else if(TextUtils.isEmpty(password)){
            editTextPassword.setError("Password cannot be empty!");
            editTextPassword.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LogIn_Activity.this, "Logged in successfully!", Toast.LENGTH_LONG).show();
//                        startActivity(new intent(LoginActivity.this, MainActivity.class));
                        Intent intent=new Intent(LogIn_Activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LogIn_Activity.this, "Logged in error!" + task.getException().getMessage() , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}