package com.gyz.androidsamples.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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


//    isVisiable //fragment是否对用户可见，1.已经add 2.已经attach到window上 3.非hidden
//    onHiddenChanged //只有add或者remove等切换时才会调用
//    isHidden   //伴随onHiddenChanged状态变化
//    setTargetFragment //多用于设置希望跳转到目标fragment并获取返回结果,getTargetFragment().onActivityResult设置返回结果
//    setUserVisiableHint //FragmentPagerAdapter中使用的Fragment会调用此方法，处理预加载问题,可能在Fragment生命周期之外调用

    private ArrayList<String> listData = new ArrayList<>();
    MyAdapter adapter;
    XRecyclerView xrv;
    private View currView;

    private static final String TAG = ASFragment.class.getSimpleName();

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
        currView = xrv;
        return xrv;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, isVisibleToUser + "");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        Log.d(TAG,"isVisible = " + isVisible());
        Log.d(TAG,"isAdded = " + isAdded());
        Log.d(TAG,"isHidden = " + isHidden());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart");
        Log.d(TAG,"isVisible = " + isVisible());
        Log.d(TAG,"isAdded = " + isAdded());
        Log.d(TAG,"isHidden = " + isHidden());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        Log.d(TAG,"isVisible = " + isVisible());
        Log.d(TAG,"isAdded = " + isAdded());
        Log.d(TAG,"isHidden = " + isHidden());
        Log.d(TAG,currView.getWindowToken() + "");
        Log.d(TAG,currView.getVisibility() + "");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG,"onAttachContext");
        Log.d(TAG,"isVisible = " + isVisible());
        Log.d(TAG,"isAdded = " + isAdded());
        Log.d(TAG,"isHidden = " + isHidden());
    }

    //SDK API<23时，onAttach(Context)不执行，需要使用onAttach(Activity)。Fragment自身的Bug，v4的没有此问题
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG,"onAttachActivity");
        Log.d(TAG,"isVisible = " + isVisible());
        Log.d(TAG,"isAdded = " + isAdded());
        Log.d(TAG,"isHidden = " + isHidden());
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

