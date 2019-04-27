package com.rai.nulungi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class TempatPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_page);

        final ArrayList<Tempat> listtempat = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Detail Tempat");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Tempat mLokasi = dt.getValue(Tempat.class);
                    listtempat.add(mLokasi);
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_lokasi);
                recyclerView.setAdapter(new AdapterLokasi(listtempat, getApplicationContext()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        Button btnBack = findViewById(R.id.backbutton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TempatPage.this, ListPerKategoriPage.class));
            }
        });

        String urlfoto = getIntent().getStringExtra("urlfoto");
        final String namatempat = getIntent().getStringExtra("namatempat");
        final String alamattempat = getIntent().getStringExtra("alamattempat");
        final String kebutuhan = getIntent().getStringExtra("kebutuhan");
        String notelepon = getIntent().getStringExtra("notelepon");

        ImageView ivurl = findViewById(R.id.urltempat);
        Picasso.get().load(urlfoto).into(ivurl);
        TextView tvnama = findViewById(R.id.namatempat);
        tvnama.setText(namatempat);
        TextView tvalamat = findViewById(R.id.alamattempat);
        tvalamat.setText(alamattempat);
        TextView tvnotelepon = findViewById(R.id.notelepon);
        tvnotelepon.setText(notelepon);
        TextView kebutuhantv = findViewById(R.id.kebutuhan);
        kebutuhantv.setText(kebutuhan);

        Button donasi = findViewById(R.id.donasibutton);
        donasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TempatPage.this,SumbangPage.class);
                intent.putExtra("namatempat",namatempat);
                intent.putExtra("alamattempat",alamattempat);
                startActivity(intent);
            }
        });
    }
}
