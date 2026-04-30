package com.example.crud.Admin;

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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import com.example.crud.R;
import com.example.crud.Register.MultipleChoiceDialogFragment;
import com.example.crud.Users.Ticket;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class EditTicket extends AppCompatActivity {

    private EditText etNoticket, etProblem, etSolve, etLokasi, etJam;

    private TextView tvPilihPetugas;
    private ImageButton btnPilihPetugas, btnTanggal;
    private Spinner shift, area;
    private Button btnSave, btnBatal;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    private ArrayAdapter<CharSequence> shiftAdapter, areaAdapter;
    private Ticket ticket;
    private String noTicket, problem, solve, lokasi, petugas, areaad, shiftad, jam;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("Ticket");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ticket);
        etNoticket = findViewById(R.id.etNoticket);
        etProblem = findViewById(R.id.etProblem);
        etSolve = findViewById(R.id.etSolve);
        etLokasi = findViewById(R.id.etLokasi);
        btnSave = findViewById(R.id.btnSave);
        btnBatal = findViewById(R.id.btnBatal);
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
        shiftAdapter = ArrayAdapter.createFromResource(this, R.array.pilih_shift, R.layout.spinner_layout);
        shiftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shift.setAdapter(shiftAdapter);

        area = findViewById(R.id.area);
        areaAdapter = ArrayAdapter.createFromResource(this, R.array.pilih_area, R.layout.spinner_layout);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        area.setAdapter(areaAdapter);

        btnPilihPetugas = findViewById(R.id.btnPilihPetugas);
        btnPilihPetugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment multiChoiceDialog = new MultipleChoiceDialogFragment();
                multiChoiceDialog.setCancelable(false);
                multiChoiceDialog.show(getSupportFragmentManager(), "Pilih Petugas");
            }
        });



        if (getIntent().hasExtra("noTicket") && getIntent().hasExtra("problem") && getIntent().hasExtra("solve") && getIntent().hasExtra("lokasi") && getIntent().hasExtra("petugas") && getIntent().hasExtra("area") && getIntent().hasExtra("shift") && getIntent().hasExtra("jam")) {
            noTicket = getIntent().getStringExtra("noTicket");
            problem = getIntent().getStringExtra("problem");
            solve = getIntent().getStringExtra("solve");
            lokasi = getIntent().getStringExtra("lokasi");
            petugas = getIntent().getStringExtra("petugas");
            areaad = getIntent().getStringExtra("area");
            shiftad = getIntent().getStringExtra("shift");
            jam = getIntent().getStringExtra("jam");


        }

        etNoticket.setText(noTicket);
        etProblem.setText(problem);
        etSolve.setText(solve);
        etLokasi.setText(lokasi);
        tvPilihPetugas.setText(petugas);
        shift.setSelection(shiftAdapter.getPosition(shiftad));
        area.setSelection(areaAdapter.getPosition(areaad));
        etJam.setText(jam);

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin.class));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noTicketBaru = etNoticket.getText().toString();
                String problemBaru = etProblem.getText().toString();
                String solveBaru = etSolve.getText().toString();
                String lokasiBaru = etLokasi.getText().toString();
                String petugasBaru = tvPilihPetugas.getText().toString();
                String areaBaru = area.getSelectedItem().toString().trim();
                String shiftBaru = shift.getSelectedItem().toString().trim();
                String jamBaru = etJam.getText().toString().trim();


                HashMap hashMap = new HashMap();
                hashMap.put("noTicket", noTicketBaru);
                hashMap.put("problem", problemBaru);
                hashMap.put("solve", solveBaru);
                hashMap.put("lokasi", lokasiBaru);
                hashMap.put("petugas", petugasBaru);
                hashMap.put("area", areaBaru);
                hashMap.put("shift", shiftBaru);
                hashMap.put("jam", jamBaru);


                database.child(noTicketBaru).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                        Toast.makeText(getApplicationContext(), "Berhasil Dirubah", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Admin.class));

                    }
                });
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
}