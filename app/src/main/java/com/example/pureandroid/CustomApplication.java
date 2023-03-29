package com.example.pureandroid;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.pureandroid.testaidl.ChatManager;
import com.example.pureandroid.testaidl.NotInitializedExecption;

import java.lang.reflect.Method;

public class CustomApplication extends Application {
    private static final String TAG = "CustomApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        Log.d(TAG, "CustomApplication attachBaseContext");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "CustomApplication onCreate");

        if (!tryToInitLeakcanary(this)) {
            return;
        }

        // 只在主进程初始化 单独的im进程
        if (AppUtils.getCurProcessName(this).equals(BuildConfig.APPLICATION_ID)) {
            initWFClient(this);
        }

        GcUtils.addGcWatcher(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "IN on gc");
            }
        });
    }

    /**
     * 初始化Leakcanary，需要在Application的super.onCreate之后马上调用，因为需要根据return决定后续代码是否继续执行
     *
     * @param application
     * @return true=后续代码继续执行，false＝后续代码不再继续执行
     */
    private boolean tryToInitLeakcanary(Application application) {
        if (BuildConfig.DEBUG) {
            try {
                Class LeakCanaryClazz = Class.forName("com.squareup.leakcanary.LeakCanary");

                Method isInAnalyzerProcessMethod = LeakCanaryClazz.getDeclaredMethod("isInAnalyzerProcess", Context.class);
                boolean isInAnalyzerProcess = (boolean) isInAnalyzerProcessMethod.invoke(null, application);
                if (isInAnalyzerProcess) {
                    // This process is dedicated to LeakCanary for heap analysis.
                    // You should not init your app in this process.
                    Log.w("evan", "isInAnalyzerProcess, return");
                    return false;
                }

                Method installMethod = LeakCanaryClazz.getDeclaredMethod("install", Application.class);
                installMethod.invoke(null, application);
                // Normal app init code...
            } catch (Exception e) {
                Log.w("evan", "Leakcanary init ERROR : " + e);
            }
            return true;
        } else {
            return true;
        }
    }


    private void initWFClient(Application application) {
        try {
            ChatManager.init(application, "Config.IM_SERVER_HOST");
        } catch (NotInitializedExecption notInitializedExecption) {
            notInitializedExecption.printStackTrace();
        }
    }

}
