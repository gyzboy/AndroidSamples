package com.gyz.androidsamples.view.inflate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.gyz.androidsamples.R;

public class ASInflate extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflate);
        LinearLayout layout = (LinearLayout) findViewById(R.id.ll);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_inflate_sub,layout,true);
//        layout.addView(view);
    }
}
