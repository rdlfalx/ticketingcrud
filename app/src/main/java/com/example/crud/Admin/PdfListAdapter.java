    package com.example.crud.Admin;

    import android.content.Context;
    import android.content.Intent;
    import android.net.Uri;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.crud.R;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;

    import java.util.ArrayList;
    import java.util.List;

    public class PdfListAdapter extends RecyclerView.Adapter<PdfListAdapter.ViewHolder> {

        private List<PdfFile> pdfFiles;

        private List<PdfFile> originalList;

        private Context context;
        private static final String CHANNEL_ID = "PDF_DOWNLOAD_CHANNEL";
        private static final int NOTIFICATION_ID = 101;

        public PdfListAdapter(Context context, List<PdfFile> pdfFiles) {
            this.context = context;
            this.pdfFiles = pdfFiles;
            this.originalList = new ArrayList<>(pdfFiles);

        }

        public void filter(String searchText) {
            pdfFiles.clear();
            if (searchText.trim().isEmpty()) {
                pdfFiles.addAll(originalList); // Use the original unfiltered list
            } else {
                String query = searchText.toLowerCase().trim();
                for (PdfFile pdfFile : originalList) {
                    if (pdfFile.getTitle().toLowerCase().contains(query)) {
                        pdfFiles.add(pdfFile);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_guide, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PdfFile pdfFile = pdfFiles.get(position);

            holder.tvTitle.setText(pdfFile.getTitle());

            holder.btnDownload.setOnClickListener(v -> {
                // Get the download URL from the PdfFile object
                String downloadUrl = pdfFile.getDownloadUrl();

                // Create an Intent to view the PDF using an external PDF viewer app
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(downloadUrl));
                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return pdfFiles.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvTitle;
            Button btnDownload;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.judulPanduan);
                btnDownload = itemView.findViewById(R.id.btnDownload);
            }
        }
    }
