package com.example.pureandroid;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.example.pureandroid.i.IFileDownloadIPCCallback;
import com.example.pureandroid.i.IFileDownloadIPCService;
import com.example.pureandroid.message.MessageSnapshot;

public class DownloadServiceStub extends IFileDownloadIPCService.Stub {
    private final RemoteCallbackList<IFileDownloadIPCCallback> callbackList =
            new RemoteCallbackList<>();

    @SuppressWarnings("UnusedReturnValue")
    private synchronized int callback(MessageSnapshot snapShot) {
        final int n = callbackList.beginBroadcast();
        try {
            for (int i = 0; i < n; i++) {
                callbackList.getBroadcastItem(i).callback(snapShot);
            }
        } catch (RemoteException e) {
            Log.e("DownloadService",  "callback error");
        } finally {
            callbackList.finishBroadcast();
        }

        return n;
    }

    @Override
    public void registerCallback(IFileDownloadIPCCallback callback) throws RemoteException {
        callbackList.register(callback);
    }

    @Override
    public void unregisterCallback(IFileDownloadIPCCallback callback) throws RemoteException {
        callbackList.unregister(callback);
    }

    @Override
    public boolean pause(int downloadId) throws RemoteException {

        // test callback:
        MessageSnapshot snapshot = null;
        callback(snapshot);
        // test callback:

        return false;
    }
}
