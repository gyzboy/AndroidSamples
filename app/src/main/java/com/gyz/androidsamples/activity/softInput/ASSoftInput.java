package com.gyz.androidsamples.activity.softInput;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/6/12.
 * 邮箱:gyzboy@126.com
 */
public class ASSoftInput extends Activity {
    Spinner mResizeMode;
    final CharSequence[] mResizeModeLabels = new CharSequence[]{
            "Unspecified", "Resize", "Pan", "Nothing"
    };
    final int[] mResizeModeValues = new int[]{
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED,//默认设置，通常由系统自行决定是隐藏还是显示
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE,//该Activity总是调整屏幕的大小以便留出软键盘的空间
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN,//当前窗口的内容将自动移动以便当前焦点从不被键盘覆盖和用户能总是看到输入内容的部分
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING,

            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE,
            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN,//用户选择activity时，软键盘总是被隐藏
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN,//当该Activity主窗口获取焦点时，软键盘也总是被隐藏的
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE,
            WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED,//当这个activity出现时，软键盘将一直保持在上一个activity里的状态，无论是隐藏还是显示
            WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED//软键盘的状态并没有指定，系统将选择一个合适的状态或依赖于主题的设置
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_softinput);

        mResizeMode = (Spinner) findViewById(R.id.resize_mode);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_spinner_item, mResizeModeLabels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mResizeMode.setAdapter(adapter);
        mResizeMode.setSelection(0);
        mResizeMode.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        getWindow().setSoftInputMode(mResizeModeValues[position]);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        getWindow().setSoftInputMode(mResizeModeValues[0]);
                    }
                });
    }
}
