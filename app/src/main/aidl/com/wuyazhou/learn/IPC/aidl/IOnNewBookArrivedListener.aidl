// IOnNewBookArrivedListener.aidl
package com.wuyazhou.learn.IPC.aidl;
import com.wuyazhou.learn.IPC.aidl.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
