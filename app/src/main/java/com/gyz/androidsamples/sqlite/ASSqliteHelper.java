package com.gyz.androidsamples.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guoyizhe on 2017/3/1.
 * 邮箱:gyzboy@126.com
 */

public class ASSqliteHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static final String DB_NAME = "as_sql.db";
    private static final int DB_VERSION = 1;
    private static final String createTableSql = "CREATE TABLE IF NOT EXISTS person " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age INTEGER, info TEXT)";

    public ASSqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    public ASSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableSql);
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS tb_assql");
//        db.execSQL(createTableSql);
        db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
    }
}
