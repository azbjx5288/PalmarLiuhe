package com.liuheonline.la.network;

import com.liuheonline.la.network.retrofit.RetrofitFactory;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/21
 * @Version 1.0
 * @Description
 */
public class WLHttp extends RetrofitFactory {

    private static WLHttp INSTANCE  ;

    private WLHttp(String url) {
        super(url,GsonConverterFactory.create());
    }
    public static WLHttp getInstance(String url) {
            if(INSTANCE==null) {
                INSTANCE = new WLHttp(url);
            }
        return INSTANCE;
    }
}

