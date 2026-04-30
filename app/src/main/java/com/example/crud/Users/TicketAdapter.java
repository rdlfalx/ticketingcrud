package com.example.crud.Users;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.crud.Admin.EditTicket;
import com.example.crud.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {

    private List<Ticket> list;
    private List<Ticket> originalList;
    private Context context;
    private boolean isAdmin;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("Ticket");

    public TicketAdapter(List<Ticket> list, Context context, boolean isAdmin) {
        this.list = list;
        this.originalList = new ArrayList<>(list);
        this.context = context;
        this.isAdmin = isAdmin;
    }

    public void filter(String searchText) {
        list.clear();
        if (searchText.trim().isEmpty()) {
            list.addAll(originalList); // Use the original unfiltered list
        } else {
            String query = searchText.toLowerCase().trim();
            for (Ticket ticket : originalList) {
                // Filter logic based on your requirements
                if (ticket.getNoTicket().toLowerCase().contains(query) ||
                        ticket.getProblem().toLowerCase().contains(query) ||
                        ticket.getPetugas().toLowerCase().contains(query)) {
                    list.add(ticket);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TicketAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (isAdmin) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ticket_ad, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ticket, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketAdapter.MyViewHolder holder, int position) {
        final Ticket item = list.get(position);
        holder.tvNoTicket.setText("No ticket : " + item.getNoTicket());
        holder.tvProblem.setText("Problem : " + item.getProblem());
        holder.tvPetugas.setText("Petugas : " + item.getPetugas());
        holder.tvSolve.setText("Solve : " + item.getSolve());
        holder.tvLokasi.setText("Lokasi : " + item.getLokasi());
        holder.tvArea.setText("Area : " + item.getArea());

        if (isAdmin) {
            holder.tvShift.setText("Shift : " + item.getShift());
            holder.tvJam.setText("Jam : " + item.getJam());

            holder.btnDetailAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailIntent = new Intent(context, TicketActivity.class);
                    detailIntent.putExtra("noTicket", item.getNoTicket());
                    detailIntent.putExtra("problem", item.getProblem());
                    detailIntent.putExtra("solve", item.getSolve());
                    detailIntent.putExtra("lokasi", item.getLokasi());
                    detailIntent.putExtra("petugas", item.getPetugas());
                    detailIntent.putExtra("area", item.getArea());
                    detailIntent.putExtra("shift", item.getShift());
                    detailIntent.putExtra("jam", item.getJam());
                    context.startActivity(detailIntent);
                }
            });

            holder.btnEditAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent editIntent = new Intent(context, EditTicket.class);
                    editIntent.putExtra("noTicket", item.getNoTicket());
                    editIntent.putExtra("problem", item.getProblem());
                    editIntent.putExtra("solve", item.getSolve());
                    editIntent.putExtra("lokasi", item.getLokasi());
                    editIntent.putExtra("petugas", item.getPetugas());
                    editIntent.putExtra("area", item.getArea());
                    editIntent.putExtra("shift", item.getShift());
                    editIntent.putExtra("jam", item.getJam());
                    context.startActivity(editIntent);
                }
            });

            holder.btnHapusAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    database.child(item.getNoTicket()).setValue(null);
                    Toast.makeText(context, "Ticket Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            holder.btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Your onClick logic
                    String noTicket = item.getNoTicket();
                    String problem = item.getProblem();
                    String solve = item.getSolve();
                    String lokasi = item.getLokasi();
                    String petugas = item.getPetugas();
                    String area = item.getArea();
                    String shift = item.getShift();
                    String jam = item.getJam();


                    Log.d("TicketAdapter", "noTicket: " + noTicket);
                    Log.d("TicketAdapter", "problem: " + problem);
                    Log.d("TicketAdapter", "lokasi: " + lokasi);
                    Log.d("TicketAdapter", "solve: " + solve);
                    Log.d("TicketAdapter", "area: " + area);

                    Intent copy = new Intent(context, TicketActivity.class);
                    copy.putExtra("noTicket", noTicket);
                    copy.putExtra("problem", problem);
                    copy.putExtra("solve", solve);
                    copy.putExtra("lokasi", lokasi);
                    copy.putExtra("petugas", petugas);
                    copy.putExtra("area", area);
                    copy.putExtra("shift", shift);
                    copy.putExtra("jam", jam);
                    context.startActivity(copy);
                }
            });
        }
    }

        @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNoTicket, tvProblem, tvPetugas, tvLokasi, tvSolve, tvArea;
        private TextView tvShift, tvJam;
        private Button btnDetailAd, btnEditAd, btnHapusAd, btnDetail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoTicket = itemView.findViewById(R.id.tvNoticket);
            tvProblem = itemView.findViewById(R.id.tvProblem);
            tvPetugas = itemView.findViewById(R.id.tvPetugas);
            tvLokasi = itemView.findViewById(R.id.tvLokasi);
            tvSolve = itemView.findViewById(R.id.tvSolve);
            tvArea = itemView.findViewById(R.id.tvArea);

            if (isAdmin) {
                tvShift = itemView.findViewById(R.id.tvShift);
                tvJam = itemView.findViewById(R.id.tvJam);
                btnDetailAd = itemView.findViewById(R.id.btnDetailAd);
                btnEditAd = itemView.findViewById(R.id.btnEditAd);
                btnHapusAd = itemView.findViewById(R.id.btnHapusAd);
            } else {
                btnDetail = itemView.findViewById(R.id.btnDetail);
            }
        }
    }
}