package com.example.crud.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crud.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText etUsername, etNoHp, etPassword;
    private TextView tvPilihPetugas;
    private Button btnRegister, btnPilihPetugas ;
    private Spinner spArea;
    private ArrayAdapter<CharSequence> kostAdapter;
    private DatabaseReference database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etNoHp = findViewById(R.id.etNoHp);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        spArea = findViewById(R.id.kost);
        kostAdapter = ArrayAdapter.createFromResource(this, R.array.pilih_area,R.layout.spinner_layout);
        kostAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spArea.setAdapter(kostAdapter);



        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-4c8c7-default-rtdb.firebaseio.com/");


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String noHp = etNoHp.getText().toString();
                String password = etPassword.getText().toString();
                String areatugas = spArea.getSelectedItem().toString().trim();

                if (username.isEmpty() || noHp.isEmpty() || password.isEmpty() || areatugas.isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Harap isi Data Dahulu!!", Toast.LENGTH_SHORT).show();
                }else {
                    database = FirebaseDatabase.getInstance().getReference("Users");
                    database.child(username).child("noHp").setValue(noHp);
                    database.child(username).child("username").setValue(username);
                    database.child(username).child("password").setValue(password);
                    database.child(username).child("areatugas").setValue(areatugas);



                    Toast.makeText(getApplicationContext(), "Register Berhasil", Toast.LENGTH_SHORT).show();
                    Intent register = new Intent(getApplicationContext(), Login.class);
                    startActivity(register);
                }

            }
        });


    }
}