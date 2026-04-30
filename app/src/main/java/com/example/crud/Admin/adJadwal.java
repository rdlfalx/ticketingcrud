package com.example.crud.Admin;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adJadwal extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterGambar adapter;

    private ArrayList<DataClass> dataClasses; // Corrected to DataClass
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Images");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_jadwal);

        recyclerView = findViewById(R.id.rvadJadwal);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataClasses = new ArrayList<>(); // Corrected to DataClass
        adapter = new AdapterGambar(this, dataClasses, true);// Corrected to DataClass
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataClasses.clear(); // Clear the list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DataClass data = dataSnapshot.getValue(DataClass.class); // Corrected to DataClass
                    dataClasses.add(data); // Corrected to DataClass
                }
                adapter.notifyDataSetChanged(); // Notify adapter after updating the list
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event if needed
            }
        });
    }
}