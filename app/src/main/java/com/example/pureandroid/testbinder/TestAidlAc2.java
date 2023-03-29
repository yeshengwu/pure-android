package com.example.pureandroid.testbinder;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.example.pureandroid.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public class TestAidlAc2 extends FragmentActivity {
    private static final String TAG = "TestAidlAc2";

    private IBinder mClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_aidl);
        findViewById(R.id.test_tv_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                int _result;
                try {
                    _data.writeInterfaceToken(TestMediaServer.DESCRIPTOR);
                    _data.writeInt(1);
                    _data.writeLong(11);
                    _data.writeInt(1);
                    _data.writeFloat(1.0f);
                    _data.writeDouble(22);
                    _data.writeString("aString");
                    boolean _status = mClient.transact(TestMediaServer.TRANSACTION_basicTypes, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readInt();
                    Log.e(TAG, "basicTypes transact result =  "+_result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }

            }
        });

        findViewById(R.id.test_rv_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Intent intent = new Intent(this, TestMediaServer.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "service disconnected. ComponentName = " + name);
            mClient = null;
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.e(TAG, "service disconnected. ComponentName = " + name);
        }

        @Override
        public void onNullBinding(ComponentName name) {
            Log.e(TAG, "service onNullBinding. ComponentName = " + name);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "service connected.ComponentName = " + name + " service = " + service);
            mClient = service;

            Log.e(TAG, "service connected.mMediaServer = " + mClient);

            // 跨进程日志:
            // service connected.ComponentName = ComponentInfo{com.example.pureandroid/com.example.pureandroid.testaidl.TestAidlServer}
            // service = android.os.BinderProxy@627fea6
            // service connected.mMediaServer = com.example.pureandroid.IMyAidlInterface$Stub$Proxy@90230e7

            // 同进程日志：
            // service connected.ComponentName = ComponentInfo{com.example.pureandroid/com.example.pureandroid.testaidl.TestAidlServer}
            // service = com.example.pureandroid.testaidl.TestAidlServer$1@90230e7
            // service connected.mMediaServer = com.example.pureandroid.testaidl.TestAidlServer$1@90230e7
            // TestAidlServer 那边返回相同：90230e7
            // E/TestAidlServer: onBind. intent = Intent { cmp=com.example.pureandroid/.TestAidlServer }
            // return binder = com.example.pureandroid.testaidl.TestAidlServer$1@90230e7

//            service.pingBinder()
//            service.isBinderAlive();


            try {
                service.linkToDeath(new IBinder.DeathRecipient() {
                    @Override
                    public void binderDied() {
                        Log.e(TAG, "service connected. binderDied");
                    }
                }, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    };

}
