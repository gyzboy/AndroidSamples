package com.gyz.androidsamples.activity.process;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.gyz.androidsamples.utils.ActivityInfo;
import com.gyz.androidsamples.utils.Utils;

/**
 * Created by guoyizhe on 16/9/13.
 * 邮箱:gyzboy@126.com
 */
public class ASProcessOne extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        TextView tv = new TextView(this);
        StringBuilder sb = new StringBuilder();
        sb.append("进程间的内存空间时不可见的。从而，开启多进程后，我们需要面临这样几个问题：\n" +
                "1）Application的多次重建。\n" +
                "2）静态成员的失效。\n" +
                "3）文件共享问题。\n" +
                "4）断点调试问题。");
        tv.setText("不设置进程process标签就是默认包名进程\n"
                + "当前进程名为:" + ActivityInfo.getRunningProcess(this).get(0).processName + "\n"
                + sb.toString());
        setContentView(tv);
    }
}
