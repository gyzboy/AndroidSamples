package com.gyz.androidsamples.support;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.AsyncLayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyz.androidsamples.R;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by guoyizhe on 2016/12/12.
 * 邮箱:gyzboy@126.com
 */

public class ASAysncInflate extends Activity {

    private View inflatedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AsyncLayoutInflater inflater = new AsyncLayoutInflater(this);
        inflater.inflate(R.layout.activity_aysncinflate, null, new AsyncLayoutInflater.OnInflateFinishedListener() {
            @Override
            public void onInflateFinished(View view, int resid, ViewGroup parent) {
                inflatedView = view;
                setContentView(inflatedView);
            }
        });

        //目前工程中有个使用这个加载的例子,在RV中使用asyncLayout进行预加载,onCreateView中全部
        //inflate完成后再进行页面的显示，将holder通过键值对方式存放在map中

        //每次执行异步layout后都增加这个值,用来记录是否layout过,作为循环结束的标签
//        AtomicInteger mInitedViewHolder = new AtomicInteger(0);

        //每次inflate完成后都cache.put(pos, viewHolder)将holder放入map中,每次取的时候从map中取值
//        ConcurrentHashMap<Integer, HomeItemViewHolder> cache = new ConcurrentHashMap<>();
//        if(cache != null && cache.get(position) != null){
//            Logger.d(TAG, "Position " + position + " used one cache.This position has cached");
//            viewHolder = cache.remove(position);
//            if(viewHolder != null){
//                return viewHolder;
//            }
//        }

//        while (mContext != null && mInitedViewHolder.get() != homeCardInfos.size()){
//              int pos = getItemViewType(mInitedViewHolder.get());
//              if (mContext != null) {
//                    asyncInitViewHolderImpl(pos);
//                   mInitedViewHolder.getAndIncrement();
//               }
//        }
    }
}
