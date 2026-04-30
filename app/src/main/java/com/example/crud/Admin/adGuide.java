package com.example.crud.Admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.crud.R;

public class adGuide extends AppCompatActivity {

    private static final int PICK_PDF_REQUEST = 1;

    private EditText etPdfTitle;
    private Button btnChooseFile;
    private TextView SelectedFileName;
    private Button btnUpload;

    private Uri selectedFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_guide);

        etPdfTitle = findViewById(R.id.etPdfTitle);
        btnChooseFile = findViewById(R.id.btnChooseFile);
        SelectedFileName = findViewById(R.id.tvSelectedFileName);
        btnUpload = findViewById(R.id.btnUpload);

        btnChooseFile.setOnClickListener(v -> chooseFile());
        btnUpload.setOnClickListener(v -> uploadFile());
    }

    private void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST);
    }

    private void uploadFile() {
        String title = etPdfTitle.getText().toString().trim();

        if (selectedFileUri != null && !title.isEmpty()) {
            PdfFileAdapter.uploadPdfFile(this, selectedFileUri, title);
        } else {
            Toast.makeText(this, "Please select a file and enter a title", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedFileUri = data.getData();
            String fileName = getFileName(selectedFileUri);
            SelectedFileName.setText(fileName);
            SelectedFileName.setVisibility(TextView.VISIBLE);
            btnUpload.setVisibility(Button.VISIBLE);
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}