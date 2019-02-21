package com.yxt.itv.library.http.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/21
 * @Version 1.0
 * @Description  接口基类
 */
public class BaseEntity<T> {

    @SerializedName("code")
    private int mCode;
    @SerializedName("msg")
    private String mMessage;
    @SerializedName("data")
    private T mData;
    private static int SUCCESS_CODE=200;//成功的code

    public boolean isSuccess(){
        return getmCode()== SUCCESS_CODE || getmCode() == 201;
    }

    public int getmCode() {
        return mCode;
    }

    public void setmCode(int mCode) {
        this.mCode = mCode;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public T getmData() {
        return mData;
    }

    public void setmData(T mData) {
        this.mData = mData;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "mCode=" + mCode +
                ", mMessage='" + mMessage + '\'' +
                ", mData=" + mData +
                '}';
    }
}
