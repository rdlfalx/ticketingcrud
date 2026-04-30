package com.example.crud.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crud.Admin.Admin;
import com.example.crud.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditDataUser extends AppCompatActivity {

    private EditText etUsername, etNoHp, etPassword, etAreaTugas;
    private Button btnSave, btnBatal;
    private String username, noHP, password, areatugas;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_user);

        etUsername = findViewById(R.id.etUsername);
        etNoHp = findViewById(R.id.etNoHp);
        etPassword = findViewById(R.id.etPassword);
        etAreaTugas = findViewById(R.id.etAreaTugas);
        btnSave = findViewById(R.id.btnSave);
        btnBatal = findViewById(R.id.btnBatal);

        if (getIntent().hasExtra("username") && getIntent().hasExtra("noHp") && getIntent().hasExtra("password") && getIntent().hasExtra("areatugas" ) ){
            username = getIntent().getStringExtra("username");
            noHP = getIntent().getStringExtra("noHp");
            password = getIntent().getStringExtra("password");
            areatugas = getIntent().getStringExtra("areatugas");


        }

        etUsername.setText(username);
        etNoHp.setText(noHP);
        etPassword.setText(password);
        etAreaTugas.setText(areatugas);

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin.class));

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noHpBaru = etNoHp.getText().toString();
                String passwordBaru = etPassword.getText().toString();
                String areatugasBaru = etAreaTugas.getText().toString();


                HashMap hashMap = new HashMap();
                hashMap.put("noHp", noHpBaru);
                hashMap.put("password", passwordBaru);
                hashMap.put("areatugas", areatugasBaru);


                database.child(username).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                        Toast.makeText(getApplicationContext(), "Berhasil Dirubah", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Admin.class));

                    }
                });
            }
        });
    }
}