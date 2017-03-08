package com.gyz.androidopensamples.urljump;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Created by guoyizhe on 2016/11/7.
 * 邮箱:gyzboy@126.com
 */

public class ASUrlJump1 extends Activity {

    private PackageManager pm = null;

//    当自定义的URL配置在LAUNCHER对应的Activity上时，上述配置就足够了。
//    但是当自定义的URL配置在非LAUNCHER对应的Activity时，还需要增加额外几步操作。

//    问题一：使用自定义的URL启动Activity时，默认是已FLAG_ACTIVITY_NEW_TASK的方式启动的，所以可能存在URL启动的Activity跟应用已启动的Activity不再同一个堆栈的现象。
//    解决方式：这种情况下，需要在manifest中将Activity多配置一个taskAffinity属性，约束URL启动的Activity与应用自身的启动的Activity在同一个堆栈中。


//    问题二：应用A使用url的方式唤起应用B的Activity时，可能存在应用B的Activity启动了，但是堆栈仍然在后台的现象，即应用B的Activity没有聚焦的问题。
//    解决方式：这种情况下，应用B的Activity收到启动的请求后，可以主动将Activity对应的堆栈移动到最前端。

//    ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//    activityManager.moveTaskToFront(getTaskId(), ActivityManager.MOVE_TASK_WITH_HOME);
//    使用这种方式需要注意的是该api是在Android 3.0（api 11）以后才提供的，现在基本上手机rom版本都是Android4.4以上了，就不太需要关注3.0一下怎么处理了，且使用这个需要在manifest中申请android.permission.REORDER_TASKS权限。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final Button button = new Button(this);
        button.setText("跳转");
        getWindow().getDecorView().getRootView().post(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) button.getLayoutParams();
                params.width = FrameLayout.LayoutParams.MATCH_PARENT;
                params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
                button.setLayoutParams(params);
            }
        });
        setContentView(button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Navi.from(ASUrlJump1.this).to("app://go");
                Navi.from(ASUrlJump1.this).withIntercepter(new CustomIntercepter(ASUrlJump1.this)).to("app://otherroute");
            }
        });
    }
}
