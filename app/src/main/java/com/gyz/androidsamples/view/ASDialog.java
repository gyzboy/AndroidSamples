package com.gyz.androidsamples.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.gyz.androidsamples.R;

/**
 * Created by guoyizhe on 16/7/21.
 * 邮箱:gyzboy@126.com
 */
public class ASDialog extends Activity {
    // 加载重命名输入框布局文件
    private LayoutInflater factory;
    // 重命名视图
    private View renameView;

    // 显示文本
    private TextView showInfo;

    // 用户输入新名字的输入框
    private EditText input;

    // 上下文信息
    private Context mContext;

    // 显示Dialog的ID
    private static final int SHOW_RENAME_DIALOG = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_show);
        mContext = this;
        initUI();
    }

    /*
     * dialog内部就是新建了一个window,然后将这个window的decorview add到windowmanager上
     *
     *
     * hide与dismiss的区别:
     * hide只是将decorview设置为gone
     * dismiss则是将decorview整个的remove掉
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        switch (id) {
            case SHOW_RENAME_DIALOG:
                dialog = new AlertDialog.Builder(mContext)
                        .setTitle(showInfo.getText())
                        .setView(renameView)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        showInfo.setText(input.getText());

                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                    }
                                }).create();
                //如果同时设置了FLAG_NOT_FOCUSABLE选项和FLAG_ALT_FOCUSABLE_IM，窗口将能够与输入法交互，允许输入法窗口覆盖；
                //如果FLAG_NOT_FOCUSABLE没有设置而设置了FLAG_ALT_FOCUSABLE_IM，窗口不能与输入法交互，可以覆盖输入法窗口。
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
//                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
//                        WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                break;
            default:
                dialog = null;
        }

        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
        switch (id) {
            case SHOW_RENAME_DIALOG:
                dialog.setTitle(showInfo.getText());
                break;
        }
        super.onPrepareDialog(id, dialog, args);
    }

    /**
     * 初始化控件
     * */
    private void initUI() {
        factory = (LayoutInflater) mContext
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        renameView = factory.inflate(R.layout.view_dialog, null);
        input = (EditText) renameView.findViewById(R.id.new_name);
        showInfo = (TextView) findViewById(R.id.showinfo);
        showInfo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                showDialog(SHOW_RENAME_DIALOG);
            }
        });



        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setIcon(R.mipmap.ic_launcher);//
        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
        dialog.setTitle("提示");
        dialog.setIndeterminate(true);
//        dialog.setIndeterminateDrawable(getResources().getDrawable(R.mipmap.ic_launcher));//圆环图像
        dialog.setMax(500);
        // dismiss监听
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                // TODO Auto-generated method stub

            }
        });
        // 监听Key事件被传递给dialog
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                return false;
            }
        });
        // 监听cancel事件
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub

            }
        });
        //设置可点击的按钮，最多有三个(默认情况下)
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "中立",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });
        dialog.setMessage("这是一个圆形进度条");
        dialog.show();
    }

}
