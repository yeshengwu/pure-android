package com.example.pureandroid;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;

/**
 * 测试 Handler 泄露链
 */
public class TestHandlerAc extends Activity {
    public static final String TAG = "TestHandlerAc";

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(200,400));
        textView.setText("testHandler. can click me");
        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setContentView(textView);
        // onTouch和onTouchEvent以及onClick的顺序
        // https://blog.csdn.net/jim1451/article/details/93095828
        /**
         * 1 onTouchListener的onTouch方法优先级比onTouchEvent高，会先触发。
         *
         * 2 假如onTouch方法返回false会接着触发onTouchEvent，反之onTouchEvent方法不会被调用。
         *
         * 3 内置诸如click事件的实现等等都基于onTouchEvent，假如onTouch返回true，这些事件将不会被触发。
         */
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.e(TAG,"onTouch. return false");
                Log.e(TAG,"onTouch. return true");
                return true;
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"onClick");
            }
        });

//        mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        };
        mHandler = new LocalHandler(this);
        Message message = Message.obtain();

        message.what = MSG_1;

        mHandler.sendMessageDelayed(message, 10 * 1000);
        // android.view.WindowManager$BadTokenException: Unable to add window -- token null is not valid; is your activity running?
        // PopupWindow

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                Log.e(TAG,"doFrame. SystemClock.uptimeMillis = " + SystemClock.uptimeMillis()); //is counted in milliseconds since the system was booted and stops when the system enters deep sleep
                Log.e(TAG,"doFrame. SystemClock.elapsedRealtime = " + SystemClock.elapsedRealtime()); //the time since the system was booted, and include deep sleep
                Log.e(TAG,"doFrame. System.currentTimeMillis = " + System.currentTimeMillis());
                Log.e(TAG,"doFrame. frameTimeNanos = " + frameTimeNanos);

                // 需要循环调用一下。
//                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // android.view.WindowManager$BadTokenException: Unable to add window -- token null is not valid; is your activity running?
        // PopupWindow
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // 场景： android popupwindow onCreate
        // https://www.cnblogs.com/yulook/p/5570593.html android 关于无法在onCreate方法中显示popUpWindow的解决方案
        if (hasFocus) {
            PopupWindow popupWindow = new PopupWindow(TestHandlerAc.this);
            TextView xx = new TextView(TestHandlerAc.this);
            xx.setLayoutParams(new LinearLayout.LayoutParams(200,200));
            xx.setBackgroundColor(TestHandlerAc.this.getColor(R.color.colorAccent));
            xx.setText("test popupWindow");
            popupWindow.setContentView(xx);
            popupWindow.showAsDropDown(getWindow().getDecorView(),300,200);
        }
    }

    private static final int MSG_1 = 1;
    private static class LocalHandler extends Handler {
        WeakReference<TestHandlerAc> weakReference;

        LocalHandler(TestHandlerAc activity) {
            super();
            weakReference = new WeakReference<>(activity);
        }

        LocalHandler(TestHandlerAc activity,Callback callback) {
            super(callback);
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TestHandlerAc activity = weakReference.get();
            if (activity == null) {
                Log.e(TAG,"handleMessage activity = null. return");
                return;
            }
            switch (msg.what) {
                case MSG_1:
                    Log.e(TAG,"handleMessage = " + MSG_1);
                    break;
                default:
                    break;
            }
        }
    }
}
