package com.rai.nulungi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class ListPerKebutuhan extends AppCompatActivity {

    FirebaseRecyclerAdapter<Tempat,ListPerKebutuhan.TempatViewHolder> tempatadapter;
    DatabaseReference produkRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_per_kebutuhan);

        Button btnBack = findViewById(R.id.backbutton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListPerKebutuhan.this, MainActivity.class));
            }
        });

        final String barang = getIntent().getStringExtra("barang");
        TextView title = findViewById(R.id.title);
        title.setText(barang);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        produkRef = FirebaseDatabase.getInstance().getReference().child("tempat");
        Query query = produkRef.orderByChild("kebutuhan").equalTo(barang.toLowerCase());
        FirebaseRecyclerOptions<Tempat> options =
                new FirebaseRecyclerOptions.Builder<Tempat>()
                        .setQuery(query, Tempat.class)
                        .build();

        tempatadapter = new FirebaseRecyclerAdapter<Tempat, ListPerKebutuhan.TempatViewHolder>(options) {
            @Override
            public ListPerKebutuhan.TempatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_lokasi_horizontal, parent, false);
                return new ListPerKebutuhan.TempatViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ListPerKebutuhan.TempatViewHolder holder, int position, final Tempat model) {
                holder.display(model.getNama(), model.getUrlfoto(), model.getNotelepon(), model.getAlamat());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ListPerKebutuhan.this,SumbangPage.class);
                        intent.putExtra("barang",barang);
                        intent.putExtra("namatempat",model.getNama());
                        intent.putExtra("alamattempat",model.getAlamat());
                        intent.putExtra("kordinat",model.getKordinat());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(tempatadapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        tempatadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tempatadapter.stopListening();
    }

    public class TempatViewHolder extends RecyclerView.ViewHolder {

        View view;

        public TempatViewHolder(View itemView) {
            super(itemView);

            view = itemView;

        }

        public void display(String namaTempat,String urlPhoto, String notelepon, String alamatTempat){
            TextView namatempat = (TextView) view.findViewById(R.id.namapanti);
            namatempat.setText(namaTempat);
            TextView noTelepon = (TextView) view.findViewById(R.id.notelepon);
            noTelepon.setText(notelepon);
            ImageView fototempat = (ImageView) view.findViewById(R.id.fotoPanti);
            Picasso.get().load(urlPhoto).into(fototempat);
            TextView alamat = (TextView) view.findViewById(R.id.alamat);
            alamat.setText(alamatTempat);
        }

    }
}
