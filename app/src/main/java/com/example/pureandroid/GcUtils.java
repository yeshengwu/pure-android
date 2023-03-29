package com.example.pureandroid;

import android.os.SystemClock;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * copy form BinderInternal
 * 监控gc次数。
 */
public class GcUtils {
    private static final String TAG = "GcUtils";

    static WeakReference<GcWatcher> sGcWatcher
            = new WeakReference<GcWatcher>(new GcWatcher());
    static ArrayList<Runnable> sGcWatchers = new ArrayList<>();
    static Runnable[] sTmpWatchers = new Runnable[1];
    static long sLastGcTime;


    static final class GcWatcher {
        @Override
        protected void finalize() throws Throwable {
            handleGc();
            sLastGcTime = SystemClock.uptimeMillis();
            synchronized (sGcWatchers) {
                sTmpWatchers = sGcWatchers.toArray(sTmpWatchers);
            }
            for (int i = 0; i < sTmpWatchers.length; i++) {
                if (sTmpWatchers[i] != null) {
                    sTmpWatchers[i].run();
                }
            }
            sGcWatcher = new WeakReference<GcWatcher>(new GcWatcher());
        }


    }

    public static void addGcWatcher(Runnable watcher) {
        synchronized (sGcWatchers) {
            sGcWatchers.add(watcher);
        }
    }

    private static final void handleGc() {
        Log.e(TAG, "handleGc");
    }

}
