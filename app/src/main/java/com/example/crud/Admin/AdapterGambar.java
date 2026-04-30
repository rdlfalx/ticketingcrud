package com.example.crud.Admin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.example.crud.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;

public class AdapterGambar extends RecyclerView.Adapter<AdapterGambar.MyViewHolder> {
    private ArrayList<DataClass> dataList;
    private Context context;
    private boolean isAdjadwal; // Flag to determine the type of view

    public AdapterGambar(Context context, ArrayList<DataClass> dataList, boolean isAdjadwal) {
        this.dataList = dataList;
        this.context = context;
        this.isAdjadwal = isAdjadwal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (isAdjadwal) {
            view = LayoutInflater.from(context).inflate(R.layout.view_adjadwal, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.view_jadwal, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataClass currentItem = dataList.get(position);
        holder.recyclerCaption.setText(currentItem.getCaption());

        // Load image using Glide into SubsamplingScaleImageView
        Glide.with(context)
                .asBitmap() // Load the image as a Bitmap
                .load(currentItem.getImageURL()) // Load the image URL
                .into(new CustomTarget<Bitmap>() { // Use CustomTarget to load the Bitmap
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Set the Bitmap to the SubsamplingScaleImageView
                        holder.recyclerImage.setImage(ImageSource.bitmap(resource));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Placeholder handling if needed
                    }
                });

        if (isAdjadwal) {
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();

                    // Check if the position is valid
                    if (pos != RecyclerView.NO_POSITION) {
                        // Get the DataClass object at the position
                        DataClass selectedItem = dataList.get(pos);

                        // Remove the item from the list
                        dataList.remove(pos);

                        // Notify adapter of item removal
                        notifyItemRemoved(pos);

                        // Delete the item from the Realtime Database
                        DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference("Images").child(selectedItem.getKey());
                        itemRef.removeValue();

                        // Delete the image from Firebase Storage
                        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(selectedItem.getImageURL());
                        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Image deleted successfully from Storage
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure to delete image from Storage
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        SubsamplingScaleImageView recyclerImage; // Update the variable type
        TextView recyclerCaption;
        Button btnDelete; // Button for deleting

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            if (isAdjadwal) {
                recyclerImage = itemView.findViewById(R.id.recycleradImage);
                recyclerCaption = itemView.findViewById(R.id.recycleradCaption);
                btnDelete = itemView.findViewById(R.id.btnadHapus); // Initialize the button
            } else {
                recyclerImage = itemView.findViewById(R.id.recyclerImage);
                recyclerCaption = itemView.findViewById(R.id.recyclerCaption);
            }
        }
    }
}