package com.rai.nulungi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SumbangPage extends AppCompatActivity {

    private Button btnnulung;
    private EditText edt_namabarang,edt_namadonatur;
    private Spinner spn_kategori,spn_metode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumbang_page);
        final String barang = getIntent().getStringExtra("barang");
        final String kordinat = getIntent().getStringExtra("kordinat");
        final String idtempat = getIntent().getStringExtra("idtempat");
//        Toast.makeText(SumbangPage.this,barang,Toast.LENGTH_LONG).show();
//        Toast.makeText(SumbangPage.this,idtempat,Toast.LENGTH_LONG).show();
//        Toast.makeText(SumbangPage.this,kordinat,Toast.LENGTH_LONG).show();
        Button btnBack = findViewById(R.id.backbutton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SumbangPage.this, MainActivity.class));
            }
        });
        String kategoribarang[] = {"Furnitur","Pakaian","Elektronik","Buku"};
        edt_namabarang = findViewById(R.id.namabarang);
        spn_kategori = findViewById(R.id.inputkategori);
        spn_metode = findViewById(R.id.inputmetode);
        edt_namadonatur = findViewById(R.id.namadonatur);
        int kategori = 0;
        for (int i = 0; i <kategoribarang.length ; i++) {
            if (kategoribarang[i].equals(barang)){
                kategori=i+1;
            }
        }
        spn_kategori.setSelection(kategori);

        btnnulung = findViewById(R.id.nulungbutton);
        btnnulung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nama = edt_namabarang.getText().toString();
                final String kategori = spn_kategori.getSelectedItem().toString();
                final String metode = spn_metode.getSelectedItem().toString();
                final String namatempat = getIntent().getStringExtra("namatempat");
                final String alamattempat = getIntent().getStringExtra("alamattempat");
                final String namadonatur = edt_namadonatur.getText().toString();
                Intent intent = new Intent(SumbangPage.this,RincianPage.class);
                intent.putExtra("nama",nama);
                intent.putExtra("kategori",kategori);
                intent.putExtra("metode",metode);
                intent.putExtra("namatempat",namatempat);
                intent.putExtra("alamattempat",alamattempat);
                intent.putExtra("kordinat",kordinat);
                intent.putExtra("idtempat",idtempat);
                intent.putExtra("namadonatur",namadonatur);
                startActivity(intent);
            }
        });
    }

}
