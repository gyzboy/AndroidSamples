package com.gyz.androidsamples;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

    private String packName = "com.gyz.androidsamples";
    private boolean isLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String path = intent.getStringExtra(packName);
        isLoaded = intent.getBooleanExtra("isLoaded",false);

        if (path == null) {
            path = "";
        }

        setListAdapter(new SimpleAdapter(this, getData(path),
                android.R.layout.simple_list_item_1, new String[]{"title"},
                new int[]{android.R.id.text1}));
        getListView().setTextFilterEnabled(true);
    }

    protected List<Map<String, Object>> getData(String prefix) {
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory("androidSample");

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        String[] prefixPath;
        String prefixWithSlash = prefix;

        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
            prefixWithSlash = prefix + "/";
        }

        int len = list.size();

        Map<String, Boolean> entries = new HashMap<String, Boolean>();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null
                    ? labelSeq.toString()
                    : info.activityInfo.name;

            if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {

                String[] labelPath = label.split("/");

                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(myData, nextLabel, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }

        Collections.sort(myData, sDisplayNameComparator);
        if (!isLoaded) {
            addItem(myData, "Open", activityIntent(
                    packName,
                    "com.gyz.androidopensamples.MainActivity"));
            isLoaded = true;
        }

        return myData;
    }

    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
            new Comparator<Map<String, Object>>() {
                private final Collator collator = Collator.getInstance();

                public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                    return collator.compare(map1.get("title"), map2.get("title"));
                }
            };

    protected Intent activityIntent(String pkg, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkg, componentName);
        return result;
    }

    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, MainActivity.class);
        result.putExtra(packName, path);
        return result;
    }

    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);

        Intent intent = (Intent) map.get("intent");
        intent.putExtra("isLoaded",true);
        startActivity(intent);
    }


    class lambdaTest{
        private static final String TAG = "LAMBDA_TEST";

        /**
         * 第一种方式,无参数+语句(代码块)：适用于匿名内部类中方法无参数的情况
         * () -> 语句
         * 或
         * () -> {代码块}
         **/
        private void threadTest() {
            /**普通写法**/
            new Thread(new Runnable() {
                @Override
                public void run() {

                }
            }).start();
            /**使用lambda表达式写法**/
//            new Thread(() -> Log.d(TAG, "this is a lambda Thread")).start();
        }

        /**
         * 第二种方式，有参数+语句：适用于匿名内部类中方法只有一个参数的情况
         * 方法参数 -> 语句
         * 或
         * 方法参数 -> {代码块}
         */
        private void setOnClick() {
            /**普通写法**/
            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "this is a general onClick");
                }
            });
            /**使用lambda表达式写法**/
//            findViewById(R.id.button).setOnClickListener(v -> Log.d(TAG, "this is a lambda onClick"));
        }

        /**
         * 第三种方式，有参数+代码块：适用于匿名内部类中方法不止一个参数的情况
         * (参数1, 参数2) -> 语句
         * 或
         * (参数1, 参数2) -> {代码块}
         */
        private void setOnChecked() {
            CheckBox checkBox = new CheckBox(MainActivity.this);
            /**普通写法**/
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d(TAG, "this is a general onCheckedChanged");
                }
            });
            /**使用lambda表达式写法**/
//            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                Log.d(TAG, "this is a lambda onCheckedChanged");
//                Log.d(TAG, "this is a lambda onCheckedChanged_isChecked=" + isChecked);
//            });
        }
    }

}
