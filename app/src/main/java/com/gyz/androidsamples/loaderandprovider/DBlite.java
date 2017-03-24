package com.gyz.androidsamples.loaderandprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBlite extends SQLiteOpenHelper {


	public DBlite(Context context) {
		super(context, MyProvider.DBNAME, null, MyProvider.VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table "+MyProvider.TNAME+"(" +
				MyProvider.TID+" integer primary key autoincrement not null,"+
				MyProvider.EMAIL+" text not null," +
				MyProvider.USERNAME+" text not null," +
				MyProvider.DATE+" integer not null,"+
				MyProvider.SEX+" text not null);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		switch(newVersion){
		case 1:
			
			break;
		case 2://为2的时候测试升级
			db.execSQL("DROP TABLE IF EXISTS " + MyProvider.TNAME);
			onCreate(db);
			break;
		}
	}
}
