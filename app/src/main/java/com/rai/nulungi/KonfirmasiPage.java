package com.rai.nulungi;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Locale;

import static android.content.ContentValues.TAG;

public class KonfirmasiPage extends AppCompatActivity {

    private Button btnselesai;
    private LinearLayout lljemput, llantar;
    private TextView alamattempat;
    String alamat, kordinat;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_page);


        alamattempat = findViewById(R.id.alamattempat);
        TextView tglpenjemputan = findViewById(R.id.tanggalpenjemputan);

        lljemput = findViewById(R.id.lljemput);
        llantar = findViewById(R.id.llantar);
        String metode = getIntent().getStringExtra("metode");
        alamat = getIntent().getStringExtra("alamat");
        kordinat = getIntent().getStringExtra("kordinat");
        String tanggaldonasi = getIntent().getStringExtra("tanggaldonasi");
        if (metode.equals("Jemput di rumah")) {
            lljemput.setVisibility(View.VISIBLE);
            tglpenjemputan.setText(tanggaldonasi);
        } else {
            Button test = findViewById(R.id.test);
            test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String latilongi[] = kordinat.split(",");
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%s,%s (%s)", latilongi[0], latilongi[1], "Where the party is at");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }
            });
            llantar.setVisibility(View.VISIBLE);
            alamattempat.setText(alamat);
        }

        btnselesai = findViewById(R.id.selesaibutton);
        btnselesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KonfirmasiPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
