package cg.edukids.drawing.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cg.edukids.R;
import cg.edukids.drawing.DrawActivity;
import cg.edukids.drawing.general.General;
import cg.edukids.drawing.interfaceImage.ImageOnClick;
import cg.edukids.drawing.viewHolder.ImageViewHolder;

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private Context mcontext;
    private List<Integer> listImages;

    public ImageAdapter(Context context){
        mcontext = context;
        listImages = getImages();
    }

    private List<Integer> getImages() {
        List<Integer> results = new ArrayList<>();

            results.add(R.drawable.img01);
            results.add(R.drawable.img02);
            results.add(R.drawable.img03);
            results.add(R.drawable.img04);
            results.add(R.drawable.img05);
            results.add(R.drawable.img06);
            results.add(R.drawable.img07);
            results.add(R.drawable.img08);
            results.add(R.drawable.img09);
            results.add(R.drawable.img10);
            results.add(R.drawable.img11);
            results.add(R.drawable.img12);
            results.add(R.drawable.img13);
            results.add(R.drawable.img14);
            results.add(R.drawable.img15);
            results.add(R.drawable.img16);
            results.add(R.drawable.img17);
            results.add(R.drawable.img18);

        return results;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_images, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(listImages.get(position));

        holder.setImageOnClick(new ImageOnClick() {
            @Override
            public void onClcik(int pos) {
                General.PICTURE_SELECTED = listImages.get(pos);
                mcontext.startActivity(new Intent(mcontext, DrawActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }
}
