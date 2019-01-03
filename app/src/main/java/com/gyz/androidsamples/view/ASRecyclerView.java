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
import android.support.v7.widget.helper.ItemTouchHelper;
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
    private ItemTouchHelper itemTouchHelper;

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

//RecyclerView.Recycler.getViewForPosition()返回ViewHolder对象:
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

        getItemTouchHelper().attachToRecyclerView(rvView);
    }

    public final ItemTouchHelper getItemTouchHelper() {
        if (null == itemTouchHelper) {
            ItemBinderTouchCallback itemBinderTouchCallback = new ItemBinderTouchCallback(ASRecyclerView.this);
            itemTouchHelper = new ItemTouchHelper(itemBinderTouchCallback);
        }
        return itemTouchHelper;
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
    //适合位置固定、样式、type不改变的item,例如广告
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

//    RecyclerView在Recyler里面实现ViewHolder的缓存，Recycler里面的实现缓存的主要包含以下5个对象：
//
//    ArrayList mAttachedScrap：
//       未与RecyclerView分离的ViewHolder列表,如果仍依赖于 RecyclerView （比如已经滑动出可视范围，但还没有被移除掉），
//       但已经被标记移除的 ItemView 集合会被添加到 mAttachedScrap 中,按照id和position来查找ViewHolder
//
//
//    ArrayList mChangedScrap：表示数据已经改变的viewHolder列表,存储 notifXXX 方法时需要改变的 ViewHolder,匹配机制按照position和id进行匹配
//    ArrayList mCachedViews：缓存ViewHolder，主要用于解决RecyclerView滑动抖动时的情况，还有用于保存Prefetch的ViewHoder
//
//    最大的数量为：mViewCacheMax = mRequestedCacheMax + extraCache（extraCache是由prefetch的时候计算出来的）
//
//
//    ViewCacheExtension mViewCacheExtension：开发者可自定义的一层缓存，是虚拟类ViewCacheExtension的一个实例，开发者可实现方法getViewForPositionAndType(Recycler recycler, int position, int type)来实现自己的缓存。
//
//    适用场景：固定位置、样式固定(广告)
//    位置固定
//    内容不变
//    数量有限
//
//
//    mRecyclerPool ViewHolder缓存池，在有限的mCachedViews中如果存不下ViewHolder时，就会把ViewHolder存入RecyclerViewPool中。
//
//    按照Type来查找ViewHolder
//            每个Type默认最多缓存5个

//    RecyclerView在设计的时候讲上述5个缓存对象分为了3级。每次创建ViewHolder的时候，会按照优先级依次查询缓存创建ViewHolder。每次讲ViewHolder缓存到Recycler缓存的时候，也会按照优先级依次缓存进去。三级缓存分别是：
//
//    一级缓存：返回布局和内容都都有效的ViewHolder
//
//            按照position或者id进行匹配
//            命中一级缓存无需onCreateViewHolder和onBindViewHolder
//            mAttachScrap在adapter.notifyXxx的时候用到
//            mChanedScarp在每次View绘制的时候用到
//            mCachedView：用来解决滑动抖动的情况，默认值为2
//
//
//    二级缓存：返回View
//
//            按照position和type进行匹配
//            直接返回View
//            需要自己继承ViewCacheExtension实现
//            位置固定，内容不发生改变的情况，比如说Header如果内容固定，就可以使用
//
//
//    三级缓存：返回布局有效，内容无效的ViewHolder
//
//            按照type进行匹配，每个type缓存值默认=5
//            layout是有效的，但是内容是无效的
//            多个RecycleView可共享,可用于多个RecyclerView的优化

//具体步骤:

//出屏幕时候的情况
//
//    1、当ViewHolder（position=0，type=1）出屏幕的时候，由于mCacheViews是空的，那么就直接放在mCacheViews里面，ViewHolder在mCacheViews里面布局和内容都是有效的，因此可以直接复用。
//    2、ViewHolder（position=1，type=2）同步骤1
//    3、当ViewHolder（position=2，type=1）出屏幕的时候由于一级缓存mCacheViews已经满了，因此将其放入RecyclerPool（type=1）的缓存池里面。
//       此时ViewHolder的内容会被标记为无效，当其复用的时候需要再次通过Adapter.bindViewHolder来绑定内容。
//    4、ViewHolder（position=3，type=2）同步骤3
//
//进屏幕时候的情况
//
//    5、当ViewHolder（position=3-10，type=3）进入屏幕绘制的时候，由于Recycler的mCacheViews里面找不到position匹配的View，同时RecyclerPool里面找不到type匹配的View，
//       因此，其只能通过adapter.createViewHolder来创建ViewHolder，然后通过adapter.bindViewHolder来绑定内容。
//    6、当ViewHolder（position=11，type=1）进入屏幕的时候，发现ReccylerPool里面能找到type=1的缓存，因此直接从ReccylerPool里面取来使用。
//      由于内容是无效的，因此还需要调用bindViewHolder来绑定布局。同时ViewHolder（position=4，type=3）需要出屏幕，其直接进入RecyclerPool（type=3）的缓存池中
//    7、ViewHolder（position=12，type=2）同步骤6
//
//    屏幕往下拉ViewHoder（position=1）进入屏幕的情况
//
//    8、由于mCacheView里面的有position=1的ViewHolder与之匹配，直接返回。由于内容是有效的，因此无需再次绑定内容
//    9、ViewHolder（position=0）同步骤8
//
//

}

