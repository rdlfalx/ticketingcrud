package com.example.crud.Register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crud.Admin.Admin;
import com.example.crud.R;
import com.example.crud.Users.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private Button btnRegister, btnLogin;
    private EditText etUsername, etPassword;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(getApplicationContext(), Register.class);
                startActivity(register);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                database = FirebaseDatabase.getInstance().getReference("Users");

                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username atau Password Salah", Toast.LENGTH_SHORT).show();
                } else if (username.equals("admin") && password.equals("admin")) {
                    Toast.makeText(getApplicationContext(), "Login Admin", Toast.LENGTH_SHORT).show();
                    Intent masuk = new Intent(getApplicationContext(), Admin.class);
                    startActivity(masuk);
                    
                } else{
                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(username).exists()){
                                if (snapshot.child(username).child("password").getValue(String.class).equals(password)){
                                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent masuk = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(masuk);
                                } else {
                                    // Show alert if the password is incorrect
                                    etPassword.setError("Password salah");
                                    showAlert("Password Salah", "Password yang Anda masukkan salah.");
                                }
                            } else {
                                // Show alert if the username doesn't exist
                                etUsername.setError("Username tidak ada");
                                showAlert("Username Tidak Ditemukan", "Username yang Anda masukkan tidak terdaftar.");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss the dialog if the user clicks OK
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}