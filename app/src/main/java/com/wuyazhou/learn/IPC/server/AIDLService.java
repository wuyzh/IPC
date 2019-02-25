package com.wuyazhou.learn.IPC.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.wuyazhou.learn.IPC.aidl.Book;
import com.wuyazhou.learn.IPC.aidl.ICallback;
import com.wuyazhou.learn.IPC.aidl.IOnNewBookArrivedListener;
import com.wuyazhou.learn.IPC.aidl.IService;


public class AIDLService extends Service {
    RemoteCallbackList<IOnNewBookArrivedListener> mRemoteCallbackList = new RemoteCallbackList<IOnNewBookArrivedListener>();


    public AIDLService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    private final IService.Stub mBinder = new IService.Stub() {
        @Override
        public String hello(String name) throws RemoteException {
            return "Server处理后的: "+name;
        }


        @Override
        public Book addBook() throws RemoteException {
            onNewBookArrived(new Book(123,"艺术探索"));
            return new Book(123,"艺术探索");
        }

        @Override
        public void useCallback(ICallback callback) throws RemoteException {
            callback.callback(new Book(456,"Android进阶"));
        }

        /** 添加监听*/
        @Override
        public void registerListener(IOnNewBookArrivedListener listener) {
            mRemoteCallbackList.register(listener);

            final int N = mRemoteCallbackList.beginBroadcast();
            mRemoteCallbackList.finishBroadcast();
        }

        /** 移除监听*/
        @Override
        public void unRegisterListener(IOnNewBookArrivedListener listener) {
            mRemoteCallbackList.unregister(listener);

            final int N = mRemoteCallbackList.beginBroadcast();
            mRemoteCallbackList.finishBroadcast();
        }
    };
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        final int N = mRemoteCallbackList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener l = mRemoteCallbackList.getBroadcastItem(i);
            if (l != null) {
                try {
                    l.onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        mRemoteCallbackList.finishBroadcast();
    }
}
