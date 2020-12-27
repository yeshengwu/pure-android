// IMyAidlInterface.aidl
package com.example.pureandroid;

// Declare any non-default types here with import statements
import android.content.pm.ActivityInfo;
//import com.example.pureandroid.IApplicationCallback;

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes( int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

//    int add(int aInt,int bInt);
// boolean unregisterApplicationCallback(in IApplicationCallback callback);

//List<ActivityInfo> getReceivers(in String packageName ,int flags);
}
