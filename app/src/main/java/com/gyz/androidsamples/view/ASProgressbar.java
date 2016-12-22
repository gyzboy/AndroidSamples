package com.gyz.androidsamples.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/8/5.
 * 邮箱:gyzboy@126.com
 */
public class ASProgressbar extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressbar);
        ProgressBar pb = (ProgressBar) findViewById(R.id.pb);
////        pb.setIndeterminateDrawable(getResources().getDrawable(android.R.drawable.progress_indeterminate_horizontal));
//        pb.setProgressDrawable(getResources().getDrawable(android.R.drawable.progress_horizontal));
//        pb.setIndeterminate(false);//是否允许使用不确定模式，在不确定模式下，进度条动画无限循环
    }
}
