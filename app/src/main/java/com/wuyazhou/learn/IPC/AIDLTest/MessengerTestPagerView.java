package com.wuyazhou.learn.IPC.AIDLTest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.wuyazhou.learn.IPC.R;
import com.wuyazhou.learn.IPC.server.MessengerService;

import static com.wuyazhou.learn.IPC.server.MessengerService.MSG_FROM_CLIENT;

/**
 * @author 吴亚洲
 * @date 2018.7.7
 * @function
 */
public class MessengerTestPagerView extends FrameLayout implements View.OnClickListener {
    private Context mContext = null;
    private RelativeLayout mLayout;

    /** 监听服务*/
    Messenger mMessenger;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder serverBinder) {
            mMessenger = new Messenger(serverBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    public MessengerTestPagerView(Context context) {
        super(context);
        mContext = context;
        initView();

        initServiceForMessenger();
    }

    public MessengerTestPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();

        initServiceForMessenger();
    }

    public MessengerTestPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();

        initServiceForMessenger();
    }

    public void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (RelativeLayout) inflater.inflate(R.layout.pager_messenger_layout, null);

        addView(mLayout);

        View modelFirst  = mLayout.findViewById(R.id.model_button_1);
        modelFirst.setOnClickListener(this);
        View modelSecond  = mLayout.findViewById(R.id.model_button_2);
        modelSecond.setOnClickListener(this);
        View modelThird  = mLayout.findViewById(R.id.model_button_3);
        modelThird.setOnClickListener(this);
    }

    /**
     * 连接服务
     * */
    private void initServiceForMessenger(){
        Intent intent = new Intent(mContext, MessengerService.class);
        mContext.bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        try {
            if (i == R.id.model_button_1) {
                Message msg = Message.obtain(null,MSG_FROM_CLIENT);
                Bundle data = new Bundle();
                data.putString("msg","hello, Messenger");
                msg.setData(data);
                mMessenger.send(msg);
            } else if (i == R.id.model_button_2) {

            } else if (i == R.id.model_button_3) {

            }else {

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void release(){
        mContext.unbindService(mConnection);

        mConnection = null;
    }
}
