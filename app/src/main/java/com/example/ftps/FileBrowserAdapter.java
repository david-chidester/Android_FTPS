package com.example.ftps;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FileBrowserAdapter extends RecyclerView.Adapter<FileBrowserAdapter.FileBrowserViewHolder> {
    private ArrayList<FileBrowserListItem> fbliList;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClicked(int posititon);
    }

    public void setOnClockListener(OnItemClickListener oicl) {
        listener = oicl;
    }

    public static class FileBrowserViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView fileName;
        public TextView fileSize;

        public FileBrowserViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            fileName = itemView.findViewById(R.id.fileNameTextView);
            fileSize = itemView.findViewById(R.id.fileSizeTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClicked(position);
                        }
                    }
                }
            });
        }
    }

    public FileBrowserAdapter(ArrayList<FileBrowserListItem> fileList) {
        fbliList = fileList;
    }

    @NonNull
    @Override
    public FileBrowserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.file_browser_item, viewGroup, false);
        FileBrowserViewHolder fbvh = new FileBrowserViewHolder(v, listener);
        return fbvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FileBrowserViewHolder fileBrowserViewHolder, int i) {
        FileBrowserListItem currentItem = fbliList.get(i);
        fileBrowserViewHolder.imageView.setImageResource(currentItem.getImageRecource());
        fileBrowserViewHolder.fileName.setText(currentItem.getFileName());
        fileBrowserViewHolder.fileSize.setText(currentItem.getFileSize());
    }

    @Override
    public int getItemCount() {
        return fbliList.size();
    }
}
