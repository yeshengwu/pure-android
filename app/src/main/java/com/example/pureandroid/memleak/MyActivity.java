package com.example.pureandroid.memleak;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pureandroid.R;

import androidx.fragment.app.FragmentActivity;

public class MyActivity extends FragmentActivity {

//    private static class MyHandler extends Handler {
//        private final WeakReference<MyActivity> mActivity;
//
//        public MyHandler(MyActivity activity) {
//            mActivity = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            MyActivity activity = mActivity.get();
//            if (activity != null) {
//                // do something
//            }
//        }
//    }
//
//    private final MyHandler mHandler = new MyHandler(this);

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(200,400));
        textView.setText("MyActivity Handler leak context.");
        textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setContentView(textView);

        mHandler.sendEmptyMessageDelayed(0, 60 * 1000);
    }
}
