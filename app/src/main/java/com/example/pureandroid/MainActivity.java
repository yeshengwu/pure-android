package com.example.pureandroid;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.TestB;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private IMyAidlInterface mMediaServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.test_textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, TestAidlServer.class);
                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
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
//                        Log.e(TAG,"sum 1 + 2 = "+sum);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        TestB dd = new TestB() {
        };
        AppCompatActivity compatActivity = new AppCompatActivity();
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
            Log.e(TAG, "service connected.ComponentName = " + name + " service = " + service+ " service.hashCode = "+service.hashCode());

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
                },0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    };

}