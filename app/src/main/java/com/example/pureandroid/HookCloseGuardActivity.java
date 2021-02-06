package com.example.pureandroid;

import android.app.Activity;
import android.database.CursorWindow;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Android Hook 一 Hook CloseGuard
 * https://blog.csdn.net/rambomatrix/article/details/85715989
 *
 * 使用：等待gc后会触发hook的回调，或者开启 as 的 profile, 手动触发gc，回到首页一样会触发hook的回调
 */
public class HookCloseGuardActivity extends Activity {
    private static final String TAG = "HookCloseGuardActivity";
    private volatile static Object sOriginalReporter;

    FileInputStream a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_closeguard);
        setTitle("HookCloseGuard");
    }

    private void testAllocate() {
//        CursorWindow cursorWindow = new CursorWindow("test");
        try {
           a = new FileInputStream("/sdcard/block1868");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void allocate(View view) {
        testAllocate();
    }

    public void hook(View view) {
        tryHook();
    }

    private boolean tryHook() {
        try {
            Class<?> closeGuardCls = Class.forName("dalvik.system.CloseGuard");
            Class<?> closeGuardReporterCls = Class.forName("dalvik.system.CloseGuard$Reporter");
            Field fieldREPORTER = closeGuardCls.getDeclaredField("REPORTER");
            Field fieldENABLED = closeGuardCls.getDeclaredField("ENABLED");
            fieldREPORTER.setAccessible(true);
            fieldENABLED.setAccessible(true);
            sOriginalReporter = fieldREPORTER.get(null);
            fieldENABLED.set(null, true);
            ClassLoader classLoader = closeGuardReporterCls.getClassLoader();
            if (classLoader == null) {
                return false;
            }
            fieldREPORTER.set(null, Proxy.newProxyInstance(classLoader,
                    new Class<?>[]{closeGuardReporterCls},
                    new IOCloseLeakDetector(sOriginalReporter)));
            fieldREPORTER.setAccessible(false);
            return true;
        } catch (Throwable e) {
            Log.e(TAG, "tryHook exp=%s", e);
        }

        return false;
    }

    class IOCloseLeakDetector implements InvocationHandler {
        private final Object originalReporter;

        public IOCloseLeakDetector(Object originalReporter) {
            this.originalReporter = originalReporter;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("report")) {
                Log.d(TAG, "invoke hook method");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HookCloseGuardActivity.this, "invoke hook method", Toast.LENGTH_LONG).show();
                    }
                });
                return null;
            }
            return method.invoke(originalReporter, args);
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
