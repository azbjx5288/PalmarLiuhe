package com.yxt.itv.library.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/2
 * @Version 1.0
 * @Description
 */
public class HttpUtils  {

    public static OkHttpEngine handler ;
    //链式调用
    private String mUrl;
    private int mType = GET_TYPE;
    private Context mContext;

    private static final int POST_TYPE = 0x0011;
    private static final int GET_TYPE = 0x0101;

    private Map<String,Object> mParams;

    private static IHttpEngine mHttpEngine = new OkHttpEngine();

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    private HttpUtils(Context context) {
        this.mContext = context;
        this.mParams = new HashMap<>();
    }

    public HttpUtils post() {
        mType = POST_TYPE;
        return this;
    }
    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }
    public HttpUtils get() {
        mType = GET_TYPE;
        return this;
    }
    public HttpUtils addParam(String key,Object value){
        mParams.put(key,value);
        return this;
    }
    public HttpUtils addParams(Map<String,Object> params){
        mParams.putAll(params);
        return this;
    }

    public void execute(){
        execute(null);
    }
    public void execute(EngineCallBack callBack){
        callBack.onPreExecute(mContext,mParams);
        if (callBack==null){
            callBack = EngineCallBack.DEFAULT_CALLBACK;
        }
        if (mType == POST_TYPE){
            post(mUrl,mParams,callBack);
        }
        if (mType ==GET_TYPE){
            get(mUrl,mParams,callBack);
        }
      //  execute(null);
    }
    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    public static void exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }
    private void get(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.get(mContext,url, params, callBack);
    }
    private void post(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.post(mContext,url, params, callBack);
    }
}
