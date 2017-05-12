package com.gyz.androidsamples.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gyz.androidsamples.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoyizhe on 16/9/8.
 * 邮箱:gyzboy@126.com
 */
public class ASRecyclerView extends Activity implements MyAdapter.onItemClickLsn {

    public static final String TAG = ASRecyclerView.class.getSimpleName();

    //RecyclerView将它的measure与layout过程委托给了RecyclerView.LayoutManager来处理，
    // 并且，它对子控件的measure及layout过程是逐个处理的，也就是说，执行完成一个子控件的measure及layout过程再去执行下一个。

    //layoutChild的时候有两种方向去填充itemView:fill towards end,fill towards start
    //填充ItemView的算法为：向父容器增加子控件，测量子控件大小，布局子控件，布局锚点向当前布局方向平移子控件大小，
    // 重复上诉步骤至RecyclerView可绘制空间消耗完毕或子控件已全部填充。


    //itemview执行顺序:getFromPool---->onBind---->attached---->detached
    private RecyclerView rvView;
    private String[] arr;
    private ArrayList<String> arrayList;
    private ArrayList<String> oldList;
    private int lastInVisPos = 0;
    private int lastVisPos = 0;

    private Button btn_notifyDataChanged;
    private Button btn_notifyItemInsert;
    private MyAdapter adapter;

    private CustomViewCacheExtension mCacheExtension;//自定义二级缓存


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recyclerview);

        rvView = (RecyclerView) findViewById(R.id.rv);
        btn_notifyItemInsert = (Button) findViewById(R.id.notifyItemInsert);
        btn_notifyDataChanged = (Button) findViewById(R.id.notifyDataChange);
        btn_notifyItemInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.add(5,"notifyItemInsert");
                adapter.notifyItemChanged(5);
            }
        });
        btn_notifyDataChanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.add("notifyDataChanged");
                DiffUtil.DiffResult diffResult =
                    DiffUtil.calculateDiff(new DiffCallBack(oldList, arrayList), true);
                diffResult.dispatchUpdatesTo(adapter);
                //adapter.setDatas(arrayList);
                //adapter.notifyDataSetChanged();
            }
        });
        LinearLayoutManager llM = new LinearLayoutManager(this);
        rvView.setLayoutManager(llM);
        rvView.addItemDecoration(mItemDecoration);
        arr = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"};
        arrayList = new ArrayList<>();
        oldList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            arrayList.add("" + i);
            oldList.add("" + i);
        }
//        MyAdapter adapter = new MyAdapter(arr);
        adapter = new MyAdapter(arrayList);
        rvView.setAdapter(adapter);
        adapter.setInterface(this);

        //SnapHelper是对RecyclerView的拓展,旨在支持 RecyclerView 的对齐方式，也就是通过计算对齐 RecyclerView 中 TargetView 的指定点或者容器中的任何像素点。
        //StartSnapHelper helper = new StartSnapHelper();
        //helper.attachToRecyclerView(rvView);


//        rvView.setLayoutFrozen(true);//禁止滚动
        //设置rv是否通过adapter的content大小或者child的数量来改变尺寸,但其他因素引起的(比如父容器尺寸的变化)依旧会有作用
//        rvView.setHasFixedSize(true);
        //设置touch越界 [TOUCH_SLOP_DEFAULT/TOUCH_SLOP_PAGING]
        rvView.setScrollingTouchSlop(RecyclerView.TOUCH_SLOP_PAGING);

        rvView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {

            //view出现在屏幕中时调用
            @Override
            public void onChildViewAttachedToWindow(View view) {
                Log.e(TAG, "attached pos is: " + rvView.getChildViewHolder(view).getLayoutPosition());
                lastVisPos = rvView.getChildViewHolder(view).getLayoutPosition();
            }

            //view离开屏幕时调用
            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Log.e(TAG, "detached pos is: " + rvView.getChildViewHolder(view).getLayoutPosition());
                lastInVisPos = rvView.getChildViewHolder(view).getLayoutPosition();
            }
        });

        //设置在view超出屏幕时缓存的view数量,这些view还没有进入到cachepool中,默认是2,设置过大后都频繁调用onCreateView,因为需要将这些view都缓存起来
//        rvView.setItemViewCacheSize(arr.length);
        mCacheExtension = new CustomViewCacheExtension();

