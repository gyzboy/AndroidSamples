package com.gyz.androidsamples.intent.flags;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/6/13.
 * 邮箱:gyzboy@126.com
 */
public class ASIntentActivityFlagsC extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_text_jump);

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(ASIntentActivityFlagsC.class.getSimpleName());

        Button button = (Button) findViewById(R.id.jump);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ASIntentActivityFlagsC.this,ASIntentActivityFlagsD.class);
                startActivity(intent);
            }
        });
    }
}
