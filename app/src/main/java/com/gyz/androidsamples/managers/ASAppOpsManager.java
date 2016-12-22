package com.gyz.androidsamples.managers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * Created by guoyizhe on 16/7/28.
 * 邮箱:gyzboy@126.com
 */
public class ASAppOpsManager extends Activity {

//    AppOpsService具体处理用户的各项设置，用户的设置项存储在 /data/system/appops.xml文件中。
//    AppOpsService也会被注入到各个相关的系统服务中，进行权限操作的检验。
//
//    各个权限操作对应的系统服务（比如定位相关的Location Service，Audio相关的Audio Service等）中注入AppOpsService的判断。如果用户做了相应的设置，那么这些系统服务就要做出相应的处理。
//    （比如，LocationManagerSerivce的定位相关接口在实现时，会有判断调用该接口的app是否被用户设置成禁止该操作，如果有该设置，就不会继续进行定位。）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(checkOp(this,24)){//检测是否有弹窗权限
            Toast.makeText(this,"有弹窗权限",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"没弹窗权限",Toast.LENGTH_SHORT).show();
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class managerClass = manager.getClass();
                Method method = managerClass.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                int isAllowNum = (Integer) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
                if (op == 24 && (AppOpsManager.MODE_ALLOWED == isAllowNum || AppOpsManager.MODE_DEFAULT == isAllowNum)) {
                    return true; //AppOpsManager.OP_SYSTEM_ALERT_WINDOW
                }
                if (AppOpsManager.MODE_ALLOWED == isAllowNum) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
