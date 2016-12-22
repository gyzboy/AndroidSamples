package com.gyz.androidsamples.activity.fullscreen;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/6/13.
 * 邮箱:gyzboy@126.com
 */
public class ASImmersive extends Activity {


    //SYSTEM_UI_FLAG_IMMERSIVE与SYSTEM_UI_FLAG_IMMERSIVE_STICKY都提供了沉浸式的体验，但是在上面的描述中，他们是不一样的，下面讲解一下什么时候该用哪一种标签。
    //如果你在写一款图书浏览器、新闻杂志阅读器，请将IMMERSIVE标签与SYSTEM_UI_FLAG_FULLSCREEN,SYSTEM_UI_FLAG_HIDE_NAVIGATION一起使用。
    //因为用户可能会经常访问Action Bar和一些UI控件，又不希望在翻页的时候有其他的东西进行干扰。IMMERSIVE在该种情况下就是个很好的选择。

    //如果你在打造一款真正的沉浸式应用，而且你希望屏幕边缘的区域也可以与用户进行交互，并且他们也不会经常访问系统UI
    //这个时候就要将IMMERSIVE_STICKY和SYSTEM_UI_FLAG_FULLSCREENSYSTEM_UI_FLAG_HIDE_NAVIGATION两个标签一起使用。比如做一款游戏或者绘图应用就很合适。

    //如果你在打造一款视频播放器，并且需要少量的用户交互操作。
    //你可能就需要之前版本的一些方法了（从Android 4.0开始）。对于这种应用，简单的使用SYSTEM_UI_FLAG_FULLSCREEN与SYSTEM_UI_FLAG_HIDE_NAVIGATION就足够了，不需要使用immersive标签。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_immersive);
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(
                new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int i) {
                        int height = decorView.getHeight();
                        Toast.makeText(ASImmersive.this, "Current height: " + height, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sample_action) {//非sticky模式
            hideSysUI();
        } else if (item.getItemId() == R.id.sample_action2) {
            hideSysUISticky();
        }
        return true;
    }

    private void hideSysUI() {

        //当你使用SYSTEM_UI_FLAG_IMMERSIVE标签的时候，它是基于其他设置过的标签(SYSTEM_UI_FLAG_HIDE_NAVIGATION和SYSTEM_UI_FLAG_FULLSCREEN)来隐藏系统栏的。当用户向内滑动，系统栏重新显示并保持可见。
        //用其他的UI标签(如SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION和SYSTEM_UI_FLAG_LAYOUT_STABLE)来防止系统栏隐藏时内容区域大小发生变化是一种很不错的方法。你也需要确保Action Bar和其他系统UI控件同时进行隐藏。下面这段代码展示了如何在不改变内容区域大小的情况下，隐藏与显示状态栏和导航栏
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE;
        }
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

    }

    private void hideSysUISticky() {
        //当使用了SYSTEM_UI_FLAG_IMMERSIVE_STICKY标签的时候，向内滑动的操作会让系统栏临时显示，
        //并处于半透明的状态。此时没有标签会被清除，系统UI可见性监听器也不会被触发。如果用户没有进行操作，系统栏会在一段时间内自动隐藏

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

}
