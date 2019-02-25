package com.wuyazhou.learn.IPC;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wuyazhou.learn.IPC.AIDLTest.AIDLTestPagerView;
import com.wuyazhou.learn.IPC.AIDLTest.MessengerTestPagerView;
import com.wuyazhou.learn.logview.LogShowView;
import com.wuyazhou.pagerview.ModelPagerView;
import com.wuyazhou.pagerview.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager = null;

    private List<View> mViews = new ArrayList<View>();
    private List<String> mViewTitle = new ArrayList<String>();
    private ViewPagerAdapter mViewPagerAdapter = null;

    private LogShowView mShowLogView = null;

    private AIDLTestPagerView mAIDLTestPagerView = null;
    private MessengerTestPagerView mMessengerTestPagerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPager();
        initShowLogView();
    }

    private void initPager(){
        mViewPager = findViewById(R.id.view_pager);
        mViewPagerAdapter = new ViewPagerAdapter(mViews,mViewTitle, this);
        mViewPager.setAdapter(mViewPagerAdapter);
        mAIDLTestPagerView = new AIDLTestPagerView(this);
        addViewPagerView("AIDL",mAIDLTestPagerView);

        mMessengerTestPagerView = new MessengerTestPagerView(this);
        addViewPagerView("Messenger",mMessengerTestPagerView);

        addViewPagerView("标题二",new ModelPagerView(this));
        mViewPagerAdapter.notifyDataSetChanged();
    }

    private void addViewPagerView(String title, View view){
        mViewTitle.add(title);
        mViews.add(view);
    }

    private void initShowLogView(){
        mShowLogView = findViewById(R.id.show_log_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAIDLTestPagerView.release();
        mMessengerTestPagerView.release();
        mViews.clear();
        mViews = null;
        mShowLogView.release();
    }

}
