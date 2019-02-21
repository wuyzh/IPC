package com.wuyazhou.learn.IPC;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * @author wuyzh
 * @function 初始化一些数据
 * */
public class LauncherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initLeakCanary();
    }
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
