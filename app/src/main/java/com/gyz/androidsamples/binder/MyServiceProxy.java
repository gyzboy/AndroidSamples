package com.gyz.androidsamples.binder;

import android.os.IBinder;
import android.os.RemoteException;

import static com.gyz.androidsamples.binder.IMyService.DESCRIPTOR;
import static com.gyz.androidsamples.binder.IMyService.TRANSACTION_say;

/**
 * Created by guoyizhe on 2018/1/9.
 */

public class MyServiceProxy implements IMyService{
    private android.os.IBinder mRemote;  //代表BpBinder

    public MyServiceProxy(android.os.IBinder remote) {
        mRemote = remote;
    }

    public java.lang.String getInterfaceDescriptor() {
        return DESCRIPTOR;
    }

    /** 自定义的sayHello()方法 **/
    @Override
    public void sayHello(String str) throws RemoteException {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
            _data.writeInterfaceToken(DESCRIPTOR);
            _data.writeString(str);
            mRemote.transact(TRANSACTION_say, _data, _reply, 0);
            _reply.readException();
        } finally {
            _reply.recycle();
            _data.recycle();
        }
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}
