package com.wuyazhou.learn.IPC.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wuyzh on 2017/11/10.
 */

public class Book implements Parcelable {
    public int bookId;
    public String bookName;

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    /**
     * 序列化
     * */
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(bookId);
        out.writeString(bookName);
    }

    /**
     * 内容描述功能
     * */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 反序列化
     */
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };



    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }
}
