package com.example.quickbusiness.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.quickbusiness.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email = findViewById(R.id.txtEmail);
        mAuth = FirebaseAuth.getInstance();
        email.setText(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());

    }
}