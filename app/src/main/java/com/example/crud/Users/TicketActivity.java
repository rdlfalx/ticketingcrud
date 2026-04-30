package com.example.crud.Users;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crud.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TicketActivity extends AppCompatActivity {

    private EditText editText;
    private Button btnCopy;
    private String noTicket, problem, solve, lokasi, petugas, area, shift, jam;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("Ticket");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        editText = findViewById(R.id.et);
        btnCopy = findViewById(R.id.btnCopy);


        if (getIntent().hasExtra("noTicket") && getIntent().hasExtra("problem") && getIntent().hasExtra("solve") && getIntent().hasExtra("lokasi" ) && getIntent().hasExtra("petugas" ) && getIntent().hasExtra("area" ) && getIntent().hasExtra("shift" ) && getIntent().hasExtra("jam" ) ){
            noTicket = getIntent().getStringExtra("noTicket");
            problem = getIntent().getStringExtra("problem");
            solve = getIntent().getStringExtra("solve");
            lokasi = getIntent().getStringExtra("lokasi");
            petugas = getIntent().getStringExtra("petugas");
            area = getIntent().getStringExtra("area");
            shift = getIntent().getStringExtra("shift");
            jam = getIntent().getStringExtra("jam");




            editText.setText
                    ("Dear HD Asyst" +"\n" + "\n"+ "\n"+
                            "Kami informasikan telah dilakukan onsite dan tiket sudah selesai dikerjakan " + "\n" + "\n" +
                            "Lokasi = " + lokasi + "\n" +
                            "Problem = " + problem + "\n" +
                            "solved = " + solve + "\n" +
                            "Ticket dapat di CLOSED" + "\n" + "\n" + "\n"+
                            "Demikian informasi ini kami sampaikan" + "\n" +
                            "Regards," + "\n" +
                            petugas + "\n"+
                            area);


    }
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied", editText.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(TicketActivity.this, "Copied", Toast.LENGTH_SHORT).show();

            }
        });


}
}