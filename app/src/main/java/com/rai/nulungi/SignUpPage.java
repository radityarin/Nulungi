package com.rai.nulungi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpPage extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText inputNama, inputEmail, inputNOKTP, inputPassword, inputNOHP;
    private Button btnDaftar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        auth = FirebaseAuth.getInstance();

        inputNama = findViewById(R.id.nama);
        inputEmail = findViewById(R.id.email);
        inputNOHP = findViewById(R.id.noHp);
        inputNOKTP = findViewById(R.id.noKtp);
        inputPassword = findViewById(R.id.password);
        btnDaftar = findViewById(R.id.tomboldaftar);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nama = inputNama.getText().toString();
                final String email = inputEmail.getText().toString();
                final String noktp = inputNOKTP.getText().toString();
                final String password = inputPassword.getText().toString();
                final String nohp = inputNOHP.getText().toString();

                try {
                    if (password.length() > 0 && email.length() > 0) {
                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(
                                                    SignUpPage.this,
                                                    "Authentication Failed",
                                                    Toast.LENGTH_LONG).show();
                                            Log.v("error", task.getResult().toString());
                                        } else {
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference myRef = database.getReference("Detail Pengguna").child(auth.getUid());
                                            Profil profil = new Profil(nama,email,noktp,nohp);

                                            myRef.setValue(profil);
                                            Intent intent = new Intent(SignUpPage.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(
                                SignUpPage.this,
                                "Fill All Fields",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
