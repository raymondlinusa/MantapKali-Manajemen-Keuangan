package com.example.uangmantapkali.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.uangmantapkali.R;
import com.example.uangmantapkali.ui.adapters.ProfileListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddTransaksiActivity extends Activity {
    private static final String TAG = "AddTransaksiActivity";
    private Spinner btnDompet;
    private ToggleButton btnJenisTransaksi, btnTanggal;
    private EditText deskripsi, harga;
    private Button cancel, save;

    private List<String> dompetList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaksi);

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

        btnJenisTransaksi = findViewById(R.id.btnJenisTransaksi);
        btnDompet = findViewById(R.id.btnDompet);
        btnTanggal = findViewById(R.id.btnTanggal);
        deskripsi = findViewById(R.id.txtDeskripsi);
        harga = findViewById(R.id.txtHarga);
        cancel = findViewById(R.id.btnCancel);
        save = findViewById(R.id.btnSave);

        dompetList.clear();
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
                                dompetList.add(document.getData().get("nama").toString());

                            }
                            adapter = new ArrayAdapter<>(AddTransaksiActivity.this,
                                    android.R.layout.simple_spinner_item, dompetList);
                            adapter.setDropDownViewResource(R.layout.padding_spinner);
                            btnDompet.setAdapter(adapter);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


        btnDompet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        myCalendar = Calendar.getInstance();
        date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            ToggleButton tanggal = findViewById(R.id.btnTanggal);
            String myFormat = "dd MMM yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            tanggal.setText(sdf.format(myCalendar.getTime()));
        };

        btnTanggal.setOnClickListener(v -> {
            new DatePickerDialog(AddTransaksiActivity.this, date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            btnTanggal.setChecked(false);
        });

        cancel.setOnClickListener(view -> finish());

    }
}