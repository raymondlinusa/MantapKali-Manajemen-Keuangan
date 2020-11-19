package com.example.uangmantapkali.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.uangmantapkali.R;
import com.example.uangmantapkali.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private EditText name;
    private EditText phone;
    private EditText dateOfBirth;
    private EditText email;
    private EditText password;
    private EditText passwordConfirm;
    private Button register;
    private Button login;
    private ProgressBar progress;
    private Calendar myCalendar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private SharedPreferences session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        myCalendar = Calendar.getInstance();
        session = getSharedPreferences("user", Context.MODE_PRIVATE);

        name = findViewById(R.id.editTextName);
        phone = findViewById(R.id.editTextPhone);
        dateOfBirth = findViewById(R.id.editTextDate);
        password = findViewById(R.id.editTextPassword);
        passwordConfirm = findViewById(R.id.editTextPasswordConfirm);
        email = findViewById(R.id.editTextEmail);
        register = findViewById(R.id.buttonRegister);
        progress = findViewById(R.id.progressBar);

        progress.setVisibility(View.GONE);

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            dateOfBirth.setText(sdf.format(myCalendar.getTime()));
        };

        dateOfBirth.setOnClickListener(v -> new DatePickerDialog(RegisterActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        register.setOnClickListener(v -> {
            if(name.getText().toString().isEmpty()) {
                name.setError("Name cannot be empty");
                return;
            }
            if(phone.getText().toString().isEmpty()) {
                phone.setError("Phone cannot be empty");
                return;
            }
            if(dateOfBirth.getText().toString().isEmpty()) {
                dateOfBirth.setError("Date of birth cannot be empty");
                return;
            }
            if(email.getText().toString().isEmpty()) {
                email.setError("Email cannot be empty");
                return;
            }
            if(password.getText().toString().isEmpty()) {
                password.setError("Password cannot be empty");
                return;
            }
            if(passwordConfirm.getText().toString().isEmpty() || passwordConfirm.getText().toString().equals(password.getText().toString())) {
                passwordConfirm.setError("Password Confirmation is wrong");
                return;
            }

            progress.setVisibility(View.VISIBLE);
            User newUser = new User(name.getText().toString(), phone.getText().toString(), dateOfBirth.getText().toString());

            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            db.collection("users")
                                    .document(user.getUid())
                                    .get()
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            SharedPreferences.Editor edit = session.edit();
                                            edit.clear();
                                            edit.putString("name", task1.getResult().get("name").toString());
                                            edit.apply();

                                            finish();
                                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "No data.",
                                                    Toast.LENGTH_SHORT).show();
                                            progress.setVisibility(View.GONE);
                                            mAuth.signOut();
                                        }
                                    });
                        } else {
                            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                    .addOnCompleteListener(result -> {
                                        if (result.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "signInWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();

                                            db.collection("users")
                                                    .document(user.getUid())
                                                    .set(newUser);

                                            SharedPreferences.Editor edit = session.edit();
                                            edit.clear();
                                            edit.putString("name", newUser.getName());
                                            edit.apply();

                                            finish();
                                            LoginActivity.fa.finish();
                                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                                            Toast.makeText(RegisterActivity.this, "Register failed.",
                                                    Toast.LENGTH_SHORT).show();
                                            progress.setVisibility(View.GONE);
                                            password.setText("");
                                        }
                                    });
                        }
                    });


        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null) {
            finish();
            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}