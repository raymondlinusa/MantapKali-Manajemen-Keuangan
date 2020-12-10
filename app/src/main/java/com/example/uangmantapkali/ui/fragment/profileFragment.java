package com.example.uangmantapkali.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uangmantapkali.R;
import com.example.uangmantapkali.ui.ProfileActivity;

public class profileFragment extends Fragment {
    private TextView editProfil, settingProfil, about;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        editProfil = rootView.findViewById(R.id.txtEdit);
        settingProfil = rootView.findViewById(R.id.txtSetting);
        about = rootView.findViewById(R.id.txtAbout);

        editProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        settingProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return rootView;
    }
}