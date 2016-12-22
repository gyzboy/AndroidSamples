package com.gyz.androidsamples.managers;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/6/16.
 * 邮箱:gyzboy@126.com
 */
public class ASPowerManager extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simple_text_jump);

        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        wl.acquire();

        wl.release();

    }
}
