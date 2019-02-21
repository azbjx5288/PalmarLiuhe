package com.yxt.itv.library.base;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/13
 * @Version 1.0
 * @Description
 */
public interface BaseView<T> {
    void onLoading();
    void onLoadFailed(int code, String error);
    void successed(T t);
}
