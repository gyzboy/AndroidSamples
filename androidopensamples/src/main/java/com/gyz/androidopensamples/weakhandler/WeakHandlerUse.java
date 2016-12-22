package com.gyz.androidopensamples.weakhandler;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by guoyizhe on 2016/11/18.
 * 邮箱:gyzboy@126.com
 */

public class WeakHandlerUse extends Activity {

    private WeakHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new WeakHandler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO
            }
        },5000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);//这样在activity销毁的时候清空所有的回调和message,也可以达到防止内存泄露的目的
    }
}
