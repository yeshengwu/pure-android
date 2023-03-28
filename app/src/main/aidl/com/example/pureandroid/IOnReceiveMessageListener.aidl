package com.example.pureandroid;

// Declare any non-default types here with import statements
import com.example.pureandroid.message.Message;

interface IOnReceiveMessageListener {
    void onReceive(in List<Message> messages, boolean hasMore);
    void onRecall(in long messageUid);
}
