package com.example.pureandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * 测试
 * 同一个进程 bindService
 * 不同进程 bindService
 * ServiceConnection  binder 变化 和  生成的 Aidl 接口类 的 端点调试。
 */
public class TestAidlServer extends Service {
    private static final String TAG = "TestAidlServer";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG,"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"onStartCommand. intent = "+intent + " flags = "+flags + " startId = "+startId);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind. intent = "+intent+ " return binder = "+mBinder+ " binder.hashCode = "+mBinder.hashCode());
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG,"onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }

    private final IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {

        @Override
        public int basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.e(TAG,"basicTypes receive params : anInt = "+anInt+" aLong = "+aLong);
            return anInt;
        }
    };

}
