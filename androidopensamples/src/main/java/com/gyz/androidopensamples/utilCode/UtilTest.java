package com.gyz.androidopensamples.utilCode;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.ScrollView;
import android.widget.TextView;
import com.gyz.androidopensamples.utilCode.AppUtils.AppInfo;
import com.taobao.orange.OrangeConfig;
import com.youku.ranger.RangerManager;
import com.youku.ranger.configs.ConfigForOrange;

/**
 * Created by gyzboy on 2017/7/18.
 */

public class UtilTest extends Activity {

    private int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<AppInfo> infos = AppUtils.getAppsInfo(this);
        ScrollView sv = new ScrollView(this);
        TextView tv = new TextView(this);
        StringBuilder sb = new StringBuilder();
        int size = infos.size();
        //for (int i = 0; i < size; i++) {
        //    if (!infos.get(i).isSystem()) {
        //        count++;
        //        sb.append("appName: " +infos.get(i).getPackageName() + "    " + infos.get(i).getName() + "\n");
        //    }
        //}

        //List<File> list = FileUtils.listFilesInDir(Environment.getDataDirectory().toString() + "/");
        //for (File file : list) {
        //    System.out.println(file.getName());
        //}

        //List<File> file = FileUtils.listFilesInDir(Environment.getExternalStorageDirectory().getAbsolutePath(), false);
        //String dir = Environment.getExternalStorageDirectory().toString();
        //System.out.println(dir);
        ////File[] files = dir.listFiles();
        ////System.out.println(files.length);
        //System.out.println(Environment.getExternalStorageState().equals(
        //    Environment.MEDIA_MOUNTED));
        //for (int i = 0; i < file.size(); i++) {
        //    System.out.println(file.get(i).getName());
        //}
        OrangeConfig.getInstance().init(UtilTest.this,"23570660","6.8.2",2,1);
        RangerManager.getInstance().init(UtilTest.this);
        ConfigForOrange.getInstance().config(UtilTest.this);
        //tv.setText("app数量为:" + count + "\n" + sb.toString());
        //sv.addView(tv);
        setContentView(sv);

    }
}
