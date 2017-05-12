package com.gyz.androidsamples.view;

import java.util.List;

import android.support.v7.util.DiffUtil;

/**
 * Created by gyzboy on 2017/5/9.
 */

class DiffCallBack extends DiffUtil.Callback {
    private List<String> mOldDatas, mNewDatas;

    public DiffCallBack(List<String> mOldDatas, List<String> mNewDatas) {
        this.mOldDatas = mOldDatas;
        this.mNewDatas = mNewDatas;
    }

    @Override
    public int getOldListSize() {
        return mOldDatas != null ? mOldDatas.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewDatas != null ? mNewDatas.size() : 0;
    }
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        /**
         *这边根据你的需求去判断到底是刷新整个item，还是该item只有一点点数据的变化，从而去下面的方法判断是否需要刷新
         *如果该item从旧数据到新数据我们用到的layout都变了，就是多布局，那就这边直接返回false，刷新整个item。如果返回true，讲继续执行下面的方法去判断。
         **/
        return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
    }
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        //这边也是一样的，如果返回true，则不刷新该item，如果返回false，则只刷新该item，并且是一个动作非常小的刷新
        return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
    }
}
