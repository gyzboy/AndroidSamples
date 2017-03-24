package com.gyz.androidsamples.loaderandprovider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.EditText;

/**
 * SMS监听 使用的时候直接getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, content);
 */
public class SmsContent extends ContentObserver {

	public static final String SMS_URI_INBOX = "content://sms/inbox";
	private Activity activity = null;
	private String smsContent = "";
	private EditText verifyText = null;
	private Handler handler;
	private int SMS_CONTENT = 1;

	public SmsContent(Activity activity, Handler handler, EditText verifyText) {
		super(handler);
		this.activity = activity;
		this.verifyText = verifyText;
		this.handler = handler;
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		Cursor cursor = null;
		// 读取收件箱中指定号码的未读短信,一般只监听自己公司使用的短信通道号码，如果有多个，查询语句改为 address in (xxx，xxx)就可以了
		cursor = activity.getContentResolver().query(Uri.parse(SMS_URI_INBOX), new String[] { "_id", "address", "body", "read" }, "address =�� and read=?",
				new String[] { "5554" ,"0" }, "date desc");
		if (cursor != null) {//有未读短信
			cursor.moveToFirst();
			if (cursor.moveToFirst()) {//这里只针对一条,多条的话使用while循环遍历
				String smsbody = cursor.getString(cursor.getColumnIndex("body"));
				String regEx = "[^0-9]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(smsbody.toString());
				smsContent = m.replaceAll("").trim().toString();
				verifyText.setText(smsContent);
			}
			handler.obtainMessage(SMS_CONTENT, verifyText.getText().toString()).sendToTarget();
			cursor.close();
		}
	}
}

