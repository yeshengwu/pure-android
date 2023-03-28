// IMyAidlInterface.aidl
package com.example.pureandroid;

// Declare any non-default types here with import statements
import android.content.pm.ActivityInfo;
import com.example.pureandroid.IOnReceiveMessageListener;
import android.os.IBinder;
import android.content.pm.PackageInfo;

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int basicTypes( int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

   IBinder getBinder(IBinder binder);
   void setPackageInfo(in PackageInfo packageInfo);
//    int add(int aInt,int bInt);
    List<com.example.pureandroid.message.Message> getMessages(in long fromIndex);

    oneway void setOnReceiveMessageListener(in IOnReceiveMessageListener listener);
}
