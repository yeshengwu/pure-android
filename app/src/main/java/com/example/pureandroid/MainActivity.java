package com.example.pureandroid;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.TestB;
import com.example.mysecondlib.ITestC;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private IMyAidlInterface mMediaServer;
    private IBinder serviceBinder;
    private Handler mHandler;

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

//                Intent intent = new Intent(MainActivity.this, TestLayoutAc.class);
//                startActivity(intent);
//                Intent intent = new Intent(MainActivity.this, HookCloseGuardActivity.class);
//                startActivity(intent);

//                Intent intent2 = new Intent(MainActivity.this, TestActivity.class);
//                startActivity(intent2);

//                Intent intent3 = new Intent(MainActivity.this, TestLiveDataAc.class);
//                startActivity(intent3);

                Intent intent4 = new Intent(MainActivity.this, TestHandlerAc.class);
                startActivity(intent4);

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

        // 测试 module 间依赖: gradle api implementation 区别。
        // app implementation 方式 ->b  b api方式 -> c. 这样 app 主模块能访问c
        // b implementation 方式  -> c, app 主模块 无法访问c
        // TestB 是 b 模块
        TestB dd = new TestB() {
        };
        AppCompatActivity compatActivity = new AppCompatActivity();
        // ITestC 是 c 模块
        ITestC testC = new ITestC(){
            @Override
            public void process(String args) {

            }
        };

        Log.e("evan", "context.classloader = " + Context.class.getClassLoader());
        Log.e("evan", "context.classloader parent = " + Context.class.getClassLoader().getParent());
        Log.e("evan", "getClassLoader = " + getClassLoader());
        Log.e("evan", "getClassLoader getParent = " + getClassLoader().getParent());

        List<String> testAddNull = new ArrayList<>();
        String a = null;
        testAddNull.add(a);
        Log.e("evan", "testAddNull " + testAddNull);

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

        // 2021-3-4 evan: 头插法，首先 画个 lru 数字测试表哈，经常画的  1 2 3 4 2 1 5 2 3 1 栈大小为3
        // 那么头插法实现，就是  1 再就2 插入，结果 2->1, 3插入后 3->2->1 4插入发现满了 删除最后一个元素3，然后将4插入头部
        // 就是 4->3->2  依次类推，关键就是头插法，后插入的做为头就明白了。

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
        System.out.println(" getA = " + getA);

        String getC = lruCache.get("c");
        System.out.println(" getC = " + getC);
        System.out.println(" get after.S = " + lruCache.snapshot());

        // LinkedHashMap lru
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);
        linkedHashMap.put("1", "a");
        linkedHashMap.put("2", "b");
        linkedHashMap.put("3", "c");
        System.out.println("put all after map  = " + linkedHashMap); //  {1=a, 2=b, 3=c}
        String get1 = linkedHashMap.get("1");
        System.out.println("get1 = " + get1);
        // 当 accessOrder的值为true， 1节点跑到链表末尾了。
        System.out.println("get after map  = " + linkedHashMap); // {2=b, 3=c, 1=a}

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

        Bitmap mBitmap = BitmapFactory.decodeFile("");
        Log.e("evan", "decodeFile return mBitmap = " + mBitmap);

//        AppComponentFactory
//        createPackageContext()
        // AsyncTask 子线程中调用此构造器方法,28版本 可行。因为AsyncTask源码内部 Handler 做了判断。
        // 分析文章： https://mp.weixin.qq.com/s/5CeZ6NHF6dm3qN6RgzaGDQ
        new Thread(new Runnable() {
            @Override
            public void run() {
                AsyncTask<String,Integer,String> asyncTask = new AsyncTask<String, Integer, String>() {
                    @Override
                    protected void onPreExecute() {
                        Log.e("evan","asyncTask onPreExecute. isMainUI = "+(Looper.myLooper() == Looper.getMainLooper()));
                    }

                    @Override
                    protected String doInBackground(String... strings) {
                        Log.e("evan","asyncTask doInBackground isMainUI = "+(Looper.myLooper() == Looper.getMainLooper()));
                        return "result";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        Log.e("evan","asyncTask onPostExecute isMainUI = "+(Looper.myLooper() == Looper.getMainLooper()));
                    }
                };
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }).start();

        // Handler 处理分发消息代码A
        /*public void dispatchMessage(Message msg) {
            if (msg.callback != null) {
                handleCallback(msg);
            } else {
                if (mCallback != null) {
                    if (mCallback.handleMessage(msg)) {
                        return;
                    }
                }
                handleMessage(msg);
            }
        }*/

        //        Handler
//        Looper.myLooper().setMessageLogging(new Printer() {
//            @Override
//            public void println(String x) {
//                Log.e("evan", "IN myLooper println(x) x = " + x);
//            }
//        });

//        Looper.myQueue();
//        Looper.prepare();

        // Handler callback
        mHandler = new LocalHandler(this, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                // true false 决定要不要继续下发给 Handler 子类 handleMessage(),看上面代码片段消息代码A。
                Log.e("evan","IN Handler Callback");
                return true;
            }
        });
        mHandler.sendEmptyMessage(MSG_1);

//        mHandler = new LocalHandler(this);
//        mHandler.sendEmptyMessage(MSG_1);

        // msg callback
