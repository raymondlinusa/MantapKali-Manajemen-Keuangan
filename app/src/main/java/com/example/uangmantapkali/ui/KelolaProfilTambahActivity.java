package com.example.uangmantapkali.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uangmantapkali.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class KelolaProfilTambahActivity extends AppCompatActivity {

    private EditText nama;
    private Button cancel, save;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_profil_tambah);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;

        getWindow().setLayout((int) (widthPixels*.8),(int) (heightPixels*.4));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;

        getWindow().setAttributes(params);

        nama = findViewById(R.id.txtNamaProfil);
        cancel = findViewById(R.id.btnCancel);
        save = findViewById(R.id.btnSave);

        save.setOnClickListener(v -> {
            Map<String, Object> docData = new HashMap<>();
            docData.put("nama", nama.getText());
            db.collection("users")
                    .document(mAuth.getUid())
                    .collection("profile")
                    .document().set(docData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(KelolaProfilTambahActivity.this, "Profil berhasil didaftarkan",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(KelolaProfilTambahActivity.this, "ERROR" + e.toString(),
                                    Toast.LENGTH_SHORT).show();
                            Log.d("TAG", e.toString());
                        }
                    });
            finish();
        });

        cancel.setOnClickListener(view -> finish());
    }
}