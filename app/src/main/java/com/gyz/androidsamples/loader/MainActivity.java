package com.gyz.androidsamples.loader;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gyz.androidsamples.R;

import org.w3c.dom.Text;


public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ContentResolver contentResolver;
    private MyObserver ob;
    private int count = 0;
    private TextView tv_content;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
//            Toast.makeText(MainActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
            tv_content.setText((String) msg.obj);
        }

        ;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_main);

        Button button = (Button) findViewById(R.id.button);
        tv_content = (TextView) findViewById(R.id.tv_content);
        contentResolver = MainActivity.this.getContentResolver();
        ob = new MyObserver(handler, MainActivity.this);
        getContentResolver().registerContentObserver(MyProvider.CONTENT_URI, true, ob);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                count++;
                ContentValues values = new ContentValues();
                values.put(MyProvider.EMAIL, "gyzboy@126.com");
                values.put(MyProvider.USERNAME, "gyzboy");
                values.put(MyProvider.DATE, "20160629 点击次数" + count);
                values.put(MyProvider.SEX, "man");
                contentResolver.insert(MyProvider.CONTENT_URI, values);
                contentResolver.notifyChange(MyProvider.CONTENT_URI, null);


            }
        });

        Button delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                contentResolver.delete(MyProvider.CONTENT_URI, null, null);
                contentResolver.notifyChange(MyProvider.CONTENT_URI, null);
            }
        });
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(ob);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(), MyProvider.CONTENT_URI, new String[]{MyProvider.EMAIL, MyProvider.USERNAME, MyProvider.DATE, MyProvider.SEX}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Toast.makeText(MainActivity.this, "加载完毕,数据数量为:" + String.valueOf(data.getCount()), Toast.LENGTH_SHORT).show();
        StringBuilder sb = new StringBuilder();
        while (data.moveToNext()){
            sb.append(data.getColumnIndex(MyProvider.EMAIL) + " " + data.getString(data.getColumnIndex(MyProvider.USERNAME)) + " "
                    + data.getString(data.getColumnIndex(MyProvider.DATE)) + " " + data.getString(data.getColumnIndex(MyProvider.SEX))+ "\n");
        }
        tv_content.setText(sb);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//        loader = new MyCursorLoader(getApplicationContext(), MyProvider.CONTENT_URI, new String[]{MyProvider.EMAIL, MyProvider.USERNAME, MyProvider.DATE, MyProvider.SEX}, null, null, null);
    }

}
