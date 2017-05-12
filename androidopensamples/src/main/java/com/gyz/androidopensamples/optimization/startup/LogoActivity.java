package com.gyz.androidopensamples.optimization.startup;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by gyzboy on 2017/5/10.
 */

public class LogoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if (firstRun) {
        //    //logo空界面
        //}
        //else {
        //   //app主界面
        //}
        finish();
    }
}
