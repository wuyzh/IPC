package com.wuyazhou.learn.IPC.server;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * @author wuyzh
 */
public class MessengerService extends Service {
    public static final int MSG_FROM_CLIENT = 101;
    public static final int MSG_FROM_SERVICE = 102;
    private class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_FROM_CLIENT:
                    //获取客户端发来的信息
                    Log.d("wuyazhouMessenger","Messenger获取到的信息："+msg.getData().getString("msg"));

                    //回复给客户端的信息
                    Messenger client = msg.replyTo;
                    Message replyMessage = Message.obtain(null,MSG_FROM_SERVICE);
                    Bundle data = new Bundle();
                    data.putString("msg","这是对你的回复");
                    replyMessage.setData(data);
                    try {
                        if (client != null){
                            client.send(replyMessage);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
