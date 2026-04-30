package com.example.crud.Users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListTicket extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView ivMenu;

    private SearchView searchView;
    private RecyclerView rvMenu, rvTicket;
    private DatabaseReference database;


    private TicketAdapter adapter;
    private ArrayList<Ticket> arrayList;

    private FloatingActionButton fab_add, fabTkt, fabGuide;

    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ticket);

        drawerLayout = findViewById(R.id.drawer);
        ivMenu = findViewById(R.id.ivMenu);
        rvMenu = findViewById(R.id.rvMenus);
        searchView = findViewById(R.id.svRv);
        fab_add = findViewById(R.id.flbtn);
        fabTkt = findViewById(R.id.fabTkt);
        fabGuide =findViewById(R.id.fabGuide);
        fabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationFab();

            }
        });

        fabTkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationFab();
                startActivity(new Intent(getApplicationContext(), Buat_tiket.class));
                Toast.makeText(ListTicket.this, "Engineer On Board", Toast.LENGTH_SHORT).show();
            }
        });

        fabGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationFab();
                Toast.makeText(ListTicket.this, "Let's Make History", Toast.LENGTH_SHORT).show();
            }
        });



        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setAdapter(new MainDrawerAdapter(this, MainActivity.arrayList, MainActivity.image));
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        database = FirebaseDatabase.getInstance().getReference("Ticket");
        rvTicket = findViewById(R.id.rvTicket);
        rvTicket.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvTicket.setLayoutManager(layoutManager);
        rvTicket.setItemAnimator(new DefaultItemAnimator());

        tampilTicket();

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

    private void animationFab() {
        if(isOpen){
            fab_add.startAnimation(rotateForward);
            fabTkt.startAnimation(fabClose);
            fabGuide.startAnimation(fabClose);
            fabTkt.setClickable(false);
            fabGuide.setClickable(false);
            isOpen=false;
        }

        else {
            fab_add.startAnimation(rotateBackward);
            fabTkt.startAnimation(fabOpen);
            fabGuide.startAnimation(fabOpen);
            fabTkt.setClickable(true);
            fabGuide.setClickable(true);
            isOpen=true;
        }
    }

    private void tampilTicket() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList = new ArrayList<>();

                for (DataSnapshot item : snapshot.getChildren()) {
                    Ticket ticket = item.getValue(Ticket.class);
                    arrayList.add(ticket);
                }

                adapter = new TicketAdapter(arrayList, ListTicket.this, false);
                rvTicket.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);

    }
}