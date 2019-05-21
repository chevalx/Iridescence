package com.bonesignited.iridescence;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    private Context context;
    private List<Picture> pictureList;

    public PictureAdapter(Context context, List<Picture> pictureList) {
        this.context = context;
        this.pictureList = pictureList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.picture_image);
            description = itemView.findViewById(R.id.description);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.picture_item, viewGroup, false
        );
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String url = pictureList.get(i).getUrl();
        String copyright = pictureList.get(i).getCopyright();
        Glide.with(context).load(url).into(viewHolder.image);
        Log.i(TAG, "onBindViewHolder: 加载图片");

        viewHolder.description.setText(copyright);
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }
}
