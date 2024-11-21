package com.erns.canvaspre.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.erns.canvaspre.R;
import com.erns.canvaspre.model.ent.PictureEntity;
import com.erns.canvaspre.model.database.FileRepository;

import java.util.List;

public class PictureListAdapter extends RecyclerView.Adapter<PictureListAdapter.ViewHolder> {
    private final List<PictureEntity> pictureList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PictureEntity picture);
    }

    public PictureListAdapter(List<PictureEntity> pictureList, OnItemClickListener listener) {
        this.pictureList = pictureList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PictureEntity picture = pictureList.get(position);
        holder.bind(picture, listener);
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final ImageView pictureImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.txtTitle);
            pictureImageView = itemView.findViewById(R.id.imgPicture);
        }

        public void bind(final PictureEntity picture, final OnItemClickListener listener) {
            titleTextView.setText(picture.title);

            // Cargar imagen desde el repositorio o archivo
            FileRepository fileRepository = new FileRepository(itemView.getContext());
            Bitmap bitmap = fileRepository.getPicture(picture.link);
            if (bitmap != null) {
                pictureImageView.setImageBitmap(bitmap);
            }

            itemView.setOnClickListener(v -> listener.onItemClick(picture));
        }
    }
}

