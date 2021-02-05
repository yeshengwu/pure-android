package com.example.pureandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TestLayoutAc extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_layout);

        // 问题：每个 view 的 getLayoutParams 返回是什么
        LinearLayout rootContainer = findViewById(R.id.root_container);
        TextView textViewInRootContainer = findViewById(R.id.test_textview);
        RelativeLayout textRvContainerInRootContainer = findViewById(R.id.test_rv_container);
        TextView textViewInRvContainer = findViewById(R.id.test2_textview);

        ViewParent rootGetParent = rootContainer.getParent();
        textViewInRvContainer.requestLayout();
        Log.e("evan","rootContainer.getParent() = "+rootGetParent);
        Log.e("evan","rootContainer.getLayoutParams() = "+rootContainer.getLayoutParams());
        Log.e("evan","textViewInRootContainer.getLayoutParams() = "+textViewInRootContainer.getLayoutParams());
        Log.e("evan","textRvContainerInRootContainer.getLayoutParams() = "+textRvContainerInRootContainer.getLayoutParams());
        Log.e("evan","textViewInRvContainer.getLayoutParams() = "+textViewInRvContainer.getLayoutParams());
    }
}
