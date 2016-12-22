package com.gyz.androidsamples.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by guoyizhe on 16/6/12.
 * 邮箱:gyzboy@126.com
 */

/**
 * 1、安装，卸载应用
 * 2、查询permission相关信息
 * 3、查询Application相关信息(application，activity，receiver，service，provider及相应属性等）
 * 4、查询已安装应用
 * 5、增加，删除permission
 * 6、清除用户数据、缓存，代码段等
 */
public class PackageInfo {


    public static Drawable getAppIcon(Context context, String pkgName) {
        try {
            return context.getPackageManager().getApplicationIcon(pkgName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0 系统应用
    //applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0 非系统应用
    //applicationInfo.flags == ApplicationInfo.FLAG_SYSTEM SDCard应用
    public static ApplicationInfo getAppInfo(Context context, String pkgName, int flags) {
        try {
            return context.getPackageManager().getApplicationInfo(pkgName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ApplicationInfo> getAppInfos(Context context, int flags) {
        return context.getPackageManager().getInstalledApplications(flags);
    }

    /**
     * 根据<intent>节点来获取其上一层目录的信息，通常是<activity>、<receiver>、<service>节点信息。
     * 常用方法有loadIcon(PackageManager pm)和loadLabel(PackageManager pm)
     *
     * @param context
     * @param intent
     * @param flags
     * @return
     */
    public static List<ResolveInfo> queryIntentActivities(Context context, Intent intent, int flags) {
        return context.getPackageManager().queryIntentActivities(intent, flags);
    }

    public static android.content.pm.PackageInfo getPkgInfo(Context context, String pkgName){
        try {
            return context.getPackageManager().getPackageInfo(pkgName,0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
