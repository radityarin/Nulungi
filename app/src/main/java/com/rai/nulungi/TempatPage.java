package com.rai.nulungi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class TempatPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempat_page);


        Button btnBack = findViewById(R.id.backbutton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TempatPage.this, ListPerKategoriPage.class));
            }
        });

        String urlfoto = getIntent().getStringExtra("urlfoto");
        String namatempat = getIntent().getStringExtra("namatempat");
        String alamattempat = getIntent().getStringExtra("alamattempat");
        String notelepon = getIntent().getStringExtra("notelepon");

        ImageView ivurl = findViewById(R.id.urltempat);
        Picasso.get().load(urlfoto).into(ivurl);
        TextView tvnama = findViewById(R.id.namatempat);
        tvnama.setText(namatempat);
        TextView tvalamat = findViewById(R.id.alamattempat);
        tvalamat.setText(alamattempat);
        TextView tvnotelepon = findViewById(R.id.notelepon);
        tvnotelepon.setText(notelepon);
    }
}
