package com.gyz.androidsamples.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ActionMode;
import android.view.InputQueue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by guoyizhe on 16/9/9.
 * 邮箱:gyzboy@126.com
 */
public class ASWindow extends Activity {
    public static final String TAG = ASWindow.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //window是作为top-level的抽象类,唯一继承他的是PhoneWindow，它定义了基本的UI策略,比如背景\标题
//        一、定义Callback接口，它包含一系列dispatchXxxx函数和一系列onXxxx函数，用于处理UI事件；
//        二、定义了自己的WindowManager——LocalWindowManager。主要修改了addView函数，设置title。

        //Activity中默认实现了window类中的callback
        Window window = getWindow();
        window.setCallback(new MyCallback(window));

    }


}

class MyCallback implements Window.Callback {
    public static final String TAG = ASWindow.class.getSimpleName();

    private Window mWindow;
    public MyCallback(Window w) {
        mWindow = w;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
            //  处理Key事件。至少，你的代码要调用superDispatchKeyEvent函数。
        if (mWindow.superDispatchKeyEvent(event)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        if (mWindow.superDispatchKeyShortcutEvent(event)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mWindow.superDispatchTouchEvent(event)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        if (mWindow.superDispatchTrackballEvent(event)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        if (mWindow.superDispatchGenericMotionEvent(event)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
//        if (处理完成) {
//            return true;
//        }
        return false;
    }

    @Nullable
    @Override
    public View onCreatePanelView(int featureId) {
        //初始化panel菜单，如果onCreatePanelView函数返回null将调用此函数，创建一个标准菜单，你可以向它添加菜单项。只有当panel第一次显示时方调用此函数。如果返回false，panel将无法显示。
        return null;
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
//       //每次panel窗口显示前，都会调用此函数。应当返回true，如果返回false将不显示。
        return false;
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        //当用户打开panel的菜单时被调用。当菜单样式切换时也会调用。例如，从图标菜单切换至扩展菜单。

        return false;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //        返回true允许菜单打开，返回false阻止菜单打开。
        return false;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return false;
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {

    }

    @Override
    public void onContentChanged() {
        Log.e(TAG,"ContentChanged");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.e(TAG,"WindowFocus is: " + hasFocus);
    }

    @Override
    public void onAttachedToWindow() {
        Log.e(TAG,"AttachedToWindow");
    }

    @Override
    public void onDetachedFromWindow() {
        Log.e(TAG,"DetachedFromWindow");
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {

    }

    @Override
    public boolean onSearchRequested() {
        return false;
    }

    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return false;
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        return null;
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onActionModeFinished(ActionMode mode) {

    }
}

