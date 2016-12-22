package com.gyz.androidsamples.intent.actions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/6/14.
 * 邮箱:gyzboy@126.com
 */
public class ASIntentActivityActionA extends Activity {

    //Action 尽管一个Intent只可以设置一个Action，但一个Intentfilter可以持有一个或多个Action用于过滤，
    //到达的Intent只需要匹配其中一个Action即可。 深入思考：如果一个Intentfilter没有设置Action的值，那么，任何一个Intent都不会被通过；
    //反之，如果一个Intent对象没有设置Action值，那么它能通过所有的Intentfilter的Action检查

    //PS:在自定义动作时，使用activity组件时，必须添加一个默认的类别
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_simple_text_jump);

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(ASIntentActivityActionA.class.getSimpleName());

        Button button = (Button) findViewById(R.id.jump);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.gyz.androidsamples.action.custom");
                startActivity(intent);
            }
        });

    }
}
