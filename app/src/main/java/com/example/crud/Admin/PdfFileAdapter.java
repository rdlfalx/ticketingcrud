package com.example.crud.Admin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.UUID;

public class PdfFileAdapter {

    public static void uploadPdfFile(Activity activity, Uri fileUri, String title) {
        // Create a reference to "pdfs" node in Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pdfs");

        // Generate a unique key for the pdf
        String key = databaseReference.push().getKey();

        // Create a reference to the location where the file will be uploaded in Firebase Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("pdfs").child(key);

        // Upload file to Firebase Storage
        storageReference.putFile(fileUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get the download URL of the uploaded file
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();

                        // Create PdfFile object with title, downloadUrl, and key
                        PdfFile pdfFile = new PdfFile(title, downloadUrl, key);

                        // Save PdfFile object to Firebase Realtime Database
                        databaseReference.child(key).setValue(pdfFile)
                                .addOnSuccessListener(aVoid ->
                                        Toast.makeText(activity, "PDF uploaded successfully", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e ->
                                        Toast.makeText(activity, "Failed to upload PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    });
                })
                .addOnFailureListener(e ->
                        Toast.makeText(activity, "Failed to upload PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}