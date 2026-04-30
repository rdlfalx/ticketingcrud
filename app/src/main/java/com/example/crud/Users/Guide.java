package com.example.crud.Users;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud.Admin.PdfFile;
import com.example.crud.Admin.PdfFileAdapter;
import com.example.crud.Admin.PdfListAdapter;
import com.example.crud.R;
import com.example.crud.Users.MainActivity;
import com.example.crud.Users.MainDrawerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Guide extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView ivMenu;
    private RecyclerView rvMenu, recyclerView;
    private SearchView svrv;
    private PdfListAdapter adapter;
    private List<PdfFile> pdfFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        drawerLayout = findViewById(R.id.drawer);
        ivMenu = findViewById(R.id.ivMenu);
        rvMenu = findViewById(R.id.rvMenus);
        recyclerView = findViewById(R.id.rvpdf);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        svrv = findViewById(R.id.svGuide);
        pdfFiles = new ArrayList<>();
        adapter = new PdfListAdapter(this, pdfFiles);
        recyclerView.setAdapter(adapter);

        svrv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        fetchPdfFiles();

        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setAdapter(new MainDrawerAdapter(this, MainActivity.arrayList, MainActivity.image));
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void fetchPdfFiles() {
        FirebaseDatabase.getInstance().getReference("pdfs")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            PdfFile pdfFile = dataSnapshot.getValue(PdfFile.class);
                            if (pdfFile != null) {
                                pdfFiles.add(pdfFile);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle onCancelled event if needed
                    }
                });
    }


    @Override
        protected void onPause() {
            super.onPause();
            MainActivity.closeDrawer(drawerLayout);

        }
}