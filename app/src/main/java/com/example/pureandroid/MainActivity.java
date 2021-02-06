package com.example.pureandroid;


import android.app.Activity;
import android.app.AppComponentFactory;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.StatFs;
import android.util.Log;
import android.util.LruCache;
import android.util.Printer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.TestB;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import androidx.appcompat.app.AppCompatActivity;

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

//                Intent intent = new Intent(MainActivity.this, TestLayoutAc.class);
//                startActivity(intent);
//                Intent intent = new Intent(MainActivity.this, HookCloseGuardActivity.class);
//                startActivity(intent);

                Intent intent = new Intent(MainActivity.this, TestActivityWithConstructor.class);
                startActivity(intent);
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
        Log.e("evan", "testAddNull " + testAddNull);
//        Handler

//        Looper.myLooper().setMessageLogging(new Printer() {
//            @Override
//            public void println(String x) {
//                Log.e("evan", "IN myLooper println(x) x = " + x);
//            }
//        });

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

        // LRU Cache 结论：java 链表 lru 。
        // 实现链表维护的LRU， 头部插入来维持LRU。 https://blog.csdn.net/weixin_43087333/article/details/105582535
        //插入数据：
        // 判断要插入的数据是否存在，如果存在，删除存在的元素，将该元素插入进头部
        //如果不存在，判断是否超出容量，如果超出容量，删除最后一个元素，将该元素插入头部
        // 获取数据：
        // 链表遍历查找，如果存在，删除存在的元素，将该元素插入进头部
        // 如果不存在返回空

        LruCache<String, String> lruCache = new LruCache<String, String>(2) {
            @Override
            protected void entryRemoved(boolean evicted, String key, String oldValue, String newValue) {
                System.out.println("entryRemoved. evicted = " + evicted + " key = " + key + " oldValue = " + oldValue + " newValue = " + newValue);
            }

//            @Override
//            protected String create(String key) {
//                System.out.println("create. key = " + key);
//                return ""+new Random().nextInt();
//            }

            @Override
            protected int sizeOf(String key, String value) {
                System.out.println("sizeOf. key = " + key + " value = " + value);
                return 1;
            }
        };

        String putA = lruCache.put("a", "a");
        System.out.println(" putA = " + putA);
        System.out.println(" putA.S = " + lruCache.snapshot());
        String putB = lruCache.put("b", "b");
        System.out.println("putB = " + putB);
        System.out.println(" putB.S = " + lruCache.snapshot());
        String putC = lruCache.put("c", "c");
        System.out.println("putC = " + putC);
        System.out.println(" putC.S = " + lruCache.snapshot());
        String getA = lruCache.get("a");
        String getX = lruCache.get("x");
        String getC = lruCache.get("c");
        System.out.println(" getA = " + getA + " getX = " + getX + " getC = " + getC);
        System.out.println(" get after.S = " + lruCache.snapshot());
        System.out.println(" get after.S2 = " + lruCache.snapshot());

        // /data/user/0/com.example.pureandroid
        System.out.println(" dataDir = " + getApplicationInfo().dataDir);

        // 结论： app 没有权限操作  /data/dalvik-cache 
        String fileName = "/data/dalvik-cache/test.txt";
        try {
            //使用这个构造函数时，如果存在kuka.txt文件，
            //则先把这个文件给删除掉，然后创建新的kuka.txt
            FileWriter writer = new FileWriter(fileName);
            writer.write("Hello Kuka:\n");
            writer.write("  My name is coolszy!\n");
            writer.write("  I like you and miss you。");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        File file = new File("/data/dalvik-cache");
//        System.out.println(" file = " + file.canRead());

//        RecyclerView
        System.out.println(" dataDir = " + getApplicationInfo().dataDir);
       int mStatFrsize = new StatFs("/data").getBlockSize();
        System.out.println(" mStat.f_frsize = " + mStatFrsize);

        // 测试 log 不同 tag 在 logcat 中显示问题
        Log.e("evan", "tag is evan");
        Log.e("mark", "tag is mark");
        Log.e("evan", "downloadModule:tag is new format");

//        AppComponentFactory
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