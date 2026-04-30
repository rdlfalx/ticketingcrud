package com.example.crud.Users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud.Admin.AdapterGambar;
import com.example.crud.Admin.DataClass;
import com.example.crud.Admin.adGuide;
import com.example.crud.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView ivMenu;
    private RecyclerView rvMenu;
    static ArrayList<String> arrayList = new ArrayList<>();
    static ArrayList<Integer> image = new ArrayList<>();
    private MainDrawerAdapter adapter;
    private FloatingActionButton fab_add, fabTkt, fabGuide;
    private RecyclerView recyclerView;
    private ArrayList<DataClass> dataList;

    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;
    private AdapterGambar adaptergbr;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Images");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        ivMenu = findViewById(R.id.ivMenu);
        rvMenu = findViewById(R.id.rvMenus);
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
                Toast.makeText(MainActivity.this, "Engineer On Board", Toast.LENGTH_SHORT).show();
            }
        });

        fabGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationFab();
                startActivity(new Intent(getApplicationContext(), adGuide.class));
                Toast.makeText(MainActivity.this, "Let's Make History", Toast.LENGTH_SHORT).show();
            }
        });


        arrayList.clear();

        arrayList.add("Home");
        arrayList.add("List Ticket");
        arrayList.add("Guide");
        arrayList.add("LogOut");

        image.add(R.drawable.baseline_home_24);
        image.add(R.drawable.baseline_list_24);
        image.add(R.drawable.baseline_edit_document_24);
        image.add(R.drawable.baseline_logout_24);


        adapter = new MainDrawerAdapter(this, arrayList, image);

        rvMenu.setLayoutManager(new LinearLayoutManager(this));

        rvMenu.setAdapter(adapter);

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        recyclerView = findViewById(R.id.rvGambar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        adaptergbr = new AdapterGambar(this, dataList, false);
        recyclerView.setAdapter(adaptergbr);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DataClass dataClass = dataSnapshot.getValue(DataClass.class);
                    dataList.add(dataClass);
                }
                adaptergbr.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onPause() {

        super.onPause();
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void animationFab(){
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

}