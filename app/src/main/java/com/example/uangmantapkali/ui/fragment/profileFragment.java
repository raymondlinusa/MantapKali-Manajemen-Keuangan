package com.example.uangmantapkali.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uangmantapkali.R;
import com.example.uangmantapkali.ui.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

public class profileFragment extends Fragment implements View.OnClickListener {
    private TextView username, email;
    private TextView editProfil, setting, about, logout;

    private Context mContext;
    private FirebaseAuth mAuth;
    private SharedPreferences session;

    public profileFragment(Context context) {
        this.mContext = context;
        session = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        username = rootView.findViewById(R.id.txtUser);
        email = rootView.findViewById(R.id.txtUserEmail);
        editProfil = rootView.findViewById(R.id.txtEdit);
        setting = rootView.findViewById(R.id.txtSetting);
        logout = rootView.findViewById(R.id.txtLogout);
        about = rootView.findViewById(R.id.txtAbout);

        username.setText(session.getString("name", ""));
        email.setText(session.getString("email", ""));

        editProfil.setOnClickListener(this);
        setting.setOnClickListener(this);
        logout.setOnClickListener(this);
        about.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.txtEdit:
                intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.txtLogout:
                mAuth.signOut();
                SharedPreferences.Editor edit = session.edit();
                edit.clear();
                edit.apply();
                break;
        }
    }
}