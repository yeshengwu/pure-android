package com.example.pureandroid.i;

import com.example.pureandroid.message.MessageSnapshot;

interface IFileDownloadIPCCallback {
    oneway void callback(in MessageSnapshot snapshot);
}
