package com.gyz.androidsamples.managers;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import com.gyz.androidsamples.R;

import java.io.FileDescriptor;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by guoyizhe on 16/7/14.
 * 邮箱:gyzboy@126.com
 */
public class ASDownloadManager extends Activity {

    String requestUrl = "http://hz.youku.com/red/click.php?tp=1&cp=4007551&cpp=1000673&url=http://dl.m.cc.youku.com/android/phone/Youku_Android_pcdaoliu.apk";

    private DownloadManager downloadManager;
    private Long DL_ID;
    private SharedPreferences prefs;

    private TextView tv_info;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_manager);
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        tv_info = (TextView) findViewById(R.id.tv_info);
    }

    public void beginDownload(View view) {
        // String url =
        // "http://developer.android.com/shareables/icon_templates-v4.0.zip";//出现illeage错误提示是因为手机没有开启下载服务
        // 开始下载
        Uri resource = Uri.parse(requestUrl);
        DownloadManager.Request request = new DownloadManager.Request(
                resource);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                | DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);
        request.addRequestHeader("Cache-Control","no-cache");
        // 设置文件类型
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap
                .getMimeTypeFromExtension(MimeTypeMap
                        .getFileExtensionFromUrl(requestUrl));
        request.setMimeType(mimeString);
        // 在通知栏中显示
        request.setNotificationVisibility(View.VISIBLE);
        request.setVisibleInDownloadsUi(true);
        // sdcard的目录下的download文件夹
        request.setDestinationInExternalPublicDir("/download/",
                "youku.apk");
        request.setTitle("优酷下载中....");
        request.setDescription("优酷主客户端");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);// 设置下载完成也显示Notification,默认只有在下载中才显示
        DL_ID = downloadManager.enqueue(request);
        prefs.edit().putLong("id",DL_ID).commit();

    }

    public void DownloadState(View view) {
        // 下载已经开始，检查状态
        queryDownloadStatus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        filter.addAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        registerReceiver(receiver, filter);
//		registerReceiver(receiver,new IntentFilter(DownloadManager.ACTION_VIEW_DOWNLOADS));
    }

    /**
     * 如果服务器不支持中文路径的情况下需要转换url的编码。
     *
     * @param string
     * @return
     */
    public String encodeGB(String string) {
        // 转换中文编码
        String split[] = string.split("/");
        for (int i = 1; i < split.length; i++) {
            try {
                split[i] = URLEncoder.encode(split[i], "GB2312");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            split[0] = split[0] + "/" + split[i];
        }
        split[0] = split[0].replaceAll("\\+", "%20");// 处理空格
        return split[0];
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 这里可以取得下载的id，这样就可以知道哪个文件下载完成了。适用与多个下载任务的监听
            Log.v("intent", "" + intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));

            switch (intent.getAction()){
                case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
                    Log.v("downloadManager","下载完成");
                    break;
                case DownloadManager.ACTION_NOTIFICATION_CLICKED:
                    Log.v("downloadManager","下载中点击通知栏");
                    break;
                case DownloadManager.ACTION_VIEW_DOWNLOADS:
                    Log.v("downloadManager","打开新activity显示全部downloads");
                    break;
            }
        }
    };

    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(prefs.getLong("id",0)); // 根据下载ID进行过滤
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c
                    .getColumnIndex(DownloadManager.COLUMN_STATUS));
            int fileNameIdx = c
                    .getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
            int fileUriIdx = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

            int fileSizeIdx = c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            int bytesDLIdx = c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            int fileLastModifyIdx = c.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP);
            int fileTypeIdx = c.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE);

            String fileName = c.getString(fileNameIdx);// 获得下载文件的文件名
            String fileUri = c.getString(fileUriIdx);// 获得下载文件的URI
            String fileSize = c.getString(fileSizeIdx);//获得下载文件名
            String byteSize = c.getString(bytesDLIdx);//获得下载剩余
            String fileLastModify = c.getString(fileLastModifyIdx);//最后修改时间
            String fileType = c.getString(fileTypeIdx);//文件mediatype

            StringBuilder sb = new StringBuilder();
            sb.append("fileName--------->" + fileName + "\n");
            sb.append("fileUri--------->" + fileUri + "\n");
            sb.append("fileSize--------->" + fileSize + "\n");
            sb.append("byteSize--------->" + byteSize + "\n");
            sb.append("fileLastModify--------->" + fileLastModify + "\n");
            sb.append("fileType--------->" + fileType + "\n");

            System.out.println(sb.toString());


            tv_info.setText(sb.toString());

            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.v("down", "STATUS_PAUSED");
                case DownloadManager.STATUS_PENDING:
                    Log.v("down", "STATUS_PENDING");
                case DownloadManager.STATUS_RUNNING:
                    // 正在下载，不做任何事情
                    Log.v("down", "STATUS_RUNNING");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    // 完成
                    Log.v("down", "下载完成");
                    break;
                case DownloadManager.STATUS_FAILED:
                    // 清除已下载的内容，重新下载
                    Log.v("down", "STATUS_FAILED");
                    downloadManager.remove(DL_ID);
                    break;
            }
        }
        c.close();//最后一定要关闭游标，否则容易造成内存泄露
    }
}
