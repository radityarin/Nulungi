package com.rai.nulungi;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class RincianPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Button btnkonfirmasi;

    private final int PICK_IMAGE_REQUEST = 1;
    private String nama,kategori,metode,urlbarang,namatempat,alamattempat;
    String tanggaldonasi ="";
    private ImageView uploadfotoproduk;
    private FirebaseAuth auth;
    private TextView tvnamabarang,tvmetode, tvnamatempat,tvalamattempat;
    private StorageReference imageStorage;
    private ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_page);

        nama = getIntent().getStringExtra("nama");
        kategori = getIntent().getStringExtra("kategori");
        metode = getIntent().getStringExtra("metode");

        tvnamabarang = findViewById(R.id.namabarang);
        tvmetode = findViewById(R.id.metodebarang);
        tvnamatempat = findViewById(R.id.namatempat);
        tvalamattempat = findViewById(R.id.alamattempat);

        tvnamabarang.setText(nama);
        tvmetode.setText(metode);

        Button btntgl = (Button) findViewById(R.id.buttontanggal);
        btntgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker ");
            }
        });

        String random = String.valueOf((int) (Math.random()*13)+1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("tempat").child(random);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tempat tempat = dataSnapshot.getValue(Tempat.class);
                tvnamatempat.setText(tempat.getNama());
                tvalamattempat.setText(tempat.getAlamat());
                namatempat = tempat.getNama();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        imageStorage = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        namatempat = "";
        alamattempat = "";

        uploadfotoproduk = (ImageView) findViewById(R.id.uploadfotoproduk);
        uploadfotoproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadfotoproduk.setImageBitmap(bitmap);


//membuat folder di firebase storage
            final StorageReference filepath = imageStorage.child("Barang Donasi").child(auth.getUid()).child(UUID.randomUUID().toString() + ".jpg");

            Button button = (Button) findViewById(R.id.konfirmasibutton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PD = new ProgressDialog(RincianPage.this);
                    PD.setMessage("Loading...");
                    PD.setCancelable(true);
                    PD.setCanceledOnTouchOutside(false);
                    PD.show();
                    filepath.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return filepath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {

                                //mendapatkan link foto
                                Uri downloadUri = task.getResult();
                                urlbarang = downloadUri.toString();
                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                                String key = myRef.push().getKey();

                                Donasi donasi = new Donasi(key,nama,kategori,metode,namatempat,urlbarang,tanggaldonasi,"Sedang diproses",auth.getUid());
                                myRef.child("List Barang Donasi").child(key).setValue(donasi).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            PD.dismiss();
                                            Intent intent = new Intent(RincianPage.this, KonfirmasiPage.class);
                                            intent.putExtra("metode",metode);
                                            intent.putExtra("alamat",alamattempat);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(RincianPage.this, "Upload gagal, coba lagi", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            });


        }
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        tanggaldonasi=currentDateString;
        Button button = (Button) findViewById(R.id.buttontanggal);
        button.setText(currentDateString);
    }
}
