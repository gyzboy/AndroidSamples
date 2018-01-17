package com.gyz.androidsamples.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Created by guoyizhe on 2018/1/9.
 */

public class MyService extends Binder implements IMyService {

    public MyService() {
        attachInterface(this, DESCRIPTOR);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    /**
     * 将MyService转换为IMyService接口
     **/
    public static IMyService asInterface(android.os.IBinder obj) {
        if ((obj == null)) {
            return null;
        }
        android.os.IInterface iInterface = obj.queryLocalInterface(DESCRIPTOR);
        if (((iInterface != null) && (iInterface instanceof IMyService))) {
            return ((IMyService) iInterface);
        }
        return null;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACTION_say:
                data.enforceInterface(DESCRIPTOR);
                String str = data.readString();
                sayHello(str);
                reply.writeNoException();
                return true;
            default:
        }
        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public void sayHello(String str) throws RemoteException {
        System.out.println("MyService:: Hello, " + str);
    }
}
