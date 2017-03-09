/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.gyz.androidopensamples.aop.sample.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.gyz.androidopensamples.R;

/**
 *
 */
public class LinearLayoutTestActivity extends Activity {

  private LinearLayout myLinearLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_linear_layout_test);

    myLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutOne);
    myLinearLayout.invalidate();
  }
}
