package com.example.pureandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

public class TestActivityWithConstructor extends Activity {
    private static final String TAG = "ac_with_constructor";
    private String name;

    public TestActivityWithConstructor(String name) {
        this.name = name;
        Log.d(TAG, "construct(String name) name = " + name);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_activity_constructor);
        Log.d(TAG, "onCreate name = " + name);
    }
}
