package com.yxt.itv.library.base;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/13
 * @Version 1.0
 * @Description mvp 基本presenter
 */

public abstract class BasePresenter<V> {

    protected boolean isNetWork = false;

    //rxjava
    protected Disposable mSubscription;
    //View的绑定
    //弱引用，避免内存溢出
    protected WeakReference<V> mViewReference;

    protected V v;

    public void attachView(V view) {
       v = view;
    }

    public V getView() {
       return v;
    }

    public boolean isViewAttached() {
        return mViewReference != null && mViewReference.get() != null;
    }
    public void detachView() {
        if (mViewReference != null) {
            mViewReference.clear();
            mViewReference = null;
        }
        if (mSubscription != null){
            mSubscription.dispose();
        }
    }
}
