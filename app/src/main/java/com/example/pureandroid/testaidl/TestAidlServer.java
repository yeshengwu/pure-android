package com.example.pureandroid.testaidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.example.pureandroid.IMyAidlInterface;
import com.example.pureandroid.IOnReceiveMessageListener;
import com.example.pureandroid.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试
 * 同一个进程 bindService
 * 不同进程 bindService
 * ServiceConnection  binder 变化 和  生成的 Aidl 接口类 的 端点调试。
 */
public class TestAidlServer extends Service {
    private static final String TAG = "TestAidlServer";

    private Handler handler;

    private RemoteCallbackList<IOnReceiveMessageListener> onReceiveMessageListeners = new WfcRemoteCallbackList<>();
    private PackageInfo mCurPackageInfo;

    private class WfcRemoteCallbackList<E extends IInterface> extends RemoteCallbackList<E> {
        @Override
        public void onCallbackDied(E callback, Object cookie) {
            Log.e(TAG, "main process died");
            Intent intent = new Intent(TestAidlServer.this, RecoverReceiver.class);
            sendBroadcast(intent);
        }
    }

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
        if (mBinder == null) {
            mBinder = new CustomStub();
        }
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

//    private final IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
//
//        @Override
//        public int basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
//            Log.e(TAG, "basicTypes receive params : anInt = " + anInt + " aLong = " + aLong);
//
//            handler.post(() -> {
//                int receiverCount = onReceiveMessageListeners.beginBroadcast();
//                IOnReceiveMessageListener listener;
//                while (receiverCount > 0) {
//                    receiverCount--;
//                    listener = onReceiveMessageListeners.getBroadcastItem(receiverCount);
//                    try {
//                        List<com.example.pureandroid.message.Message> msgList = new ArrayList<>();
//                        Message msg = new Message();
//                        msg.messageId = anInt;
//                        msgList.add(msg);
//                        listener.onReceive(msgList, true);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                }
//                onReceiveMessageListeners.finishBroadcast();
//            });
//            return anInt;
//        }
//
//        @Override
//        public IBinder getBinder(IBinder binder) throws RemoteException {
//            Log.e(TAG, "basicTypes binder = " + binder);
//            return binder;
//        }
//
//        @Override
//        public void setPackageInfo(PackageInfo packageInfo) throws RemoteException {
//            // 收到的 packageInfo 跟客户端发来的不是同一个，跨进程传输 parcelble 序列化 后已经是新的对象。
//            Log.e(TAG, "basicTypes receive packageInfo = " + packageInfo);
//            mCurPackageInfo = packageInfo;
//        }
//
//        @Override
//        public void setOnReceiveMessageListener(com.example.pureandroid.IOnReceiveMessageListener listener) throws RemoteException {
//            onReceiveMessageListeners.register(listener);
//        }
//
//        @Override
//        public List<com.example.pureandroid.message.Message> getMessages(long fromIndex) throws RemoteException {
//            List<com.example.pureandroid.message.Message> msgList = new ArrayList<>();
//            Message msg = new Message();
//            msg.messageId = fromIndex;
//            msgList.add(msg);
//            return msgList;
//        }
//    };

//    private final IMyAidlInterface.Stub mBinder = new CustomStub();
    private  IMyAidlInterface.Stub mBinder;

    private class CustomStub extends IMyAidlInterface.Stub {

        public CustomStub() {
            super();
            Log.e(TAG, "CustomStub");
        }

        @Override
        public int basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.e(TAG, "basicTypes receive params : anInt = " + anInt + " aLong = " + aLong);

            handler.post(() -> {
                int receiverCount = onReceiveMessageListeners.beginBroadcast();
                IOnReceiveMessageListener listener;
                while (receiverCount > 0) {
                    receiverCount--;
                    listener = onReceiveMessageListeners.getBroadcastItem(receiverCount);
                    try {
                        List<com.example.pureandroid.message.Message> msgList = new ArrayList<>();
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
            return anInt;
        }

        @Override
        public IBinder getBinder(IBinder binder) throws RemoteException {
            Log.e(TAG, "basicTypes binder = " + binder);
            return binder;
        }

        @Override
        public void setPackageInfo(PackageInfo packageInfo) throws RemoteException {
            // 收到的 packageInfo 跟客户端发来的不是同一个，跨进程传输 parcelble 序列化 后已经是新的对象。
            Log.e(TAG, "basicTypes receive packageInfo = " + packageInfo);
            mCurPackageInfo = packageInfo;
        }

        @Override
        public void setOnReceiveMessageListener(com.example.pureandroid.IOnReceiveMessageListener listener) throws RemoteException {
            onReceiveMessageListeners.register(listener);
        }

        @Override
        public List<com.example.pureandroid.message.Message> getMessages(long fromIndex) throws RemoteException {
            List<com.example.pureandroid.message.Message> msgList = new ArrayList<>();
            Message msg = new Message();
            msg.messageId = fromIndex;
            msgList.add(msg);
            return msgList;
        }
    }

}
