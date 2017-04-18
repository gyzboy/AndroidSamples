package com.gyz.androidopensamples.hanzitopinyin;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.gyz.androidopensamples.R;
import com.gyz.androidopensamples.hanzitopinyin.HanziToPinyin.Token;

/**
 * Created by gyzboy on 2017/4/18.
 */

public class HanziToPinyinActivity extends Activity {

    private final String str = "这是例子";
    ArrayList<Token> tokens;
    private Button button;
    private TextView textView;
    private StringBuilder sb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_text);
        button = (Button)findViewById(R.id.btn_click);
        textView = (TextView)findViewById(R.id.tv_content);
        sb = new StringBuilder();
        tokens = HanziToPinyin.getInstance().get(str);
        for (Token token : tokens) {
            sb.append("source = " + token.source);
            sb.append(" target = " + token.target);
            sb.append(" type = " + token.type);
            sb.append("\n");

        }
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                textView.setText(sb.toString());
            }
        });
    }
}
