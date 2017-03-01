package com.gyz.androidsamples.sqlite;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoyizhe on 2017/3/1.
 * 邮箱:gyzboy@126.com
 */

public class ASSqlite extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{

    private ASDBManager dbManager;
    private String str;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        tv = new TextView(this);

        Button button1 = new Button(this);
        button1.setText("add person");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
                show(tv);
            }
        });

        Button button2 = new Button(this);
        button2.setText("update person");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                show(tv);
            }
        });

        Button button3 = new Button(this);
        button3.setText("delete person");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
                show(tv);
            }
        });


        ll.addView(button1);
        ll.addView(button2);
        ll.addView(button3);
        ll.addView(tv);

        setContentView(ll);

        dbManager = new ASDBManager(this);

        getLoaderManager().initLoader(0,null,this);
    }

    private void show(TextView textView){
//        textView.setText(query());
        textView.setText(queryTheCursor());
    }

    public void add() {
        ArrayList<Person> persons = new ArrayList<Person>();

        Person person1 = new Person("Ella", 22, "lively girl");
        Person person2 = new Person("Jenny", 22, "beautiful girl");
        Person person3 = new Person("Jessica", 23, "sexy girl");
        Person person4 = new Person("Kelly", 23, "hot baby");
        Person person5 = new Person("Jane", 25, "a pretty woman");

        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        persons.add(person4);
        persons.add(person5);

        dbManager.add(persons);
    }

    public void update() {
        Person person = new Person();
        person.name = "Jane";
        person.age = 30;
        dbManager.updateAge(person);
    }

    public void delete() {
        Person person = new Person();
        person.age = 30;
        dbManager.deleteOldPerson(person);
    }

    public String query() {
        List<Person> persons = dbManager.query();
        StringBuilder sb = new StringBuilder();
        for (Person person : persons) {
            sb.append("name:" + person.name + " age:" + person.age + " info:" + person.info + "\n");
        }
        return sb.toString();

    }

    public String queryTheCursor() {
        Cursor c = dbManager.queryTheCursor();
        startManagingCursor(c); //托付给activity根据自己的生命周期去管理Cursor的生命周期
//        CursorWrapper cursorWrapper = new CursorWrapper(c) {
//            @Override
//            public String getString(int columnIndex) {
//                //将简介前加上年龄
//                if (getColumnName(columnIndex).equals("info")) {
//                    int age = getInt(getColumnIndex("age"));
//                    return age + " years old, " + super.getString(columnIndex);
//                }
//                return super.getString(columnIndex);
//            }
//        };
        StringBuilder sb = new StringBuilder();
        while (c.moveToNext()) {
            sb.append("name:" + c.getString(c.getColumnIndex("name")) + " age:" + c.getInt(c.getColumnIndex("age")) + " info:" + c.getString(c.getColumnIndex("info")) + "\n");
        }
        return sb.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDB();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this,
                Uri.parse("content://chat_order"), null, "name = ? and age = ?", new String[]{"Ella","22"}, "age asc") {
            @Override
            public Cursor loadInBackground() {
                Cursor cursor = super.loadInBackground();
                if (cursor == null) {
                    return null;
                }
                CallLogsCursor callLogsCursor = new CallLogsCursor(cursor);
                return callLogsCursor;
            }
        };
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public class CallLogsCursor extends CursorWrapper {

        public CallLogsCursor(Cursor cursor) {
            super(cursor);

            int nameIndex = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
            int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);

            // 从游标的最后索引往前查询,因为最新的通话记录在最后
            for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor
                    .moveToPrevious()) {
                // 姓名
                String name = cursor.getString(nameIndex);
                // 号码
                String number = cursor.getString(numberIndex);
                // 类型
                int type = cursor.getInt(typeIndex);
                // 日期
                long date = cursor.getLong(dateIndex);
                // 通话时长
                int duration = cursor.getInt(durationIndex);

            }
        }
    }
}
