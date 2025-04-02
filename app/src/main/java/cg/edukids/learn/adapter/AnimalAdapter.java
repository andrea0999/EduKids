package cg.edukids.learn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cg.edukids.R;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {
    private Context context;
    private int[] animalImages;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public AnimalAdapter(Context context, int[] animalImages, OnItemClickListener listener) {
        this.context = context;
        this.animalImages = animalImages;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.animalImage.setImageResource(animalImages[position]);
    }

    @Override
    public int getItemCount() {
        return animalImages.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView animalImage;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            animalImage = itemView.findViewById(R.id.animalImage);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
