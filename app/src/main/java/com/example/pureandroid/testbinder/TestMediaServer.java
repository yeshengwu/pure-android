package com.example.pureandroid.testbinder;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.example.pureandroid.IOnReceiveMessageListener;
import com.example.pureandroid.message.Message;
import com.example.pureandroid.testaidl.RecoverReceiver;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 手动实现 Binder IPC https://www.jianshu.com/p/bdef9e3178c9#
 */
public class TestMediaServer extends Service {

    public static final String DESCRIPTOR = "DESCRIPTOR";
    private static final String TAG = "TestMediaServer";
    public static final int TRANSACTION_basicTypes = 11;


    private RemoteCallbackList<IOnReceiveMessageListener> onReceiveMessageListeners = new WfcRemoteCallbackList<>();
    private PackageInfo mCurPackageInfo;

    private class WfcRemoteCallbackList<E extends IInterface> extends RemoteCallbackList<E> {
        @Override
        public void onCallbackDied(E callback, Object cookie) {
            Log.e(TAG, "main process died");
            Intent intent = new Intent(TestMediaServer.this, RecoverReceiver.class);
            sendBroadcast(intent);
        }
    }

    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "onCreate,mBinder = " + mBinder);
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand. intent = " + intent + " flags = " + flags + " startId = " + startId);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind. intent = " + intent + " return binder = " + mBinder + " binder.hashCode = " + mBinder.hashCode() + " mBinder.getClass = " + mBinder.getClass());
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    private IBinder mBinder = new CustomStub();

    private class CustomStub extends android.os.Binder {

        public CustomStub() {
            super();
            Log.e(TAG, "CustomStub");
        }

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TRANSACTION_basicTypes:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    long _arg1;
                    _arg1 = data.readLong();
                    boolean _arg2;
                    _arg2 = (0 != data.readInt());
                    float _arg3;
                    _arg3 = data.readFloat();
                    double _arg4;
                    _arg4 = data.readDouble();
                    java.lang.String _arg5;
                    _arg5 = data.readString();
                    int _result = this.basicTypes(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
            }

            return super.onTransact(code, data, reply, flags);
        }


        public int basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.e(TAG, "basicTypes receive params : anInt = " + anInt + " aLong = " + aLong);

            handler.post(() -> {
                int receiverCount = onReceiveMessageListeners.beginBroadcast();
                IOnReceiveMessageListener listener;
                while (receiverCount > 0) {
                    receiverCount--;
                    listener = onReceiveMessageListeners.getBroadcastItem(receiverCount);
                    try {
                        List<Message> msgList = new ArrayList<>();
                        Message msg = new Message();
                        msg.messageId = anInt;
                        msgList.add(msg);
                        listener.onReceive(msgList, true);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                onReceiveMessageListeners.finishBroadcast();
            });
            return (int) (anInt + aLong);
        }

    }


}

