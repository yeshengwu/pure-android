package com.example.pureandroid.i;

import com.example.pureandroid.i.IFileDownloadIPCCallback;
import com.example.pureandroid.message.MessageSnapshot;

interface IFileDownloadIPCService {

    oneway void registerCallback(in IFileDownloadIPCCallback callback);
    oneway void unregisterCallback(in IFileDownloadIPCCallback callback);

     boolean pause(int downloadId);
}
