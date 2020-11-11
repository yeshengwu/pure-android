package com.example.mylibrary;

import android.os.RemoteException;

public class TestA extends IMyAidlInterface.Stub {

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }
}
