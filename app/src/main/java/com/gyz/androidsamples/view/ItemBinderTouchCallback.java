package com.gyz.androidsamples.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;
import com.gyz.androidopensamples.utilCode.ToastUtils;

final class ItemBinderTouchCallback extends ItemTouchHelper.Callback {

    MyAdapter adapter;
    Context mContext;

    public ItemBinderTouchCallback(Context context) {
        mContext = context;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        /**
         * ItemTouchHelper支持事件方向判断，但是必须重写当前getMovementFlags来指定支持的方向
         * 这里我同时设置了dragFlag为上下左右四个方向，swipeFlag的左右方向
         * 最后通过makeMovementFlags（dragFlag，swipe）创建方向的Flag，
         */
        int dragFlag = ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
        int swipe = ItemTouchHelper.START|ItemTouchHelper.END;
        return makeMovementFlags(dragFlag,swipe);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source,
                          RecyclerView.ViewHolder target) {
        Toast.makeText(mContext, "onMove", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        Toast.makeText(mContext, "onSwipe", Toast.LENGTH_SHORT).show();
    }
}