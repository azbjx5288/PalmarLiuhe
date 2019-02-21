package com.yxt.itv.library.http.retrofit;

import android.content.Context;

import io.reactivex.functions.Consumer;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/21
 * @Version 1.0
 * @Description
 */

public abstract class BaseConsumer<T> implements Consumer<BaseEntity<T>> {

    protected Context mContext;

    public BaseConsumer(Context cxt) {
        this.mContext = cxt;
    }
    public BaseConsumer() {
    }
    @Override
    public void accept(BaseEntity<T> baseEntity) throws Exception {
       // onRequestEnd();
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



    /**
     * 返回成功
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(BaseEntity<T> t) throws Exception;

    /**
     * 返回失败
     * @param error
     * @throws Exception
     */
    protected  abstract void onCodeError(int code,String error) throws Exception ;


}