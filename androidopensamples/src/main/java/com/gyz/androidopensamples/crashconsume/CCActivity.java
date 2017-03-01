package com.gyz.androidopensamples.crashconsume;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by guoyizhe on 2017/3/1.
 * 邮箱:gyzboy@126.com
 */

public class CCActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        Button button1 = new Button(this);
        button1.setText("click exception");
        button1.setOnClickListener(v -> {
            throw new RuntimeException("click exception");
        });
        Button button2 = new Button(this);
        button2.setText("handle exception");
        button2.setOnClickListener(v -> {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    throw new RuntimeException("handle exception");
                }
            });
        });
        Button button3 = new Button(this);
        button3.setText("new thread exception");
        button3.setOnClickListener(v -> {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    throw new RuntimeException("new thread exception");
                }
            }.start();
        });
        ll.addView(button1);
        ll.addView(button2);
        ll.addView(button3);

        setContentView(ll);

    }
}
//利用了android 的message通信方式,伪代码:
/*
    Queue queue=new Queue();// 可以理解为一个加锁的，可以阻塞线程的ArrayList
    queue.add(new Message(){
        void run(){
            ...
            print("android 启动了，下一步该往queue中插入启动主Activity的Message了");
            Message msg=getMessage4LaunchMainActivity();
            queue.add(msg);
            }
        });
        for(;;){//开始死循环，for之后的代码永远也得不到执行
            Message  msg=queue.next();
            msg.run();
        }
   }
*/