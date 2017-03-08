package com.gyz.androidopensamples.urljump;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by guoyizhe on 2017/3/8.
 * 邮箱:gyzboy@126.com
 */

public class CustomIntercepter implements IIntercepter {

    private Context mContext;

    public CustomIntercepter(Context context) {
        mContext = context;
    }

    @Override
    public boolean process() {
        Toast.makeText(mContext,"this is before Navi go",Toast.LENGTH_SHORT).show();
        return true;
    }
}
