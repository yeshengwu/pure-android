package com.example.pureandroid.testaidl;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.pureandroid.R;
import com.example.pureandroid.message.Message;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public class TestAidlAc extends FragmentActivity {
    private static final String TAG = "TestAidlAc";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_aidl);
        findViewById(R.id.test_tv_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatManager.Instance().basicTypes(1, 2, true, 1.0f, 11, "sss");
            }
        });

        findViewById(R.id.test_rv_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Message> messages = ChatManager.Instance().getMessages(110);
                Log.e(TAG, "getMessages. messages = " + messages);
            }
        });

        ChatManager.Instance().addOnReceiveMessageListener(new OnReceiveMessageListener() {
            @Override
            public void onReceiveMessage(List<Message> messages, boolean hasMore) {
                Log.e(TAG, "onReceiveMessage. messages = " + messages + " hasMore = " + hasMore);
            }
        });
    }


}
