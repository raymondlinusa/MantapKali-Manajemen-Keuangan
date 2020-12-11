package com.example.uangmantapkali.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.uangmantapkali.R;
import com.example.uangmantapkali.ui.KelolaProfilActivity;
import com.example.uangmantapkali.ui.LoginActivity;
import com.example.uangmantapkali.ui.RegisterActivity;

public class homeFragment extends Fragment implements View.OnClickListener {

    private Context mContext;

    public homeFragment(Context context) {
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnProfil = view.findViewById(R.id.buttonKelolaProfil);
        btnProfil.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonKelolaProfil:
                Intent intent = new Intent(this.mContext, KelolaProfilActivity.class);
                startActivity(intent);
                break;
        }
    }
}