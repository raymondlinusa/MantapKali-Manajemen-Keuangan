package com.example.uangmantapkali.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uangmantapkali.R;
import com.example.uangmantapkali.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText email;
    private EditText password;
    private TextView register;
    private Button login;
    private ProgressBar progress;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private SharedPreferences session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        session = getSharedPreferences("user", Context.MODE_PRIVATE);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        register = findViewById(R.id.textViewRegister);
        login = findViewById(R.id.buttonLogin);
        progress = findViewById(R.id.progressBar);

        progress.setVisibility(View.GONE);

        login.setOnClickListener(v -> {
            if(email.getText().toString().isEmpty()) {
                email.setError("Email cannot be empty");
                return;
            }
            if(password.getText().toString().isEmpty()) {
                password.setError("Password cannot be empty");
                return;
            }

            progress.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            db.collection("users")
                                    .document(user.getUid())
                                    .get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        User user1 = documentSnapshot.toObject(User.class);
                                        SharedPreferences.Editor edit = session.edit();
                                        edit.clear();
                                        edit.putString("name", user1.getName());
                                        edit.apply();

                                        finish();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(LoginActivity.this, "No data.",
                                                Toast.LENGTH_SHORT).show();
                                        progress.setVisibility(View.GONE);
                                        mAuth.signOut();
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Credential not found.",
                                    Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                            password.setText("");
                            mAuth.signOut();
                        }
                    });
        });

        register.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null) {
            finish();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}