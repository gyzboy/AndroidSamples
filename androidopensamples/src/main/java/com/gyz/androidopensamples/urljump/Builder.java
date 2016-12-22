package com.gyz.androidopensamples.urljump;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by guoyizhe on 2016/11/8.
 * 邮箱:gyzboy@126.com
 */
 class Builder {
    private Bundle mBundle;
    private PackageManager pm;
    private Context mContext;

    Builder(Context context) {
        mContext = context;
        pm = context.getPackageManager();

    }

    @NonNull public Builder withExtras(@NonNull Bundle bundle) {
        mBundle = bundle;
        return this;
    }

    public void to(@NonNull String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (mBundle != null) {
            intent.putExtras(mBundle);
        }
        intent.setData(Uri.parse(url));
        ResolveInfo info = pm.resolveActivity(intent, 0);
        if (info != null) {
            mContext.startActivity(intent);
        } else {
            intent.setClass(mContext, ErrorPage.class);
            mContext.startActivity(intent);
        }
    }
}
