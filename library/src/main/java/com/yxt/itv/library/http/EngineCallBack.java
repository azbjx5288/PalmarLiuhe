package com.yxt.itv.library.http;

import android.content.Context;

import java.util.Map;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/2
 * @Version 1.0
 * @Description 请求回调方法
 */
public interface EngineCallBack {

    //开始执行，
    void onPreExecute(Context context, Map<String, Object> params);
    void onError(Exception e);

    //返回对象会出现问题  成功是data对象，失败data字符串
    void onSuccess(String result);

    //默认的
    EngineCallBack DEFAULT_CALLBACK = new EngineCallBack() {

        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }
        @Override
        public void onError(Exception e) {

        }
        @Override
        public void onSuccess(String result) {

        }
    };
}
