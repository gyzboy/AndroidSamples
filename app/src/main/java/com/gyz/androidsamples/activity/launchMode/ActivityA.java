/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gyz.androidsamples.activity.launchMode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gyz.androidsamples.R;
import com.gyz.androidsamples.activity.lifecycle.DialogActivity;
import com.gyz.androidsamples.service.ASService;
import com.gyz.androidsamples.utils.StatusTracker;
import com.gyz.androidsamples.utils.Utils;


/**
 * Created by guoyizhe on 16/6/12.
 * 邮箱:gyzboy@126.com
 */

// standard：Activity的默认加载方法，该方法会通过跳转到一个新的activity，
// 同时将该实例压入到栈中（不管该activity是否已经存在在Task栈中，都是采用new操作）。
// 例如： 栈中顺序是A B C D ，此时D通过Intent跳转到A，那么栈中结构就变成 A B C D A ，点击返回按钮的 显示顺序是 D C B A，依次摧毁。

//singleTop：singleTop模式下，当前Activity D位于栈顶的时候，如果通过Intent跳转到它本身的Activity （即D），调用onNewIntent方法
// 那么不会重新创建一个新的D实例，所以栈中的结构依旧为A B C D，如果跳转到B，那么由于B不处于栈顶，
// 所以会新建一个B实例并压入到栈中，结构就变成了A B C D B。

//singleTask：singleTask模式下，Task栈中只能有一个对应Activity的实例。
// 例如：现在栈的结构为：A B C D。A通过singleTask模式启动B，B位于新的栈中，此时D通过Intent跳转到B，则栈的结构变成了：A B。其中的C和D被栈弹出销毁了，也就是说位于B之上的实例都被销毁了
//会调用B的onNewIntent方法

//singleInstance：singleInstance模式下，会将打开的Activity压入一个新建的任务栈中。
//例如：Task栈1中结构为：A B C ，C通过Intent跳转到了D（D的模式为singleInstance），
// 那么则会新建一个Task 栈2，栈1中结构依旧为A B C，栈2中结构为D，此时屏幕中显示D，之后D通过Intent跳转到D，栈2中不会压入新的D，
// 所以2个栈中的情况没发生改变。如果D跳转到了C，那么就会根据C对应的launchMode的在栈1中进行对应的操作，C如果为standard，
// 那么D跳转到C，栈1的结构为A B C C ，此时点击返回按钮，还是在C，栈1的结构变为A B C，而不会回到D。
public class ActivityA extends Activity {

    private String mActivityName;
    private TextView mStatusView;
    private TextView mStatusAllView;
    private StatusTracker mStatusTracker = StatusTracker.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        mActivityName = getString(R.string.activity_a);
        mStatusView = (TextView)findViewById(R.id.status_view_a);
        mStatusAllView = (TextView)findViewById(R.id.status_view_all_a);
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_create));
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStatusTracker.setStatus(mActivityName, getString(R.string.on_start));
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mStatusTracker.setStatus(mActivityName, "onNewIntent");
        Utils.printStatus(mStatusView, mStatusAllView);
    }

    public void startActivityB(View v) {
//        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intents  = new Intent(ActivityA.this, ActivityB.class);
                startActivity(intents);
            }
        }).start();

    }

    public void startActivityC(View v) {
        Intent intent = new Intent(ActivityA.this, ActivityC.class);
        startActivity(intent);
    }

    public void startService(View v){
        Intent intent = new Intent(ActivityA.this, ASService.class);
        startService(intent);
    }

    public void finishActivityA(View v) {
        ActivityA.this.finish();
    }

}
