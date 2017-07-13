package com.gyz.androidsamples.os;

import java.util.logging.Logger;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by gyzboy on 2017/7/11.
 */

public class ASHandler extends Activity {

    private static final String TAG = ASHandler.class.getSimpleName();

    private StringBuilder sb = new StringBuilder();

    private Handler handler = new Handler(new MyCallback()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            sb.append("\nhandleMsg:" + msg.what);
            textView.setText(sb.toString());
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            sb.append("\ndispatchMsg:" + msg.what);
            textView.setText(sb.toString());
        }

        @Override
        public String getMessageName(Message message) {
            return super.getMessageName(message);
        }

        @Override
        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            return super.sendMessageAtTime(msg, uptimeMillis);
        }
    };

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Message类是final的,继承不了
        Message message = Message.obtain();
        message.what = 1;
        message.arg1 = 10;
        handler.sendEmptyMessage(1);
        sb.append(handler.getMessageName(message));

        handler.post(new Runnable() {
            @Override
            public void run() {
                sb.append("\npostToQueue");
                textView.setText(sb.toString());
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //默认dispatch的msg.what=0
                sb.append("\npostDelay");
                textView.setText(sb.toString());
            }
        }, 1000);

        Message message1 = Message.obtain();
        message1.what = 2;
        message1.arg1 = 20;
        handler.sendEmptyMessageDelayed(2, 2000);
        handler.postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {
                sb.append("\npostAtFront");
                textView.setText(sb.toString());
            }
        });

        Message message2 = Message.obtain();
        message2.what = 3;
        message2.arg1 = 30;
        handler.sendMessageAtFrontOfQueue(message2);
        textView = new TextView(this);
        textView.setText(sb.toString());
        setContentView(textView);

    }

    class MyCallback implements Callback {

        @Override
        public boolean handleMessage(Message msg) {
            sb.append("\nCustomCallback:" + msg.what);
            textView.setText(sb.toString());
            return false;
        }
    }
}

