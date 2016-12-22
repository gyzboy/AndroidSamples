package com.gyz.androidsamples.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gyz.androidsamples.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * Created by guoyizhe on 16/9/20.
 * 邮箱:gyzboy@126.com
 */
public class ASFragment extends Fragment {

    private ArrayList<String> listData = new ArrayList<>();
    MyAdapter adapter;
    XRecyclerView xrv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        xrv = new XRecyclerView(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrv.setLayoutManager(layoutManager);
        xrv.setArrowImageView(R.drawable.arrow_up_float);
        for (int i = 0; i < 15; i++) {
            listData.add("item" + i);
        }
        adapter = new MyAdapter(listData);
        xrv.setAdapter(adapter);
        xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                for (int i = 0; i < 15; i++) {
                    listData.add("item" + i);
                }
                adapter.notifyDataSetChanged();
                xrv.refreshComplete();

            }

            @Override
            public void onLoadMore() {
                for (int i = 0; i < 15; i++) {
                    listData.add("item" + i);
                }
                adapter.notifyDataSetChanged();
                xrv.refreshComplete();
            }
        });
        return xrv;
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public ArrayList<String> datas = null;

    public MyAdapter(ArrayList<String> datas) {
        this.datas = datas;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv, viewGroup, false);
        return new ViewHolder(view);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mTextView.setText(datas.get(position));
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_text);
        }
    }
}

