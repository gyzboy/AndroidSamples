package com.gyz.androidsamples.binder;

import android.os.IInterface;
import android.os.RemoteException;

/**
 * Created by guoyizhe on 2018/1/9.
 */

public interface IMyService extends IInterface {
    static final java.lang.String DESCRIPTOR = "com.gyz.androidsamples.binder.MyService";
    public void sayHello(String str) throws RemoteException;
    static final int TRANSACTION_say = android.os.IBinder.FIRST_CALL_TRANSACTION;
}
