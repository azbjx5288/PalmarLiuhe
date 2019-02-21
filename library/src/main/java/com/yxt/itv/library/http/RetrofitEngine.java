package com.yxt.itv.library.http;

import android.content.Context;

import java.util.Map;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/21
 * @Version 1.0
 * @Description
 */
public class RetrofitEngine implements IHttpEngine {

    public <T> void HttpRetrofit(Class<T> clazz,Map<String, Object> params){
      //  RetrofitFactory.getInstance().getApiServivc(clazz)
    }

    @Override
    public void get(Context context, String url, Map<String, Object> params, EngineCallBack callBack) {

    }

    @Override
    public void post(Context context, String url, Map<String, Object> params, EngineCallBack callBack) {

    }
}
