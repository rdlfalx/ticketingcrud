package com.example.crud.Admin;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud.R;
import com.example.crud.Register.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adUser extends AppCompatActivity {

    private RecyclerView rvUser;
    private SearchView searchView;
    private DatabaseReference database;

    private UserAdapter adapter;
    private ArrayList<User> arrayList;

    private ArrayList<User> filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_user);

        rvUser = findViewById(R.id.rvUser);

        database = FirebaseDatabase.getInstance().getReference("Users");

        filteredList = new ArrayList<>();

        rvUser = findViewById(R.id.rvUser);
        rvUser.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvUser.setLayoutManager(layoutManager);
        rvUser.setItemAnimator(new DefaultItemAnimator());

        searchView = findViewById(R.id.svUv);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });


        tampilData();




    }

    private void tampilData() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();

                for (DataSnapshot item : snapshot.getChildren()){
                    User user = new User();
                    user.setUsername(item.child("username").getValue(String.class));
                    user.setNoHp(item.child("noHp").getValue(String.class));
                    user.setPassword(item.child("password").getValue(String.class));
                    user.setAreatugas(item.child("areatugas").getValue(String.class));
                    arrayList.add(user);
                }

                adapter = new UserAdapter(arrayList, adUser.this);
                rvUser.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void filter(String query) {
        filteredList.clear(); // Clear the filtered list before adding new filtered items

        for (User user : arrayList) {
            // Filter logic (you can customize this based on your requirements)
            if (user.getUsername().toLowerCase().contains(query.toLowerCase()) ||
                    user.getNoHp().toLowerCase().contains(query.toLowerCase()) ||
                    user.getAreatugas().toLowerCase().contains(query.toLowerCase()) ||
                    user.getPassword().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(user);
            }
        }

        adapter.filterList(filteredList); // Pass filtered list to adapter
    }
}