package com.gyz.androidopensamples.EasyImage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gyz.androidopensamples.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jacek Kwiecień on 08.11.2016.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private Context context;
    private List<File> imagesFiles;

    public ImagesAdapter(Context context, List<File> imagesFiles) {
        this.context = context;
        this.imagesFiles = imagesFiles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.view_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context)
                .load(imagesFiles.get(position))
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagesFiles.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }


    }
}
