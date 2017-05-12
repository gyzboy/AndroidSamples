package com.gyz.androidsamples.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 2017/3/20.
 * 邮箱:gyzboy@126.com
 */

public class ASViewStub extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstub_main);
        //ViewStub 是一个不可见的，大小为0的View
        ViewStub stub = (ViewStub)findViewById(R.id.stub);
        View pic = stub.inflate();//inflate()被调用时, 被加载的视图替代viewstub并且返回自己的视图对象。这使得应用程序不需要额外执行findViewById()来获取所加载的视图的引用
        //		pic.setVisibility(View.VISIBLE);//一般不需要，如果要用到的话不能与stub.inflate()调换位置,因为它会间接调用inflate(),
        // ViewStub只能调用一次inflate()
        ImageView image = (ImageView)pic.findViewById(R.id.iv_pic);
        image.setImageResource(R.mipmap.ic_launcher);
        //        View view = findViewById(R.id.stub);//会返回空，因为stub加载后就已经被替换掉了，id也就不存在了
        //        System.out.println(String.valueOf(view.getId()));
        View view = findViewById(R.id.subTree);//用inflateid获取到加载进来的view正常
        ImageView imageview = (ImageView)view.findViewById(R.id.iv_pic);
        imageview.setImageResource(R.mipmap.ic_launcher);
        System.out.println(String.valueOf(view.getId()));

        //		TextView text = (TextView)findViewById(R.id.tv_show);
        //		text.setText("这是include布局文件中的TextView");
    }
}