//        Message msg = Message.obtain(mHandler, new Runnable() {
//            @Override
//            public void run() {
//                Log.e("evan","IN Message Runnable");
//            }
//        });
//        msg.what = MSG_1;
//        msg.obj = "msg.obj";
//        mHandler.sendMessage(msg);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                mHandler = new LocalHandler(MainActivity.this);
//                mHandler.sendEmptyMessage(MSG_1);
//                Looper.loop();
//            }
//        }).start();
        // Handler 处理分发消息代码 end

        // sThreadLocal 测试。不同线程看道的视图是不同的
       // 2021-02-25 12:06:10.169 19526-19526/com.example.pureandroid E/evan: main sThreadLocal get = is initialValue
        //2021-02-25 12:06:10.169 19526-19526/com.example.pureandroid E/evan: main sThreadLocal2 get = 10086
        //2021-02-25 12:06:10.169 19526-19526/com.example.pureandroid E/evan: main set value. sThreadLocal get = main set value
        //2021-02-25 12:06:10.169 19526-19526/com.example.pureandroid E/evan: main set value. sThreadLocal2 get = 110
        //2021-02-25 12:06:10.170 19526-19561/com.example.pureandroid E/evan: IN thread sThreadLocal get = is initialValue
        //2021-02-25 12:06:10.170 19526-19561/com.example.pureandroid E/evan: IN thread sThreadLocal2 get = 10086
        //2021-02-25 12:06:10.170 19526-19561/com.example.pureandroid E/evan: IN thread set value. sThreadLocal get = thread set value
        //2021-02-25 12:06:10.171 19526-19561/com.example.pureandroid E/evan: IN thread set value. sThreadLocal2 get = 119
        final ThreadLocal<String> sThreadLocal = new ThreadLocal<String>(){
            @Nullable
            @Override
            protected String initialValue() {
                return "is initialValue";
            }
        };
        final ThreadLocal<Integer> sThreadLocal2 = new ThreadLocal<Integer>(){
            @Nullable
            @Override
            protected Integer initialValue() {
                return 10086;
            }
        };
        Log.e("evan","main sThreadLocal get = "+sThreadLocal.get());
        Log.e("evan","main sThreadLocal2 get = "+sThreadLocal2.get());
        sThreadLocal.set("main set value");
        sThreadLocal2.set(110);
        Log.e("evan","main set value. sThreadLocal get = "+sThreadLocal.get());
        Log.e("evan","main set value. sThreadLocal2 get = "+sThreadLocal2.get());

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("evan","IN thread sThreadLocal get = "+sThreadLocal.get());
                Log.e("evan","IN thread sThreadLocal2 get = "+sThreadLocal2.get());
                sThreadLocal.set("thread set value");
                sThreadLocal2.set(119);
                Log.e("evan","IN thread set value. sThreadLocal get = "+sThreadLocal.get());
                Log.e("evan","IN thread set value. sThreadLocal2 get = "+sThreadLocal2.get());
            }
        }).start();

        // 同一个线程可以有多个 Handler.
        Message messageA = Message.obtain();
        messageA.what = 1;
        Message messageB = Message.obtain();
        messageB.what = 2;

        Handler handlerA = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.e("evan", "In handlerA handleMessage. msg = "+msg);
            }
        };

        Handler handlerB = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.e("evan", "In handlerB handleMessage. msg = "+msg);
            }
        };

        // 同一个消息发送会报错：
        //     MessageQueue.java
        //    if (msg.isInUse()) {
        //            throw new IllegalStateException(msg + " This message is already in use.");
        //        }
//        handlerA.sendMessage(messageA);
//        handlerB.sendMessage(messageA);
        handlerA.sendMessage(messageA);
        handlerB.sendMessage(messageB);
    }

    private static final int MSG_1 = 1;
    private static class LocalHandler extends Handler {
        WeakReference<MainActivity> weakReference;

        LocalHandler(MainActivity activity) {
            super();
            weakReference = new WeakReference<>(activity);
        }

        LocalHandler(MainActivity activity,Callback callback) {
            super(callback);
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = weakReference.get();
            if (activity == null) {
                Log.e("evan","handleMessage activity = null. return");
                return;
            }
            switch (msg.what) {
                case MSG_1:
                    Log.e("evan","handleMessage = " + MSG_1);
                break;
                default:
                    break;
            }
        }
    }

    private void ipcBitmapBinder() {
        Bitmap mBitmap = BitmapFactory.decodeFile("");
        Intent intent = new Intent(this, SecondActivity.class);
        Bundle bundle = new Bundle();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bundle.putBinder("bitmap", new BitmapBinder(mBitmap));
        }
        intent.putExtras(bundle);
        startActivity(intent);
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
            // 跨进程日志:
            // service connected.ComponentName = ComponentInfo{com.example.pureandroid/com.example.pureandroid.TestAidlServer}
            // service = android.os.BinderProxy@627fea6
            // service connected.mMediaServer = com.example.pureandroid.IMyAidlInterface$Stub$Proxy@90230e7

            // 同进程日志：
            // service connected.ComponentName = ComponentInfo{com.example.pureandroid/com.example.pureandroid.TestAidlServer}
            // service = com.example.pureandroid.TestAidlServer$1@90230e7
            // service connected.mMediaServer = com.example.pureandroid.TestAidlServer$1@90230e7
            // TestAidlServer 那边返回相同：90230e7
            // E/TestAidlServer: onBind. intent = Intent { cmp=com.example.pureandroid/.TestAidlServer }
            // return binder = com.example.pureandroid.TestAidlServer$1@90230e7

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