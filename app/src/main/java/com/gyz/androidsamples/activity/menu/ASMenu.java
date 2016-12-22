package com.gyz.androidsamples.activity.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gyz.androidsamples.R;

import java.lang.reflect.Method;

/**
 * Created by guoyizhe on 16/7/29.
 * 邮箱:gyzboy@126.com
 */
public class ASMenu extends Activity {
    private static final int sMenuExampleResources[] = {
            R.menu.title_icon,R.menu.title_only,  R.menu.submenu, R.menu.groups,
            R.menu.checkable, R.menu.shortcuts, R.menu.order, R.menu.category_order,
            R.menu.visible, R.menu.disabled
    };

    private static final String sMenuExampleNames[] = {
            "Title and Icon","Title only",  "Submenu", "Groups",
            "Checkable", "Shortcuts", "Order", "Category and Order",
            "Visible", "Disabled"
    };

    private Spinner mSpinner;

    private TextView mInstructionsText;

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sMenuExampleNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner = new Spinner(this);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                invalidateOptionsMenu();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        layout.addView(mSpinner,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        mInstructionsText = new TextView(this);
        mInstructionsText.setText("选择menu类型，然后点击右上角menu，注意activity主题设置");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);
        layout.addView(mInstructionsText, lp);

        setContentView(layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        setIconEnable(menu,true);

        mMenu = menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(sMenuExampleResources[mSpinner.getSelectedItemPosition()], menu);

        return true;
    }

    /**
     * 在android4.0以后，menubuilder源码中mOptionalIconsVisible=false，默认是关闭的，需要通过反射打开
     * @param menu
     * @param enable
     */
    private void setIconEnable(Menu menu, boolean enable)
    {
        try
        {
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);
            //下面传入参数
            m.invoke(menu, enable);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.jump:
                Toast.makeText(this, "Jump up in the air!", Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();
                return true;

            case R.id.dive:
                Toast.makeText(this, "Dive into the water!", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.browser_visibility:
                final boolean shouldShowBrowser = !mMenu.findItem(R.id.refresh).isVisible();
                mMenu.setGroupVisible(R.id.browser, shouldShowBrowser);
                break;

            case R.id.email_visibility:
                final boolean shouldShowEmail = !mMenu.findItem(R.id.reply).isVisible();
                mMenu.setGroupVisible(R.id.email, shouldShowEmail);
                break;

            default:
                if (!item.hasSubMenu()) {
                    Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                break;
        }

        return false;
    }

}
