package com.wuyazhou.learn.IPC.AIDLTest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.wuyazhou.learn.IPC.R;
import com.wuyazhou.learn.IPC.aidl.Book;
import com.wuyazhou.learn.IPC.aidl.ICallback;
import com.wuyazhou.learn.IPC.aidl.IOnNewBookArrivedListener;
import com.wuyazhou.learn.IPC.aidl.IService;
import com.wuyazhou.learn.IPC.server.AIDLService;
import com.wuyazhou.learn.logview.LogShowUtil;

/**
 * @author 吴亚洲
 * @date 2018.7.7
 * @function
 */
public class AIDLTestPagerView extends FrameLayout implements View.OnClickListener {
    private Context mContext = null;
    private RelativeLayout mLayout;

    /** 监听服务*/
    IService mRemoteService;
    IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mRemoteService == null){
                return;
            }
            mRemoteService.asBinder().unlinkToDeath(mDeathRecipient,0);
            mRemoteService = null;
            // TODO: 做重新绑定service服务，或者其他的操作
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder serverBinder) {
            mRemoteService = IService.Stub.asInterface(serverBinder);

            try {
                mRemoteService.registerListener(mOnNewBookArrivedListener);
                //给binder设置死亡代理
                serverBinder.linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            Log.d("wuyazhouAIDL","有新书到了");
        }
    };

    private ICallback mCallback = new ICallback.Stub() {
        @Override
        public void callback(Book newBook) throws RemoteException {
            Log.d("wuyazhouAIDL","回调："+newBook.bookName);
        }
    };
    public AIDLTestPagerView(Context context) {
        super(context);
        mContext = context;
        initView();

        initServiceForAIDL();
    }

    public AIDLTestPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();

        initServiceForAIDL();
    }

    public AIDLTestPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();

        initServiceForAIDL();
    }

    public void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (RelativeLayout) inflater.inflate(R.layout.pager_aidl_layout, null);

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
    private void initServiceForAIDL(){
        Intent intent = new Intent(mContext, AIDLService.class);
        mContext.bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        try {
            if (i == R.id.model_button_1) {
                String string = "AIDL test";
                LogShowUtil.addLog("Client", "交给Server处理:"+string);
                string = mRemoteService.hello(string);
                LogShowUtil.addLog("Client", string);
            } else if (i == R.id.model_button_2) {
                Book book = mRemoteService.addBook();
                LogShowUtil.addLog("Client", "获取到的书籍:"+book.bookName);
            } else if (i == R.id.model_button_3) {
                mRemoteService.useCallback(mCallback);
            }else {

            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void release(){
        try {
            mRemoteService.unRegisterListener(mOnNewBookArrivedListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mContext.unbindService(mConnection);

        mConnection = null;
    }
}
