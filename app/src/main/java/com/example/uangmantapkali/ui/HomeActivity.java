package com.example.uangmantapkali.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

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

//    private Button logout;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton floatingActionButton;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private SharedPreferences session;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavBar);
        floatingActionButton = findViewById(R.id.fabTransaksi);
        bottomNavigationView.setBackgroundColor(0);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homeFragment()).commit();
        bottomMenu();
    }

    private void bottomMenu() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.menuHome:
                        fragment = new homeFragment();
                        break;
                    case R.id.menuIncome:
                        fragment = new pemasukanFragment();
                        break;
                    case R.id.menuOutcome:
                        fragment = new pengeluaranFragment();
                        break;
                    case R.id.menuProfile:
                        fragment = new profileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                return true;
            }
        });

    }

}