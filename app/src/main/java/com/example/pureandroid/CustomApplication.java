package com.example.pureandroid;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;

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

        if (!tryToInitLeakcanary(this)) {
            return;
        }
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
                    Log.w("evan","isInAnalyzerProcess, return");
                    return false;
                }

                Method installMethod = LeakCanaryClazz.getDeclaredMethod("install", Application.class);
                installMethod.invoke(null, application);
                // Normal app init code...
            } catch (Exception e) {
                Log.w("evan","Leakcanary init ERROR : " + e);
            }
            return true;
        } else {
            return true;
        }
    }


}
