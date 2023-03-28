package com.example.pureandroid.testaidl;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.pureandroid.IMyAidlInterface;
import com.example.pureandroid.IOnReceiveMessageListener;
import com.example.pureandroid.message.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import static android.content.Context.BIND_AUTO_CREATE;

public class ChatManager {
    private static final String TAG = ChatManager.class.getName();
    private static ChatManager INST;
    private static Context gContext;

    private static IMyAidlInterface mClient;

    private Handler mainHandler;
    private Handler workHandler;
    private boolean isBackground = true;

    public static ChatManager Instance() throws NotInitializedExecption {
        if (INST == null) {
            throw new NotInitializedExecption();
        }
        return INST;
    }

    public static void init(Application context, String serverHost) {
        if (INST != null) {
            // TODO: Already initialized
            return;
        }
        gContext = context.getApplicationContext();
        INST = new ChatManager();
        INST.mainHandler = new Handler();
        HandlerThread thread = new HandlerThread("workHandler");
        thread.start();
        INST.workHandler = new Handler(thread.getLooper());
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            public void onForeground() {
                Log.e(TAG, "onForeground");
                INST.isBackground = false;
                if (mClient == null) {
                    return;
                }
//                try {
//                    mClient.setForeground(1);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            public void onBackground() {
                Log.e(TAG, "onBackground");
                INST.isBackground = true;
                if (mClient == null) {
                    return;
                }
                 /*
                try {
                    mClient.setForeground(0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }*/
            }
        });

        INST.checkRemoteService();
    }

    public void shutdown() {
        if (mClient != null) {
            gContext.unbindService(mServiceConnection);
        }
    }

    private boolean checkRemoteService() {
        if (INST != null) {
            if (mClient != null) {
                return true;
            }

            Intent intent = new Intent(gContext, TestAidlServer.class);
            boolean result = gContext.bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
            if (!result) {
                Log.e(TAG, "Bind service failure");
            }
        } else {
            Log.e(TAG, "Chat manager not initialized");
        }

        return false;
    }

    ///---------biz start
    public int basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                          double aDouble, String aString) {
        if (!checkRemoteService()) {
            return -100;
        }
        try {
            return mClient.basicTypes(anInt, aLong, aBoolean, aFloat, aDouble, aString);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Message> getMessages(long fromIndex) {
        try {
            return mClient.getMessages(fromIndex);
        } catch (RemoteException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    private List<OnReceiveMessageListener> onReceiveMessageListeners = new ArrayList<>();

    /**
     * 添加新消息监听, 记得调用{@link #removeOnReceiveMessageListener(OnReceiveMessageListener)}删除监听
     *
     * @param listener
     */
    public void addOnReceiveMessageListener(OnReceiveMessageListener listener) {
        if (listener == null) {
            return;
        }
        onReceiveMessageListeners.add((listener));
    }

    /**
     * 删除消息监听
     *
     * @param listener
     */
    public void removeOnReceiveMessageListener(OnReceiveMessageListener listener) {
        if (listener == null) {
            return;
        }
        onReceiveMessageListeners.remove(listener);
    }

    /**
     * 收到新消息
     *
     * @param messages
     * @param hasMore  是否还有更多消息待收取
     */
    private void onReceiveMessage(final List<Message> messages, final boolean hasMore) {
        mainHandler.post(() -> {
            Iterator<OnReceiveMessageListener> iterator = onReceiveMessageListeners.iterator();
            OnReceiveMessageListener listener;
            while (iterator.hasNext()) {
                listener = iterator.next();
                listener.onReceiveMessage(messages, hasMore);
            }

            // 消息数大于时，认为是历史消息同步，不通知群被删除
            if (messages.size() > 10) {
                return;
            }
            /*for (Message message : messages) {
                if ((message.content instanceof QuitGroupNotificationContent && ((QuitGroupNotificationContent) message.content).operator.equals(getUserId()))
                        || (message.content instanceof KickoffGroupMemberNotificationContent && ((KickoffGroupMemberNotificationContent) message.content).kickedMembers.contains(getUserId()))
                        || message.content instanceof DismissGroupNotificationContent) {
                    for (OnRemoveConversationListener l : removeConversationListeners) {
                        l.onConversationRemove(message.conversation);
                    }
                }
            }*/
        });
    }

    /**
     * 消息被撤回
     *
     * @param messageUid
     */
    private void onRecallMessage(final long messageUid) {
       /* Message message = new Message();
        // 想撤回的消息已经被删除
        if (message == null) {
            return;
        }
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                for (OnRecallMessageListener listener : recallMessageListeners) {
                    listener.onRecallMessage(message);
                }
            }
        });*/
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "service disconnected. ComponentName = " + name);
            mClient = null;
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
            mClient = IMyAidlInterface.Stub.asInterface(service);
            Log.e(TAG, "service connected.mMediaServer = " + mClient);
            IBinder aBinder = mClient.asBinder();
            Log.e(TAG, "service connected.aBinder = " + aBinder);
            // 跨进程日志:
            // service connected.ComponentName = ComponentInfo{com.example.pureandroid/com.example.pureandroid.testaidl.TestAidlServer}
            // service = android.os.BinderProxy@627fea6
            // service connected.mMediaServer = com.example.pureandroid.IMyAidlInterface$Stub$Proxy@90230e7

            // 同进程日志：
            // service connected.ComponentName = ComponentInfo{com.example.pureandroid/com.example.pureandroid.testaidl.TestAidlServer}
            // service = com.example.pureandroid.testaidl.TestAidlServer$1@90230e7
            // service connected.mMediaServer = com.example.pureandroid.testaidl.TestAidlServer$1@90230e7
            // TestAidlServer 那边返回相同：90230e7
            // E/TestAidlServer: onBind. intent = Intent { cmp=com.example.pureandroid/.TestAidlServer }
            // return binder = com.example.pureandroid.testaidl.TestAidlServer$1@90230e7

//            service.pingBinder()
//            service.isBinderAlive();

            try {
                mClient.setOnReceiveMessageListener(new IOnReceiveMessageListener.Stub() {

                    @Override
                    public void onReceive(List<Message> messages, boolean hasMore) throws RemoteException {
                        onReceiveMessage(messages, hasMore);
                    }

                    @Override
                    public void onRecall(long messageUid) throws RemoteException {
                        onRecallMessage(messageUid);
                    }
                });

            } catch (RemoteException e) {
                e.printStackTrace();
            }

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
