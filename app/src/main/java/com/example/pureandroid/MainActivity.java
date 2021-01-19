package com.example.pureandroid;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.TestB;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private IMyAidlInterface mMediaServer;
    private IBinder serviceBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.test_textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(MainActivity.this, TestAidlServer.class);
//                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
                Log.e("evan", "this = " + this);
            }
        });

        findViewById(R.id.test2_textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "clicked 2", Toast.LENGTH_SHORT).show();

                try {
                    if (mMediaServer != null) {
                        mMediaServer.basicTypes(1, 100L, false, 0.5f, 1d, "string");
//                        int sum = mMediaServer.add(1,2);
                        IBinder binder = mMediaServer.getBinder(serviceBinder);
//                        Log.e(TAG,"sum 1 + 2 = "+sum);
                        Log.e(TAG, "sum binder = " + binder);
                        PackageInfo packageInfo = new PackageInfo();
                        Log.e(TAG, "sum packageInfo = " + packageInfo.hashCode());
                        mMediaServer.setPackageInfo(packageInfo);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        TestB dd = new TestB() {
        };
        AppCompatActivity compatActivity = new AppCompatActivity();
        Log.e("evan", "context.classloader = " + Context.class.getClassLoader());
        Log.e("evan", "context.classloader parent = " + Context.class.getClassLoader().getParent());
        Log.e("evan", "getClassLoader = " + getClassLoader());
        Log.e("evan", "getClassLoader getParent = " + getClassLoader().getParent());

        List<String> testAddNull = new ArrayList<>();
        String a = null;
        testAddNull.add(a);
        Log.e("evan", "testAddNull "+testAddNull);
//        Handler
        Handler uiHandler = new Handler();
        Message message = Message.obtain(uiHandler, new Runnable() {
            @Override
            public void run() {
                Log.e("evan", "IN message run");
            }
        });
        uiHandler.sendMessage(message);
//        Looper.myQueue();
//        Looper.prepare();

        final HandlerThread handlerThread = new HandlerThread("name");
        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("before wait");
                    latch.await();
                    System.out.println("after wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("start");
                handlerThread.start();
                System.out.println("countDown");
                latch.countDown();
            }
        }).start();

        final HandlerThread handlerThread2 = new HandlerThread("name");
        final boolean[] test = {false};
        new Thread(new Runnable() {
            @Override
            public void run() {
                test[0] = true;
                handlerThread2.start();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (test[0] == false) {

                }
                boolean isAlive = handlerThread2.isAlive();
                System.out.println("isAlive = " + isAlive);
            }
        }).start();

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("test before AssertionError");
                throw new AssertionError("evan AssertionError");
//                throw new Error("evan Exception");
            }
        },"thread-throw-error").start();*/


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.e(TAG, "onDestroy");
        if (mMediaServer != null) {
            unbindService(mServiceConnection);
        }

    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "service disconnected. ComponentName = " + name);
            mMediaServer = null;
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
            mMediaServer = IMyAidlInterface.Stub.asInterface(service);
            Log.e(TAG, "service connected.mMediaServer = " + mMediaServer);
            IBinder aBinder = mMediaServer.asBinder();
            Log.e(TAG, "service connected.aBinder = " + aBinder);

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