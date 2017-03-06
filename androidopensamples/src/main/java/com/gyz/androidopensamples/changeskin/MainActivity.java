package com.gyz.androidopensamples.changeskin;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;

import com.gyz.androidopensamples.R;
import com.gyz.androidopensamples.changeskin.app.SkinnableActivity;

/**
 * Created by guoyizhe on 2017/3/6.
 * 邮箱:gyzboy@126.com
 */

//这个换肤思路主要是利用了layoutInflater.setFactory这个方法,这个方法可以注入自定义的layoutInfalter,这样类似于
    //appcompatActivity中的做法,在onCreateView中把appcompatTextView替换为自定义的TextView即可,这样使用原始控件的也同时具有了相应效果
    //利用OO特性,所有的view都继承一个skinnable的接口来标记上这个属于自定义类

    //关于不重启换肤:
    //通过Context.getAttr方法取到view上的色值,这样就需要view上的色值不可以是绝对值状态,必须是@Color/xxx,然后手动的setBackGroud赋值为自己想要的即可


    //或者使用自定义资源加载实现换肤:
//AssetManager assetManager = AssetManager.class.newInstance();
//Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
//addAssetPath.invoke(assetManager, skinPath);
//
//Resources superRes = mContext.getResources();
//mResources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
//mResourceManager = new ResourceManager(mResources, skinPkgName, suffix);
public class MainActivity extends SkinnableActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeskin_main);
        findViewById(R.id.btn_change).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO: {
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                // Night mode is not active, we're in day time
                break;
            }
            case Configuration.UI_MODE_NIGHT_YES: {
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                // Night mode is active, we're at night!
                break;
            }
            case Configuration.UI_MODE_NIGHT_UNDEFINED: {
                // We don't know what mode we're in, assume notnight
                break;
            }
            default:

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d("new configuration", "up");
        super.onConfigurationChanged(newConfig);
    }
}
