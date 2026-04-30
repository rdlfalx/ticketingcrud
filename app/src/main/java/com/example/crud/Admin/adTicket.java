package com.example.crud.Admin;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud.R;
import com.example.crud.Users.TicketAdapter;
import com.example.crud.Users.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adTicket extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView rvMenu, rvTicket;
    private DatabaseReference database;

    private TicketAdapter adapter;
    private ArrayList<Ticket>arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_ticket);

        searchView = findViewById(R.id.svRv);
        database = FirebaseDatabase.getInstance().getReference("Ticket");
        rvTicket = findViewById(R.id.rvTicket);
        rvTicket.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvTicket.setLayoutManager(layoutManager);
        rvTicket.setItemAnimator(new DefaultItemAnimator());

        tampilTicketAd();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

    }

    private void tampilTicketAd() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();

                for (DataSnapshot item : snapshot.getChildren()) {
                    Ticket ticket = item.getValue(Ticket.class);
                    arrayList.add(ticket);
                }

                adapter = new TicketAdapter(arrayList, adTicket.this, true);
                rvTicket.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}