package com.gyz.androidsamples.activity.XMLtags;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gyz.androidsamples.R;
import com.gyz.androidsamples.utils.ActivityInfo;

/**
 * Created by guoyizhe on 16/6/13.
 * 邮箱:gyzboy@126.com
 */
public class OneActivity extends Activity{
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_text_jump);

        textView = (TextView) findViewById(R.id.textView);

        Button button = (Button) findViewById(R.id.jump);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OneActivity.this,TwoActivity.class);
                startActivity(intent);
            }
        });
    }

    //alwaysRetainTaskState
    //如果在任务的根 Activity 中将此属性设置为 "true"，则不会发生刚才所述的默认行为。即使在很长一段时间后，任务仍将所有 Activity 保留在其堆栈中。
    //clearTaskOnLaunch
    //如果在任务的根 Activity 中将此属性设置为 "true"，则每当用户离开任务然后返回时，系统都会将堆栈清除到只剩下根 Activity。 换而言之，它与 alwaysRetainTaskState 正好相反。 即使只离开任务片刻时间，用户也始终会返回到任务的初始状态。
    //finishOnTaskLaunch
    //此属性类似于 clearTaskOnLaunch，但它对单个 Activity 起作用，而非整个任务。 此外，它还有可能会导致任何 Activity 停止，包括根 Activity。 设置为 "true" 时，Activity 仍是任务的一部分，但是仅限于当前会话。如果用户离开然后返回任务，则任务将不复存在。

    @Override
    protected void onResume() {
        super.onResume();
        textView.setText(String.valueOf(ActivityInfo.getTaskInfo(this).numActivities));
    }
}
