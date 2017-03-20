package com.gyz.androidsamples.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by guoyizhe on 15/9/23.
 */
public class MyCursorLoader extends CursorLoader {

    Cursor mCursor;

    public MyCursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }


    @Override
    public void onContentChanged() {
        if (!mCursor.isClosed()) {
            deliverResult(mCursor);
        }
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        mCursor = super.loadInBackground();
        return mCursor;
    }

}
