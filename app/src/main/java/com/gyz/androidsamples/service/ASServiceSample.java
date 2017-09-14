package com.gyz.androidsamples.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.gyz.androidsamples.R;

/**
 * Created by gyzboy on 2017/9/12.
 */

public class ASServiceSample extends Activity implements OnClickListener{

    public static final String TAG = ASServiceSample.class.getSimpleName();

    private Button startService;

    private Button stopService;

    private Button bindService;

    private Button unbindService;

    private ASService.MyBinder myBinder;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myBinder = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (ASService.MyBinder) service;
            myBinder.startDownload();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Log.e(TAG,"activity thread" + Thread.currentThread().getName());
        startService = (Button) findViewById(R.id.start_service);
        stopService = (Button) findViewById(R.id.stop_service);
        bindService = (Button) findViewById(R.id.bind_service);
        unbindService = (Button) findViewById(R.id.unbind_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
    }

    /**
     * 一个Service必须要在既没有和任何Activity关联又处理停止状态的时候才会被销毁
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                Intent startIntent = new Intent(this, ASService.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                Log.d("ASService", "click destroy Service button");
                Intent stopIntent = new Intent(this, ASService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this, ASService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                Log.d("ASService", "click Unbind Service button");
                if (myBinder != null) {
                    unbindService(connection);
                }
                break;
            default:
                break;
        }
    }
}
