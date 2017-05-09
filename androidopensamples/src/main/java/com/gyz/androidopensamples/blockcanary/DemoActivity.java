package com.gyz.androidopensamples.blockcanary;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Printer;

/**
 * Created by gyzboy on 2017/5/2.
 */

public class DemoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMainLooper().setMessageLogging(new CustomPrinter());

    }

    static class CustomPrinter implements Printer {

        @Override
        public void println(String x) {
            //在looper内处理消息的时候会调用printer打印log,主线程卡住意味着looper调用dispater卡住了,判断这个printer打印log的两次时间间隔来确定是否发生了
            //界面卡顿,发生卡顿后可以根据dump堆栈信息
        }
    }
}

