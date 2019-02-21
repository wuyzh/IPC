// IService.aidl
package com.wuyazhou.learn.IPC.aidl;
import com.wuyazhou.learn.IPC.aidl.Book;
import com.wuyazhou.learn.IPC.aidl.IOnNewBookArrivedListener;

// Declare any non-default types here with import statements

interface IService {
    String hello(String name);
    Book addBook();
    void registerListener(IOnNewBookArrivedListener listener);
    void unRegisterListener(IOnNewBookArrivedListener listener);
}
