package com.gyz.androidsamples.loader;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;


public class MyProvider extends ContentProvider {

	public static final String DBNAME = "myprovidertest.db";
	public static final String TNAME = "myprovidertest";
	public static final int VERSION = 1;

	public static String TID = "tid";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";
	public static final String DATE = "date";
	public static final String SEX = "sex";

	public static final String AUTOHORITY = "com.gyz.androidsamples";
	public static final int ITEM = 1;
	public static final int ITEM_ID = 2;

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/myprovidertest";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/myprovidertest";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTOHORITY + "/myprovidertest");

	private static final UriMatcher sMatcher;

	DBlite dBlite;
	SQLiteDatabase db;
	static {
		sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sMatcher.addURI(MyProvider.AUTOHORITY, MyProvider.TNAME, MyProvider.ITEM);
		sMatcher.addURI(MyProvider.AUTOHORITY, MyProvider.TNAME + "/#", MyProvider.ITEM_ID);

	}

	@Override
	public boolean onCreate() {
		this.dBlite = new DBlite(this.getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		db = dBlite.getWritableDatabase();                
        Cursor c;
        switch (sMatcher.match(uri)) {
        case ITEM:
                c = db.query(TNAME, projection, selection, selectionArgs, null, null, null);
                break;
        case ITEM_ID:
//                String id = uri.getPathSegments().get(1);
                String id = uri.getLastPathSegment();
                c = db.query(TNAME, projection, TID+"="+id+(!TextUtils.isEmpty(selection)?"AND("+selection+')':""),selectionArgs, null, null, sortOrder);
            break;
        default:
                throw new IllegalArgumentException("Unknown URI"+uri);
        }
//        c.setNotificationUri(getContext().getContentResolver(), uri);
//        getContext().getContentResolver().notifyChange(uri, null);
        return c;
	}

	@Override
	public String getType(Uri uri) {
		switch (sMatcher.match(uri)) {
        case ITEM:
                return CONTENT_TYPE;
        case ITEM_ID:
            return CONTENT_ITEM_TYPE;
        default:
                throw new IllegalArgumentException("Unknown URI"+uri);
        }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
        db = dBlite.getWritableDatabase();
        long rowId;
        if(sMatcher.match(uri)!=ITEM){
                throw new IllegalArgumentException("Unknown URI"+uri);
        }
        rowId = db.insert(TNAME,TID,values);
           if(rowId>0){
                   Uri noteUri=ContentUris.withAppendedId(CONTENT_URI, rowId);
                   getContext().getContentResolver().notifyChange(noteUri, null);
                   return noteUri;
           }
           throw new IllegalArgumentException("Unknown URI"+uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		db = dBlite.getWritableDatabase();  
        int match = sMatcher.match(uri);  
        switch (match) {  
        case ITEM:  
            //doesn't need any code in my provider.  
            break;  
        case ITEM_ID:  
            long _id = ContentUris.parseId(uri);  
            selection = "id = ?";  
            selectionArgs = new String[]{String.valueOf(_id)};  
        }  
        int count = db.delete(TNAME, selection, selectionArgs);  
        if (count > 0) {  
        	getContext().getContentResolver().notifyChange(uri, null);
        }  
        return count;  
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		db = dBlite.getWritableDatabase();  
        int match = sMatcher.match(uri);  
        switch (match) {  
        case ITEM:  
            //doesn't need any code in my provider.  
            break;  
        case ITEM_ID:  
            long _id = ContentUris.parseId(uri);
            selection = "id = ?";  
            selectionArgs = new String[]{String.valueOf(_id)};  
            break;  
        default:  
            throw new IllegalArgumentException("Unknown URI: " + uri);  
        }  
        int count = db.update(TNAME, values, selection, selectionArgs);  
        if (count > 0) {  
        	getContext().getContentResolver().notifyChange(uri, null);
        }  
        return count;  
	}
	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		int numValues = 0;
		db = dBlite.getWritableDatabase(); 
		db.beginTransaction();
		try {
		   numValues = values.length;
		   for (int i = 0; i < numValues; i++) {
		       insert(uri, values[i]);
		   }
		   db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			db.endTransaction();
			  getContext().getContentResolver().notifyChange(uri, null);
		}
		return numValues;
	}
}
