package com.liyh.app.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 27 日
 * @time 17 时 07 分
 * @descrip :
 */
public class JavaBean implements Parcelable {
    private String name;
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.price);
    }

    /**
     *
     * @param dest Parcel对象，用来存储和传输数据的
     */
    public void readFromParcel(Parcel dest) {
        //顺序必须于writeToParcel方法中的一致
        name = dest.readString();
        price = dest.readInt();
    }

    public JavaBean() {
    }

    protected JavaBean(Parcel in) {
        this.name = in.readString();
        this.price = in.readInt();
    }

    public static final Parcelable.Creator<JavaBean> CREATOR = new Parcelable.Creator<JavaBean>() {
        @Override
        public JavaBean createFromParcel(Parcel source) {
            return new JavaBean(source);
        }

        @Override
        public JavaBean[] newArray(int size) {
            return new JavaBean[size];
        }
    };
}
