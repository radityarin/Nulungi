package com.rai.nulungi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SumbangPage extends AppCompatActivity {

    private Button btnnulung;
    private EditText edt_namabarang;
    private Spinner spn_kategori,spn_metode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumbang_page);

        edt_namabarang = findViewById(R.id.namabarang);
        spn_kategori = findViewById(R.id.inputkategori);
        spn_metode = findViewById(R.id.inputmetode);

        btnnulung = findViewById(R.id.nulungbutton);
        btnnulung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String nama = edt_namabarang.getText().toString();
                final String kategori = spn_kategori.getSelectedItem().toString();
                final String metode = spn_metode.getSelectedItem().toString();

                Intent intent = new Intent(SumbangPage.this,RincianPage.class);
                intent.putExtra("nama",nama);
                intent.putExtra("kategori",kategori);
                intent.putExtra("metode",metode);
                startActivity(intent);
            }
        });
    }

}
