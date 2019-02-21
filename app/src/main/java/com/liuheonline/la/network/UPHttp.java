package com.liuheonline.la.network;

import com.liuheonline.la.network.retrofit.RetrofitFactory;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: aini
 * @date 2018/6/14 10:56
 * @description 更新网络请求
 */
public class UPHttp extends RetrofitFactory {

    private static UPHttp INSTANCE  ;

    private UPHttp(String url) {
        super(url, GsonConverterFactory.create());
    }
    public static UPHttp getInstance(String url) {
        if(INSTANCE==null) {
            INSTANCE = new UPHttp(url);
        }
        return INSTANCE;
    }
}
