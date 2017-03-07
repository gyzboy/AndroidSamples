package com.gyz.androidopensamples.uccopypaste;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.*;
import android.widget.TextView;

import com.gyz.androidopensamples.R;


//1.监控剪切板
//2.WindowManager 的使用
//3.Service 的使用
//4.悬浮窗处理:
//        黑色半透明背景
//        触摸背景关闭
//        点击内容跳转
//        处理返回键关闭
//5.开机自动启动 Service
//6.WakeLock 启动 Service(WakefulBroadcastReceiver接收手机硬件唤醒事件)
public final class MainActivity extends AppCompatActivity {

    private final static String KEY_CONTENT = "content";
    private TextView mTextView;

    public static void startForContent(Context context, String content) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(KEY_CONTENT, content);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_uccopypaste_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.text_view);

        Intent intent = getIntent();
        Utils.printIntent("MainActivity::onCreate()", intent);

        tryToShowContent(intent);
        ListenClipboardService.start(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Utils.printIntent("MainActivity::onNewIntent()", intent);

        tryToShowContent(intent);
    }

    private void tryToShowContent(Intent intent) {
        String content = intent.getStringExtra(KEY_CONTENT);
        if (!TextUtils.isEmpty(content)) {
            mTextView.setText(content);
        }
    }

}