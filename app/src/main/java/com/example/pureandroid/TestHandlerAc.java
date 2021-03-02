package com.example.pureandroid;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
        textView.setText("testHandler");
        setContentView(textView);

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
