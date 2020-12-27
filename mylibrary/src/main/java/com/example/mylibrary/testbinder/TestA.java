package com.example.mylibrary.testbinder;

import android.os.RemoteException;

import com.example.mylibrary.IMyAidlInterfaceEvan;

public class TestA extends IMyAidlInterfaceEvan.Stub {

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }
}
