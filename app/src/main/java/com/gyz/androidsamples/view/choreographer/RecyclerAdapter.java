package com.gyz.androidsamples.view.choreographer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.gyz.androidsamples.R;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    List<RecyclerItem> mList;

    private boolean isNeedDelay = false;

    public RecyclerAdapter(List<RecyclerItem> list, boolean isDalay) {
        mList = list;
        isNeedDelay = isDalay;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        final RecyclerItem item = mList.get(position);
        try {
            if (isNeedDelay)
                Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        holder.tvTitle.setText(item.getTitle());
        holder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        holder.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "img :" + item.getImg(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public final static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView)itemView.findViewById(R.id.iv_icon);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
        }
    }
}
