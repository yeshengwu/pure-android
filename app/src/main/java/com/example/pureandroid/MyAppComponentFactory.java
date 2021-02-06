package com.example.pureandroid;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.P)
public class MyAppComponentFactory extends AppComponentFactory {
    private static final String TAG = "MyAppComponentFactory";

    @NonNull
    @Override
    public Activity instantiateActivity(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Log.d(TAG, "instantiateActivity. cl = " + cl);
        Log.d(TAG, "instantiateActivity. className = " + className);
        Log.d(TAG, "instantiateActivity. intent = " + intent);

        /*// class name replace
        if (className.contains(".inject")) {
            className = className.replace(".inject","");
        }*/

        Class<?> activityClass = cl.loadClass(className);
        if (activityClass.equals(TestActivityWithConstructor.class)) {
            return new TestActivityWithConstructor("This is injected");
        } else {
            return super.instantiateActivity(cl, className, intent);
        }
    }
}
