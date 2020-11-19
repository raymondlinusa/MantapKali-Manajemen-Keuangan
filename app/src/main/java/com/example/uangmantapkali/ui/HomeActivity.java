package com.example.uangmantapkali.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uangmantapkali.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    private Button logout;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private SharedPreferences session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        logout = findViewById(R.id.buttonLogout);

        logout.setOnClickListener(v -> {
            mAuth.signOut();
            finish();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() == null) {
            finish();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}