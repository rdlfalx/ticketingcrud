package com.example.crud.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crud.Register.Login;
import com.example.crud.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Admin extends AppCompatActivity {

    private ImageButton userBtn, tiketBtn, guideBtn, jadwalBtn;
    private Button btnmnJadwal;
    private FloatingActionButton exitBtn;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userBtn = findViewById(R.id.userBtn);
        tiketBtn = findViewById(R.id.tiketBtn);
        guideBtn = findViewById(R.id.guideBtn);
        jadwalBtn = findViewById(R.id.jadwalBtn);
        exitBtn = findViewById(R.id.exitBtn);
        btnmnJadwal = findViewById(R.id.btnmnJadwal);

        fabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationFab();
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
                builder.setTitle("Keluar");
                builder.setMessage("Kamu Yakin Ingin Sign Out?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Admin.this, Login.class));
                        finish(); // Optional: Finish the current activity after logging out
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });



        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), adUser.class));

            }
        });

        tiketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), adTicket.class));
            }
        });

        guideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), adGuide.class));

            }
        });

        jadwalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), uploadGambar.class));

            }
        });

        btnmnJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), adJadwal.class));
            }
        });

    }
    private void animationFab(){
        if(isOpen){
            exitBtn.startAnimation(rotateForward);
            isOpen=false;
        }

        else {
            exitBtn.startAnimation(rotateBackward);
            isOpen=true;
        }
    }
}