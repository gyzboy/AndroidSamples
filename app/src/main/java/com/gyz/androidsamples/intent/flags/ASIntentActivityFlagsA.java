package com.gyz.androidsamples.intent.flags;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/6/13.
 * 邮箱:gyzboy@126.com
 */
public class ASIntentActivityFlagsA extends Activity{
    //Intent.FLAG_ACTIVITY_NEW_TASK
    //默认的跳转类型,它会重新创建一个新的Activity，不过与这种情况，比如说Task1中有A,B,C三个Activity,此时在C中启动D的话，
    //如果在AndroidManifest.xml文件中给D添加了Affinity的值和Task中的不一样的话，则会在新标记的Affinity所存在的Task中压入这个Activity。
    //如果是默认的或者指定的Affinity和Task一样的话，就和标准模式一样了启动一个新的Activity.

    //FLAG_ACTIVITY_SINGLE_TOP
    //这个FLAG就相当于启动模式中的singletop，例如:原来栈中结构是A B C D，在D中启动D，栈中的情况还是A,B,C,D。

    //FLAG_ACTIVITY_CLEAR_TOP
    //这个FLAG就相当于启动模式中的SingleTask，这种FLAG启动的Activity会把要启动的Activity之上的Activity全部弹出栈空间。
    // 例如：原来栈中的结构是A B C D ，从D中跳转到B，栈中的结构就变为了A B了。

    //FLAG_ACTIVITY_BROUGHT_TO_FRONT
    //通常不在应用代码中设置，由系统与singleTask共同使用时使用，暂时没看出效果

    // FLAG_ACTIVITY_REORDER_TO_FRONT
    // 启动顺序为A B C D,在D中使用FLAG_ACTIVITY_REORDER_TO_FRONT启动B，则栈内元素变为A C D B

    //FLAG_ACTIVITY_NO_USER_ACTION
    //onUserLeaveHint()作为activity周期的一部分，它在activity因为用户要跳转到别的activity而要退到background时使用。
    //比如,在用户按下Home键，它将被调用。比如有电话进来（不属于用户的选择），它就不会被调用。
    //那么系统如何区分让当前activity退到background时使用是用户的选择？
    //它是根据促使当前activity退到background的那个新启动的Activity的Intent里是否有FLAG_ACTIVITY_NO_USER_ACTION来确定的。
    //注意：调用finish()使该activity销毁时不会调用该函数

    //FLAG_ACTIVITY_NO_HISTORY
    //意思就是说用这个FLAG启动的Activity，一旦退出，它不会存在于栈中，
    // 比方说！原来是A,B这个时候在B中以这个FLAG启动C的，C再启动D，这个时候栈中情况为A,B,D。
    //设置此属性后，onActivityResult也会失去作用


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_text_jump);

        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(ASIntentActivityFlagsA.class.getSimpleName());

        Button button = (Button) findViewById(R.id.jump);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ASIntentActivityFlagsA.this,ASIntentActivityFlagsB.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.d("ActivityFlag","onUserLeaveHintAA");
    }
}
