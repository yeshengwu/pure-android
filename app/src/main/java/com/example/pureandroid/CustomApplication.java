package com.example.pureandroid;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class CustomApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        Log.d("evan","CustomApplication attachBaseContext");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("evan","CustomApplication onCreate");
    }

}
