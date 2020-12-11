package com.example.uangmantapkali.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uangmantapkali.R;

import java.util.List;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {
    private Context context;
    private List<String> profileList;

    public ProfileListAdapter(Context context, List<String> profileList) {
        this.context = context;
        this.profileList = profileList;
    }

    @NonNull
    @Override
    public ProfileListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_kelola_profil,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ProfileListAdapter.ViewHolder holder, int position) {
        String m = profileList.get(position);

        holder.nama.setText(m);
    }

    @Override
    public int getItemCount() {
        return this.profileList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nama;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.textNamaProfil);
        }
    }
}

