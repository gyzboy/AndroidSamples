package com.gyz.androidopensamples.urljump;

import android.app.AlertDialog;
import android.content.ComponentName;
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

public class Navi {

    @NonNull public static Builder from(Context context) {
        return new Builder(context);
    }
}
