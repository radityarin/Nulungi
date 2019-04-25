package com.rai.nulungi;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterLokasi extends RecyclerView.Adapter<AdapterLokasi.ViewHolder> {

    private ArrayList<Tempat> listlokasi;
    private Context context;

    public AdapterLokasi(ArrayList<Tempat> listlokasi, Context context) {
        this.listlokasi = listlokasi;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_lokasi, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_nama.setText(listlokasi.get(position).getNama());
        holder.tv_alamat.setText(listlokasi.get(position).getAlamat());
        Picasso.get().load(listlokasi.get(position).getUrlfoto()).into(holder.iv_url);
        holder.cv_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,TempatPage.class);
                intent.putExtra("namatempat",listlokasi.get(position).getNama());
                intent.putExtra("urlfoto",listlokasi.get(position).getUrlfoto());
                intent.putExtra("notelepon",listlokasi.get(position).getNotelepon());
                intent.putExtra("alamattempat",listlokasi.get(position).getAlamat());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listlokasi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_url;
        private TextView tv_nama,tv_alamat;
        private CardView cv_lokasi;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_url = (ImageView) itemView.findViewById(R.id.urltempat);
            tv_nama = (TextView) itemView.findViewById(R.id.namatempat);
            tv_alamat = (TextView) itemView.findViewById(R.id.alamattempat);
            cv_lokasi = (CardView) itemView.findViewById(R.id.cardlokasi);
        }
    }
}