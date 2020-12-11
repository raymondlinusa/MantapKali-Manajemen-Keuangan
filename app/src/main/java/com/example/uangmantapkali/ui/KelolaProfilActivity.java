package com.example.uangmantapkali.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uangmantapkali.R;
import com.example.uangmantapkali.ui.adapters.ProfileListAdapter;
import com.example.uangmantapkali.utilities.RecyclerItemClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class KelolaProfilActivity extends AppCompatActivity {

    private static final String TAG = "KelolaProfilActivity";
    RecyclerView rvProfil;
    Button btnTambah;
    private List<String> profilList = new ArrayList<>();

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_profil);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        rvProfil = findViewById(R.id.recyclerViewProfil);
        btnTambah = findViewById(R.id.buttonTambahProfil);

        rvProfil.addOnItemTouchListener(
                new RecyclerItemClickListener(KelolaProfilActivity.this, rvProfil, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d(TAG, "Position item : "+position);

                        TextView nama = view.findViewById(R.id.textNamaProfil);

                        Log.d(TAG, "Nama : "+nama.getText());

                        String namaProfile = nama.getText().toString();

                        Button btnEdit = view.findViewById(R.id.buttonEditProfile);
                        Button btnHapus = view.findViewById(R.id.buttonHapusProfile);

                        btnEdit.setOnClickListener(v -> {

                            Intent intent = new Intent(KelolaProfilActivity.this, KelolaProfilEditActivity.class);
                            intent.putExtra("nama", namaProfile);
                            startActivity(intent);
                        });

                        btnHapus.setOnClickListener(v -> {
                            db.collection("users")
                                    .document(mAuth.getUid())
                                    .collection("profile")
                                    .whereEqualTo("nama", namaProfile)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    document.getReference().delete();
                                                }
                                                Toast.makeText(KelolaProfilActivity.this, "Mahasiswa berhasil dihapuskan",
                                                        Toast.LENGTH_SHORT).show();
                                            } else {
                                            }
                                        }
                                    });
                        });
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );

        btnTambah.setOnClickListener(v -> {
            Intent intent = new Intent(KelolaProfilActivity.this, KelolaProfilTambahActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        profilList.clear();
        db.collection("users")
                .document(mAuth.getUid())
                .collection("profile")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData().get("nama").toString());
                                profilList.add(document.getData().get("nama").toString());
                            }
                            ProfileListAdapter adapter = new ProfileListAdapter(KelolaProfilActivity.this, profilList);
                            rvProfil.setLayoutManager(new LinearLayoutManager(KelolaProfilActivity.this));
                            rvProfil.setAdapter(adapter);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}