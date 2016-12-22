package com.gyz.androidsamples.activity.affinity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gyz.androidsamples.R;

public class OneActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_affinity);

		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//taskAffnity的几种情况:
				//1、与FLAG_ACTIVITY_NEW_TASK一起使用的话，新的Activity会在新的task中
				//2、不使用FLAG_ACTIVITY_NEW_TASK，无论如何设置Activity的taskAffnity属性都会在同一个task中

				//allowTaskReparenting
				//允许两个应用的activity存在于同一个task中
				startActivity(new Intent(OneActivity.this,SecondActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			}
		});
		System.out.println(Runtime.getRuntime().maxMemory());
		System.out.println(Runtime.getRuntime().totalMemory());
		Button btn = (Button) findViewById(R.id.button2);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finishAffinity();
			}
		});
		
	}
}
