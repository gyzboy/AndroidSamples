package com.gyz.androidsamples.loaderandprovider;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;

public class MyObserver extends ContentObserver {
	
	private Handler mHandler;
	private Context mContext;
	private StringBuilder msg = new StringBuilder();

	public MyObserver(Handler handler,Context context) {
		super(handler);
		this.mHandler = handler;
		this.mContext = context;
	}
	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		msg.setLength(0);
		Cursor cursor = mContext.getContentResolver().query(MyProvider.CONTENT_URI, new String[] { MyProvider.EMAIL, MyProvider.USERNAME, MyProvider.DATE, MyProvider.SEX }, null, null, null);
		while (cursor.moveToNext()) {
			msg.append(cursor.getString(cursor.getColumnIndex(MyProvider.EMAIL)) + " " + cursor.getString(cursor.getColumnIndex(MyProvider.USERNAME)) + " "
					+ cursor.getString(cursor.getColumnIndex(MyProvider.DATE)) + " " + cursor.getString(cursor.getColumnIndex(MyProvider.SEX)));
		}
		mHandler.obtainMessage(0,msg.toString()).sendToTarget();
		cursor.close();
	}

}
