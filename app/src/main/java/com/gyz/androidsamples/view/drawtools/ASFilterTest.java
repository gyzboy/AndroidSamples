package com.gyz.androidsamples.view.drawtools;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 2016/10/24.
 * 邮箱:gyzboy@126.com
 */

public class ASFilterTest extends Activity {
    EmbossMaskFilter emboss;// 为Paint增加边缘效果
    BlurMaskFilter blur;// 模糊效果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        emboss = new EmbossMaskFilter(new float[]
                { 1.5f, 1.5f, 1.5f }, 0.6f,	6, 4.2f);
        blur = new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL);
    }

    // 创建选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = new MenuInflater(this);
        // 装载R.menu.my_menu对应的菜单，并添加到menu中
        inflator.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        ASCanvas view = (ASCanvas) findViewById(R.id.my_view);

        switch (item.getItemId()) {
            case R.id.red:
                view.mPaint.setColor(Color.RED);
                item.setChecked(true);
                break;
            case R.id.blue:
                view.mPaint.setColor(Color.BLUE);
                item.setChecked(true);
                break;
            case R.id.green:
                view.mPaint.setColor(Color.GREEN);
                item.setChecked(true);
                break;
            case R.id.width_1:
                view.mPaint.setStrokeWidth(1);
                item.setChecked(true);
                break;
            case R.id.width_3:
                view.mPaint.setStrokeWidth(3);
                item.setChecked(true);
                break;
            case R.id.width_5:
                view.mPaint.setStrokeWidth(5);
                item.setChecked(true);
                break;
            case R.id.emboss:
                view.mPaint.setMaskFilter(emboss);
                item.setChecked(true);
                break;
            case R.id.blur:
                view.mPaint.setMaskFilter(blur);
                item.setChecked(true);
                break;
        }
        return true;
    }
}
