package com.wuyazhou.learn.logview;
/**
 * @author wuyzh
 * @fuction 显示log的回调
 * */
public interface LogShowViewContract {
    /**
     * 显示log
     * @param key
     * @param value
     * */
    void showLog(String key, String value);
}
