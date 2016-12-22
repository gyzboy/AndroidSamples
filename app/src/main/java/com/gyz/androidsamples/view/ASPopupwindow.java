package com.gyz.androidsamples.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.gyz.androidsamples.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by guoyizhe on 16/9/19.
 * 邮箱:gyzboy@126.com
 */
public class ASPopupwindow extends Activity {

//    PopupWindow可以设置出现在页面的任何位置。
//    AlertDialog默认只能显示在屏幕中央。
//
//    AlertDialog是非阻塞线程的，而PopupWindow是阻塞线程的

    MtitlePopupWindow pw;
    String[] items = {"1", "2", "3", "4", "5", "2", "3", "4", "5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Button button = new Button(this);
        button.post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) button.getLayoutParams();
                param.width = FrameLayout.LayoutParams.MATCH_PARENT;
                param.height = 100;
                button.setLayoutParams(param);
            }
        });

        button.setText("弹出PopupWindow");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showAsDropDown方法是让PopupWindow相对于某个控件显示，而showAtLocation是相对于整个窗口的。
//                pw.showAsDropDown(v);

//                第一个参数是View类型的parent,虽然这里参数名是parent，其实，不是把PopupWindow放到这个parent里，并不要求这个parent是
//                一个ViewGroup，这个参数名让人误解。官方文档”a parent view to get the android.view.View.getWindowToken()
//                token from“,这个parent的作用应该是调用其getWindowToken()方法获取窗口的Token,所以，只要是该窗口上的控件就可以了。
//
//                第二个参数是Gravity，可以使用|附加多个属性，如Gravity.LEFT|Gravity.BOTTOM。
//
//                第三四个参数是x,y偏移。
                pw.showAtLocation(v, Gravity.CENTER, 0, 0);
            }
        });
        setContentView(button);

        initPop(this);
    }

    private void initPop(Context context) {
        pw = new MtitlePopupWindow(context);
        pw.changeData(Arrays.asList(items));
        pw.setOnPopupWindowClickListener(new MtitlePopupWindow.OnPopupWindowClickListener() {

            @Override
            public void onPopupWindowItemClick(int position) {
                //你要做的事
                Toast.makeText(getApplication(), items[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class MtitlePopupWindow extends PopupWindow {

    private Context mContext;

    private OnPopupWindowClickListener listener;

    private ArrayAdapter adapter;

    private List<String> list = new ArrayList<String>();

    private int width = 0;

    public MtitlePopupWindow(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.title_popupwindow, null);
        setContentView(popupView);

        //设置宽度,若没有设置宽度为LayoutParams.WRAP_CONTENT
        setWidth(250);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置动画，也可以不设置。不设置则是显示默认的
        setAnimationStyle(R.style.popupwindow_animation);

        //设置PopupWindow是否响应touch事件，默认是true，如果设置为false，所有的touch事件无响应，包括点击事件
        this.setTouchable(true);
//        PopupWindow是否具有获取焦点的能力，默认为False。一般来讲是没有用的，因为普通的控件是不需要获取焦点的，而对于EditText则
//        不同，如果不能获取焦点，那么EditText将是无法编辑的。
        this.setFocusable(true);

//        要想前者起作用必须设置后者，setBackground函数不只能设置背景，因为你加上它之后，setOutsideTouchable（）才会生效，
//        PopupWindow才会对返回按钮有响应：点击手机返回按钮，可以关闭PopupWindow；如果不加setBackgroundDrawable（）将关闭的
//        PopupWindow所在的Activity.
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());

        //在Popupwindow中有EditText，当点选EditText后输入法在popupwindow后面显示了，设置
        //setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED)即可
        this.setInputMethodMode(INPUT_METHOD_NEEDED);

        ListView listView = (ListView) popupView.findViewById(R.id.popupwindow);
        adapter = new ArrayAdapter(mContext, R.layout.popupwindow_item, R.id.popup_item, list);
        listView.setAdapter(adapter);

        //ListView的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                MtitlePopupWindow.this.dismiss();
                if (listener != null) {
                    listener.onPopupWindowItemClick(position);
                }
            }
        });

    }

    /**
     * 为PopupWindow设置回调接口
     *
     * @param listener
     */
    public void setOnPopupWindowClickListener(OnPopupWindowClickListener listener) {
        this.listener = listener;
    }


    /**
     * 设置数据的方法，供外部调用
     *
     * @param mList
     */
    public void changeData(List<String> mList) {
        //这里用addAll也很重要，如果用this.list = mList，调用notifyDataSetChanged()无效
        //notifyDataSetChanged()数据源发生改变的时候调用的，this.list = mList，list并没有发送改变
        list.addAll(mList);
        adapter.notifyDataSetChanged();
    }


    /**
     * 回调接口.供外部调用
     *
     * @author xiaanming
     */
    public interface OnPopupWindowClickListener {
        /**
         * 当点击PopupWindow的ListView 的item的时候调用此方法，用回调方法的好处就是降低耦合性
         *
         * @param position 位置
         */
        void onPopupWindowItemClick(int position);
    }


}


