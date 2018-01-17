package com.gyz.androidsamples.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoyizhe on 2016/12/21.
 * 邮箱:gyzboy@126.com
 */

/**
 * AIDL服务端
 */
public class ASAidlService extends Service {

    public final String TAG = this.getClass().getSimpleName();

    //包含Book对象的list
    private List<Book> mBooks = new ArrayList<>();

    //由AIDL文件生成的BookManager
//    private final IASAidlInterface.Stub mBookManager = new IASAidlInterface.Stub() {
//        @Override
//        public List<Book> getBooks() throws RemoteException {
//            synchronized (this) {
//                Log.e(TAG, "invoking getBooks() method , now the list is : " + mBooks.toString());
//                if (mBooks != null) {
//                    return mBooks;
//                }
//                return new ArrayList<>();
//            }
//        }
//
//
//        @Override
//        public void addBook(Book book) throws RemoteException {
//            synchronized (this) {
//                if (mBooks == null) {
//                    mBooks = new ArrayList<>();
//                }
//                if (book == null) {
//                    Log.e(TAG, "Book is null in In");
//                    book = new Book();
//                }
//                //尝试修改book的参数，主要是为了观察其到客户端的反馈
//                book.setPrice(2333);
//                if (!mBooks.contains(book)) {
//                    mBooks.add(book);
//                }
//                //打印mBooks列表，观察客户端传过来的值
//                Log.e(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
//            }
//        }
//    };

    @Override
    public void onCreate() {
        super.onCreate();
        Book book = new Book();
        book.setName("AIDLtest");
        book.setPrice(28);
        mBooks.add(book);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(getClass().getSimpleName(), String.format("on bind,intent = %s", intent.toString()));
//        return mBookManager;
        return null;
    }
}
