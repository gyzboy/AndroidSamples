package com.gyz.androidsamples.intent.actions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyz.androidsamples.R;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Created by guoyizhe on 16/6/14.
 * 邮箱:gyzboy@126.com
 */
public class ASIntentActivityActionB extends Activity {

    //ACTION_ANSWER     接收来电
//    ACTION_CALL       打开一个电话拨号程序
//    ACTION_CALL_BUTTON    当用户按下硬件的拨打按钮时触发
//    ACTION_DELETE    启动一个Activity，允许删除Intent的数据URI中指定的数据
//    ACTION_DIAL        打开一个拨号程序
//    ACTION_SEARCH    启动一个可以搜索的Activity
//    ACTION_SEND     启动一个Activity，该Activity会发送Intent中指定的数据
//    ACTION_VIEW      最常见的动作，视图以最合理的方式显示Intent中的数据
//    ACTION_WEB_SEARCH  打开一个浏览器执行搜索

//action匹配规则：要求intent中的action 存在 且 必须和过滤规则中的其中一个相同 区分大小写；

//category匹配规则：系统会默认加上一个android.intent.category.DEAFAULT，所以intent中可以不存在category，但如果存在就必须匹配其中一个

//data匹配规则：data由两部分组成，mimeType和URI，要求和action相似。如果没有指定URI，则必须uri为content或者file，
// 因为URI默认值为content和file（schema）,一个典型的URL格式:
// scheme://host:port/path。例如：http://www.google.com

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_action_b);

        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_container);

        String[] strs = getResources().getStringArray(R.array.intent_actions);

        for (i = 0; i < strs.length; i++) {
            final Button button = new Button(this);
            final String action = strs[i];
            final int index = i;
            button.setText(action);
            button.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              todo(index);
                                          }
                                      }

            );
            layout.addView(button);
        }
    }

    private void todo(int type) {
        switch (type) {
            case 0:
                browsePageClick();
                break;
            case 1:
                openDialPageClick();
                break;
            case 2:
                dialPhoneClick();
                break;
            case 3:
                openMsgPageClick();
                break;
            case 4:
                sendMsgClick();
                break;
            case 5:
                playMusicClick();
                break;
            case 6:
                uninstallClick();
                break;
            case 7:
                installClick();
                break;
            case 8:
                shareText();
                break;
            case 9:
                shareTitleChanged();
                break;
            case 10:
                shareImg();
                break;
            case 11:
                shareImgList();
                break;
            case 12:
                shareToCustomActivity();
                break;
        }
    }

    //打开指定网页
    public void browsePageClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com/"));
        startActivity(intent);

    }

    //打开拨号面板
    public void openDialPageClick() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);
    }

    //直接拨打指定号码
    public void dialPhoneClick() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:777777"));
            startActivity(intent);
        } catch (SecurityException e) {

        }
    }

    //打开发短信的界面:action+type
    public void openMsgPageClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("vnd.android-dir/mms-sms");
        intent.putExtra("sms_body", "具体短信内容"); //"sms_body"为固定内容
        startActivity(intent);
    }

    //打开发短信的界面(指定电话号码):action+data
    public void sendMsgClick() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:18780260012"));
        intent.putExtra("sms_body", "具体短信内容"); //"sms_body"为固定内容
        startActivity(intent);
    }

    //播放指定路径音乐
    public void playMusicClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file:///storage/sdcard0/平凡之路.mp3");  //路径也可以写成："/storage/sdcard0/平凡之路.mp3"
        intent.setDataAndType(uri, "audio/mp3"); //方法：Intent android.content.Intent.setDataAndType(Uri data, String type)
        startActivity(intent);
    }

    //卸载某个应用程序，根据包名来识别
    public void uninstallClick() {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri data = Uri.parse("package:com.gyz.androisamples");
        intent.setData(data);
        startActivity(intent);
    }

    //安装某个应用程序，根据apk的文件名来识别
    public void installClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.fromFile(new File("/storage/sdcard0/AndroidSamples/xxx.apk"));//路径不能写成："file:///storage/sdcard0/···"
        intent.setDataAndType(data, "application/vnd.android.package-archive");  //Type的字符串为固定内容
        startActivity(intent);
    }

    //分享文本
    public void shareText() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    //改变分享列表标题
    public void shareTitleChanged() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "share chooser"));
    }

    //分享图片
    public void shareImg() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, "");
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, "share chooser"));
    }

    //分享图片列表
    public void shareImgList() {
        ArrayList<Uri> imageUris = new ArrayList<Uri>();
        imageUris.add(Uri.parse("")); // Add your image URIs here
        imageUris.add(Uri.parse(""));

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "Share images to.."));
    }

    //分享到指定activity
    public void shareToCustomActivity() {
        Intent intent = new Intent(ASIntentActivityActionB.this, ASIntentActivityActionC.class);
        intent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "share chooser"));
    }
}
