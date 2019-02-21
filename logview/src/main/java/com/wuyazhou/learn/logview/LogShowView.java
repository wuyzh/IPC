package com.wuyazhou.learn.logview;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyzh
 * @fuction 展示自己定义的log信息
 * */
public class LogShowView extends RelativeLayout implements LogShowViewContract {
    private Context mContext = null;
    private ViewGroup mViewGroup;

    private LogShowThread mShowLogThread;

    private ImageView mImageView = null;

    private ListView mLogListView = null;
    private LogListViewAdapter mLogListViewAdapter = null;
    private List<LogModel> mList = new ArrayList<>();

    private Handler mHandler = null;
    private LogModel mLogModel = null;
    public LogShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public LogShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LogShowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView();
    }

    private void initView() {
        mHandler = new Handler();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewGroup = (ViewGroup) inflater.inflate(R.layout.show_log_layout, null);
        addView(mViewGroup);

        mLogListView = mViewGroup.findViewById(R.id.log_list_view);
        mLogListViewAdapter = new LogListViewAdapter(mContext,mList);
        mLogListView.setAdapter(mLogListViewAdapter);

        mShowLogThread = new LogShowThread(LogShowUtil.getLogQueue());

        mImageView = mViewGroup.findViewById(R.id.clear_image_view);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogListViewAdapter.clean();
            }
        });
    }


    @Override
    public void showLog(String key,String value) {
        mLogModel = new LogModel(key,value);
        mHandler.post(new RunnableUi(mLogModel));
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE){
            if (!mShowLogThread.isAlive()){
                mShowLogThread.start(this);
            }

        }else {
            if (mShowLogThread.isAlive()){
                mShowLogThread.quit();
            }
        }
    }

    public void release(){
        mShowLogThread.quit();
        LogShowUtil.release();
    }

    static class LogModel implements Comparable{
        public String key;
        public String value;
        public LogModel(String key,String value){
            this.key = key;
            this.value = value;
        }
        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }

    class RunnableUi implements  Runnable{
        private LogModel logModel;
        public RunnableUi(LogModel logModel){
            this.logModel = logModel;
        }
        @Override
        public void run() {
            mLogListViewAdapter.addNewLog(logModel);
        }
    }

}