//        rvView.setViewCacheExtension(mCacheExtension);


        //Recycler:
        // Recycler的作用就是重用ItemView。在填充ItemView的时候，ItemView是从它获取的；滑出屏幕的ItemView是由它回收的。
        // 对于不同状态的ItemView存储在了不同的集合中，比如有scrapped、cached、exCached、recycled

//RecyclerView.Recycler.getViewForPosition():
// 根据列表位置获取ItemView，先后从scrapped、cached、exCached、recycled集合中查找相应的ItemView，如果没有找到，就创建（Adapter.createViewHolder()），
// 最后与数据集绑定。其中scrapped、cached和exCached集合定义在RecyclerView.Recycler中，分别表示将要在RecyclerView中删除的ItemView、一级缓存ItemView和二级缓存ItemView，
// cached集合的大小默认为２，exCached是需要我们通过RecyclerView.ViewCacheExtension自己实现的，默认没有；
// recycled集合其实是一个Map，定义在RecyclerView.RecycledViewPool中，将ItemView以ItemType分类保存了下来
        rvView.setRecycledViewPool(rPool);
//        rPool.setMaxRecycledViews(0, 10);//这里假设设置type为0的view缓存设置大小为10个

        //rv回收监听，当itemView被回收时调用
        rvView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            //addViewToRecyclerPool时调用,recycle后会put到pool中
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                Log.e(TAG, "holder is recycled at 位置:" + holder.getAdapterPosition());
            }
        });

        //return 0 只在0的位置绘制元素
        rvView.setChildDrawingOrderCallback(new RecyclerView.ChildDrawingOrderCallback() {
            @Override
            public int onGetChildDrawingOrder(int childCount, int i) {
//                Log.e(TAG,"Childcount: " + childCount + "i: " + i);
                return i;
            }
        });
    }


