package com.gyz.androidsamples.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoyizhe on 2016/12/21.
 * 邮箱:gyzboy@126.com
 */

public class ASAidl extends Activity {

//    IASAidlInterface aidlService;
    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //客户端
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
//                aidlService = IASAidlInterface.Stub.asInterface(service);
//                try {
//                    books = aidlService.getBooks();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }
}
