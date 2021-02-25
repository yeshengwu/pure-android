package com.example.pureandroid;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 *  TODO 没配置 configration 屏幕旋转会重建 Activity.
 *  目前这里 Fragment 会调用 onDetach 后，接着调用 onAttach 周期倒 onDetach
 *  又调用 onAttach整个周期，不太理解。
 *
 */
public class TestActivity extends FragmentActivity {
    private static final String TAG = "ac_test";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        Log.e(TAG, "onCreate");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // replace add 区别：
//        fragmentTransaction.add(R.id.ff_container, TestFragment.create()).commit();
        fragmentTransaction.replace(R.id.ff_container, TestFragment.create()).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        Log.e(TAG, "onResume. getFragments size = "+getSupportFragmentManager().getFragments().size());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

}
