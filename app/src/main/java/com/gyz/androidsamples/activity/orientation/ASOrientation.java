package com.gyz.androidsamples.activity.orientation;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/6/12.
 * 邮箱:gyzboy@126.com
 */
public class ASOrientation extends Activity {
    Spinner mOrientation;

    // Orientation spinner choices
    // This list must match the list found in samples/ApiDemos/res/values/arrays.xml
    final static int mOrientationValues[] = new int[]{
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED,//不处理
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE,//指定横屏
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT,//指定竖屏
            ActivityInfo.SCREEN_ORIENTATION_USER,//根据用户朝向
            ActivityInfo.SCREEN_ORIENTATION_BEHIND,
            ActivityInfo.SCREEN_ORIENTATION_SENSOR,
            ActivityInfo.SCREEN_ORIENTATION_NOSENSOR,//不受重力影响
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE,//横屏动态转换
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT,//竖屏动态转换
            ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE,
            ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT,
            ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR,//根据重力
            ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE,
            ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT,
            ActivityInfo.SCREEN_ORIENTATION_FULL_USER,
            ActivityInfo.SCREEN_ORIENTATION_LOCKED,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);

        mOrientation = (Spinner) findViewById(R.id.orientation);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.screen_orientations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOrientation.setAdapter(adapter);
        mOrientation.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        setRequestedOrientation(mOrientationValues[position]);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                    }
                });

        Log.e("ASOrientation", "onCreate");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //切换到了横屏
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //切换到了竖屏
        }
        super.onConfigurationChanged(newConfig);
        Log.e("ASOrientation", "onConfigurationChanged");
    }
}
