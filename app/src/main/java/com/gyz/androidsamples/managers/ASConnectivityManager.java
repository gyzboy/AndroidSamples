package com.gyz.androidsamples.managers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/7/12.
 * 邮箱:gyzboy@126.com
 */
public class ASConnectivityManager extends Activity {

    private TextView tv_showState;
    private TextView tv_showNetChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conn_manager);

        tv_showState = (TextView) findViewById(R.id.showState);
        tv_showNetChange = (TextView) findViewById(R.id.showNetChange);

//        NetworkInfo，该类用于描述网络的状态（mobile和wifi）

    }

    public void getNetworkState(View v) {
        //获取
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        StringBuilder sb = new StringBuilder();
        sb.append("TypeName:").append(networkInfo.getTypeName()).append("\n");
        sb.append("Type:").append(networkInfo.getType()).append("\n");
        sb.append("isAvailable:").append(networkInfo.isAvailable()).append("\n");
        sb.append("isConnected:").append(networkInfo.isConnected()).append("\n");
        NetworkInfo.DetailedState state = networkInfo.getDetailedState();
        String detailedState = "";
        if (state == NetworkInfo.DetailedState.AUTHENTICATING) {
            detailedState = "AUTHENTICATING";
        } else if (state == NetworkInfo.DetailedState.BLOCKED) {
            detailedState = "BLOCKED";
        } else if (state == NetworkInfo.DetailedState.CONNECTED) {
            detailedState = "CONNECTED";
        } else if (state == NetworkInfo.DetailedState.CONNECTING) {
            detailedState = "CONNECTING";
        } else if (state == NetworkInfo.DetailedState.DISCONNECTED) {
            detailedState = "DISCONNECTED";
        } else if (state == NetworkInfo.DetailedState.DISCONNECTING) {
            detailedState = "DISCONNECTING";
        } else if (state == NetworkInfo.DetailedState.FAILED) {
            detailedState = "FAILED";
        } else if (state == NetworkInfo.DetailedState.IDLE) {
            detailedState = "IDLE";
        } else if (state == NetworkInfo.DetailedState.SCANNING) {
            detailedState = "SCANNING";
        } else if (state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
            detailedState = "OBTAINING_IPADDR";
        } else if (state == NetworkInfo.DetailedState.SUSPENDED) {
            detailedState = "SUSPENDED";
        }
        sb.append("DetailedState:").append(detailedState).append("\n");
        tv_showState.setText(sb.toString());
    }

    /**
     * 获取搜有网络
     *
     * @param v
     */
    public void getNetwork(View v) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        StringBuilder stringBuilder = new StringBuilder();
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo info : networkInfos) {
            stringBuilder.append("Name:").append(info.getTypeName())
                    .append(";isAvailable:").append(info.isAvailable()).append("\n");
        }
        tv_showNetChange.setText(stringBuilder.toString());
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info != null) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        tv_showNetChange.setText("网络变化：网络已连接！");
                    } else {
                        tv_showNetChange.setText("网络变化：网络已断开！");
                    }
                } else {
                    tv_showNetChange.setText("网络变化：无网络！");
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
