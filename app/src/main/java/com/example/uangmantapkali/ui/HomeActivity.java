package com.example.uangmantapkali.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.uangmantapkali.R;
import com.example.uangmantapkali.ui.fragment.homeFragment;
import com.example.uangmantapkali.ui.fragment.pemasukanFragment;
import com.example.uangmantapkali.ui.fragment.pengeluaranFragment;
import com.example.uangmantapkali.ui.fragment.profileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton floatingActionButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private SharedPreferences session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        session = getSharedPreferences("user", Context.MODE_PRIVATE);

        bottomNavigationView = findViewById(R.id.bottomNavBar);
        floatingActionButton = findViewById(R.id.fabTransaksi);
        bottomNavigationView.setBackgroundColor(0);

        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddTransaksiActivity.class);
            startActivity(intent);
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homeFragment(HomeActivity.this)).commit();
        bottomMenu();
    }

    private void bottomMenu() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.menuHome:
                    fragment = new homeFragment(HomeActivity.this);
                    break;
                case R.id.menuIncome:
                    fragment = new pemasukanFragment(HomeActivity.this);
                    break;
                case R.id.menuOutcome:
                    fragment = new pengeluaranFragment(HomeActivity.this);
                    break;
                case R.id.menuProfile:
                    fragment = new profileFragment(HomeActivity.this);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
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