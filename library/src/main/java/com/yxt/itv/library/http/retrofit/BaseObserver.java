package com.yxt.itv.library.http.retrofit;

import android.accounts.NetworkErrorException;
import android.content.Context;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/21
 * @Version 1.0
 * @Description
 */

public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {

    protected Context mContext;
    public BaseObserver(Context cxt) {
        this.mContext = cxt;
    }
    public BaseObserver() {

    }
    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();

    }

    @Override
    public void onNext(BaseEntity<T> baseEntity) {
        onRequestEnd();
        if (baseEntity.isSuccess()) {
            try {
                onSuccees(baseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onCodeError(baseEntity.getmCode(),baseEntity.getmMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
//        Log.w(TAG, "onError: ", );这里可以打印错误信息
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(BaseEntity<T> t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param code
     * @throws Exception
     */
    protected  abstract void onCodeError(int code,String error) throws Exception ;

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;
    //重新开始请求
    protected void onRequestStart() {
    }
    //请求结束时
    protected void onRequestEnd() {

    }



}