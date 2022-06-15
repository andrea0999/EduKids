package cg.edukids.puzzle.adapters;

import static cg.edukids.puzzle.PuzzleListActivity.choose;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cg.edukids.R;
import cg.edukids.drawing.general.General;
import cg.edukids.drawing.interfaceImage.ImageOnClick;
import cg.edukids.puzzle.Start2DActivity;
import cg.edukids.puzzle.StartPuzzleActivity;
import cg.edukids.puzzle.viewHolder.ImageViewHolder;


public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private Context mcontext;
    private List<Integer> listImages;
    public static int getSelectPicture;
    //public static Bitmap get2dPicture;

    public ImageAdapter(){

    }

    public ImageAdapter(Context context){
        mcontext = context;
        listImages = getImages();
    }

    private List<Integer> getImages() {
        List<Integer> results = new ArrayList<>();

        results.add(R.drawable.puzzle01);
        results.add(R.drawable.puzzle02);
        results.add(R.drawable.puzzle03);
        results.add(R.drawable.puzzle04);
        results.add(R.drawable.puzzle05);
        results.add(R.drawable.puzzle06);
        results.add(R.drawable.puzzle07);
        results.add(R.drawable.puzzle08);
        results.add(R.drawable.puzzle09);
        results.add(R.drawable.puzzle10);
        results.add(R.drawable.puzzle11);
        results.add(R.drawable.puzzle12);
        results.add(R.drawable.puzzle13);
        results.add(R.drawable.puzzle14);
        results.add(R.drawable.puzzle15);
        results.add(R.drawable.puzzle16);
        results.add(R.drawable.puzzle17);
        results.add(R.drawable.puzzle18);
        results.add(R.drawable.puzzle19);

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
            public void onClick(int pos) {
                General.PICTURE_SELECTED = listImages.get(pos);
                System.out.println("Image adapter select picture: " + General.PICTURE_SELECTED);
                getSelectPicture = General.PICTURE_SELECTED;
                mcontext.startActivity(new Intent(mcontext, StartPuzzleActivity.class));
                if(choose != null){
                    if(choose.equals("Different: 2D")) {
                        choose = "Puzzle option…";
                        System.out.println("ImageAdapter 2D");
                        mcontext.startActivity(new Intent(mcontext, Start2DActivity.class));
                    }
                }else {
                    System.out.println("ImageAdapter normal");
                    mcontext.startActivity(new Intent(mcontext, StartPuzzleActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }

}
