package com.example.crud.Users;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.crud.R;
import com.example.crud.Register.MultipleChoiceDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Buat_tiket extends AppCompatActivity implements MultipleChoiceDialogFragment.onMultiChoiceListener{
    private EditText etNoticket, etProblem, etSolve, etLokasi, etJam;

    private TextView tvPilihPetugas;
    private ImageButton btnPilihPetugas, btnTanggal;
    private Spinner shift, area;
    private Button btnBuattkt, btnBatal;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    private ArrayAdapter<CharSequence> shiftAdapter, areaAdapter;
    private DatabaseReference database;
    private Ticket ticket;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_tiket);

        etNoticket = findViewById(R.id.etNoticket);
        etProblem = findViewById(R.id.etProblem);
        etSolve = findViewById(R.id.etSolve);
        etLokasi = findViewById(R.id.etLokasi);
        etJam = findViewById(R.id.etJam);
        dateFormatter = new SimpleDateFormat("dd-mm-yy");
        etJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        tvPilihPetugas = findViewById(R.id.tvPilihPetugas);

        shift = findViewById(R.id.shift);
        shiftAdapter = ArrayAdapter.createFromResource(this, R.array.pilih_shift,R.layout.spinner_layout);
        shiftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shift.setAdapter(shiftAdapter);

        area = findViewById(R.id.area);
        areaAdapter = ArrayAdapter.createFromResource(this, R.array.pilih_area,R.layout.spinner_layout);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        area.setAdapter(areaAdapter);

        btnPilihPetugas = findViewById(R.id.btnPilihPetugas);
        btnPilihPetugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment multiChoiceDialog = new MultipleChoiceDialogFragment();
                multiChoiceDialog.setCancelable(false);
                multiChoiceDialog.show(getSupportFragmentManager(),"Pilih Petugas");
            }
        });



        btnBuattkt = findViewById(R.id.btnBuattkt);
        btnBatal = findViewById(R.id.btnBatal);
        ticket = new Ticket();
        database = FirebaseDatabase.getInstance().getReference().child("Ticket");

        /*database.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot childSnapshot : snapshot.getChildren()){
                        String lastKey = childSnapshot.getKey();
                        int maxId = Integer.parseInt(lastKey.split("-")[1]);
                        maxid = maxId;

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); */



        btnBuattkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noTicket = etNoticket.getText().toString().trim();
                String problem = etProblem.getText().toString().trim();
                String solve = etSolve.getText().toString().trim();
                String lokasi = etLokasi.getText().toString().trim();
                String petugas = tvPilihPetugas.getText().toString().trim();
                String areaTugas = area.getSelectedItem().toString().trim();
                String shiftTugas = shift.getSelectedItem().toString().trim();
                String jamTugas = etJam.getText().toString().trim();

                // Validasi input
                if (noTicket.isEmpty() || problem.isEmpty() || solve.isEmpty() || lokasi.isEmpty() ||
                        petugas.isEmpty() || areaTugas.isEmpty() || shiftTugas.isEmpty() || jamTugas.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Harap isi semua data terlebih dahulu!", Toast.LENGTH_SHORT).show();
                } else {
                    // Inisialisasi database
                    database = FirebaseDatabase.getInstance().getReference("Ticket");

                    // Mengatur atribut ticket
                    ticket.setNoTicket(noTicket);
                    ticket.setProblem(problem);
                    ticket.setSolve(solve);
                    ticket.setLokasi(lokasi);
                    ticket.setPetugas(petugas);
                    ticket.setArea(areaTugas);
                    ticket.setShift(shiftTugas);
                    ticket.setJam(jamTugas);

                    // Menyimpan data ticket di database menggunakan noTicket sebagai kunci
                    database.child(noTicket).setValue(ticket)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(Buat_tiket.this, "Ticket Berhasil Dibuat", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ListTicket.class);
                                startActivity(intent);
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(Buat_tiket.this, "Gagal Membuat Ticket: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month += 1;

                String formattedDate = String.format(Locale.getDefault(), "%02d-%02d-%02d", dayOfMonth, month, year);
                etJam.setText(formattedDate);
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    @Override
    public void onPositiveButtonClicked(String[] list, ArrayList<String> Petugas) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        for(String str:Petugas){
            stringBuilder.append(str+" ");
        }
        tvPilihPetugas.setText(stringBuilder);
    }

    @Override
    public void onNegativeButtonClicked() {
        tvPilihPetugas.setText("Harus Pilih");

    }
}