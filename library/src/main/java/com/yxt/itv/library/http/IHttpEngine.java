package com.yxt.itv.library.http;

import android.content.Context;

import java.util.Map;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/2
 * @Version 1.0
 * @Description 引擎的规范
 */
public interface IHttpEngine {

    //get 请求
    void get(Context context, String url, Map<String, Object> params, EngineCallBack callBack);

    //post 请求
    void post(Context context, String url, Map<String, Object> params, EngineCallBack callBack);
    //上传文件
    //下载文件
    //https 添加证书
}