//    getItemOffsets()就是我们在实现一个RecyclerView.ItemDecoration时可以重写的方法，通过mTempRect的大小，
//    可以为每个ItemView设置位置偏移量，这个偏移量最终会参与计算ItemView的大小，也就是说ItemView的大小是包含这个位置偏移量的。

    // 如果top offset等于0，那么ItemView之间就没有空隙；
    // 如果top offset大于0，那么ItemView之前就会有一个间隙；
    // 如果top offset小于0，那么ItemView之间就会有重叠的区域。

    RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {

        //在itemview绘制之后绘制,所以itemDecoration可见
        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            Paint paint = new Paint();
            paint.setColor(Color.RED);

            for (int i = 0; i < parent.getLayoutManager().getChildCount(); i++) {
                final View child = parent.getChildAt(i);

                float left = child.getLeft() + (child.getRight() - child.getLeft()) / 4;
                float top = child.getTop();
                float right = left + (child.getRight() - child.getLeft()) / 2;
                float bottom = top + 5;

                c.drawRect(left, top, right, bottom, paint);
            }
        }

        //在itemview绘制之前绘制,所以有可能在itemview的下面,导致itemDecoration不可见
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            Paint paint = new Paint();
            paint.setColor(Color.RED);

            for (int i = 0; i < parent.getLayoutManager().getChildCount(); i++) {
                final View child = parent.getChildAt(i);

                float left = child.getLeft() + (child.getRight() - child.getLeft()) / 4;
                float top = child.getTop();
                float right = left + (child.getRight() - child.getLeft()) / 2;
                float bottom = top + 5;

                c.drawRect(left, top, right, bottom, paint);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);


            outRect.top = 5;
            outRect.bottom = 5;

            //itemView.height = itemView.getInsHeight + top + bottom;
        }
    };

    //这里算是RecyclerView设计上的亮点，通过RecyclerView.RecycledViewPool可以实现在不同的RecyclerView之间共享ItemView，
    // 只要为这些不同RecyclerView设置同一个RecyclerView.RecycledViewPool就可以了。默认大小为5
    RecyclerView.RecycledViewPool rPool = new RecyclerView.RecycledViewPool() {
        @Override
        public void putRecycledView(RecyclerView.ViewHolder scrap) {
            super.putRecycledView(scrap);
            Log.e(TAG, "put holder to pool: " + scrap);
        }

        @Override
        public RecyclerView.ViewHolder getRecycledView(int viewType) {
            RecyclerView.ViewHolder scrap = super.getRecycledView(viewType);
            Log.e(TAG, "get holder from pool: " + scrap);
            return super.getRecycledView(viewType);
        }

        @Override
        public void setMaxRecycledViews(int viewType, int max) {
            super.setMaxRecycledViews(0, 500);
        }

        @Override
        public void clear() {
            super.clear();
        }
    };


    //自定义二级缓存
    public class CustomViewCacheExtension extends RecyclerView.ViewCacheExtension {


        public View cachedView;

        public void setCached(View cacheView) {
            cachedView = cacheView;
        }

        @Override
        public View getViewForPositionAndType(RecyclerView.Recycler recycler, int position, int type) {
            if (position == 10) {
                cachedView = recycler.getViewForPosition(position);
            }
            recycler.bindViewToPosition(cachedView, position);
            return cachedView;
        }
    }

    /**
     * 自定义LinearLayoutManager
     */
    private class CustomLinearLayout extends LinearLayoutManager {

        public CustomLinearLayout(Context context) {
            super(context);
        }

        //设置预加载的layout距离,可以增大以加大缓存
        @Override
        protected int getExtraLayoutSpace(RecyclerView.State state) {
            return 300;
        }
    }

    //    findChildViewUnder(float x, float y)//找到指定位置的view
    @Override
    public void click(int pos) {
        int visiableCount = lastVisPos - lastInVisPos;//获得屏幕内可见item的个数
        Log.e(TAG, String.valueOf(visiableCount));
        if (pos > visiableCount) {
            rvView.smoothScrollToPosition(5);//滚动到指定位置
//            rvView.offsetChildrenVertical(100);//这个位移ItemDecoration不会随着一起移动
        }
        MyAdapter.ViewHolder holder = (MyAdapter.ViewHolder) rvView.findViewHolderForAdapterPosition(pos);
        Toast.makeText(getApplicationContext(), "getLayoutPosition:" + holder.getLayoutPosition()
                        + "  getAdapterPosition: " + holder.getAdapterPosition()
                , Toast.LENGTH_SHORT).show();
// getLayoutPosition与getAdapterPosition具体区别就是adapter和layout的位置会有时间差(<16ms), 如果你改变了Adapter的数据然后刷新视图,
// layout需要过一段时间才会更新视图, 在这段时间里面, 这两个方法返回的position会不一样.
//另外, 在notifyDataSetChanged之后并不能马上获取Adapter中的position, 要等布局结束之后才能获取到.
//而对于Layout的position, 在notifyItemInserted之后, Layout不能马上获取到新的position, 因为布局还没更新(需要<16ms的时间刷新视图),
// 所以只能获取到旧的, 但是Adapter中的position就可以马上获取到最新的position.
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public static final String TAG = ASRecyclerView.class.getSimpleName();

    public String[] datas = null;
    private ArrayList<String> arrList;

    public MyAdapter(String[] datas) {
        this.datas = datas;
    }

    public MyAdapter(ArrayList<String> list) {
        arrList = list;
    }

    interface onItemClickLsn {
        void click(int pos);
    }

    public  void setDatas(ArrayList<String> list){
        arrList = list;
    }

    public onItemClickLsn mInterface;

    public void setInterface(onItemClickLsn lsn) {
        mInterface = lsn;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.e(TAG, "onCreateViewHolder: " + viewType);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv2, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.e(TAG, "onBindViewHolder: " + position);
//        if (position == 10) {
//            viewHolder.mWebView.setVisibility(View.VISIBLE);
//            viewHolder.mTextView.setVisibility(View.GONE);
//            viewHolder.mWebView.post(new Runnable() {
//                @Override
//                public void run() {
//                    viewHolder.mWebView.loadUrl("www.baidu.com");
//                }
//            });
//        } else {
//            viewHolder.mWebView.setVisibility(View.GONE);
        viewHolder.mTextView.setVisibility(View.VISIBLE);
        if (datas != null) {
            viewHolder.mTextView.setText(datas[position]);
        } else {
            viewHolder.mTextView.setText(arrList.get(position));
        }
        viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterface.click(position);
            }
        });
//        }
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if (datas != null) {
            return datas.length;
        }
        return arrList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 10) {
//            return 1;
//        }
        return 0;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
//        public WebView mWebView;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_text_rv);
//            mWebView = (WebView) view.findViewById(R.id.rv_wb);
        }
    }
}

