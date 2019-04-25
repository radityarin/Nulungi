package com.rai.nulungi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class KonfirmasiPage extends AppCompatActivity {

    private Button btnselesai;
    private LinearLayout lljemput,llantar;
    private TextView alamattempat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_page);

        alamattempat=findViewById(R.id.alamattempat);

        lljemput=findViewById(R.id.lljemput);
        llantar=findViewById(R.id.llantar);
        String metode = getIntent().getStringExtra("metode");
        String alamat = getIntent().getStringExtra("alamattempat");
        if(metode.equals("Jemput di rumah")){
            lljemput.setVisibility(View.VISIBLE);
            alamattempat.setText(alamat);
        } else {
            llantar.setVisibility(View.VISIBLE);
        }

        btnselesai = findViewById(R.id.selesaibutton);
        btnselesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(KonfirmasiPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
