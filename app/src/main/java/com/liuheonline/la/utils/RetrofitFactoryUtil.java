package com.liuheonline.la.utils;

import com.liuheonline.la.network.UPHttp;
import com.liuheonline.la.network.WLHttp;
import com.liuheonline.la.network.retrofit.RetrofitFactory;

/**
 * Created by aini on 2018/5/15.
 * 网络请求工厂类
 */

public class RetrofitFactoryUtil {

    public static RetrofitFactory getRetrofit(String url){
        if(url.equals(Constant.UP_URL)){
            return UPHttp.getInstance(url);
        }
        return WLHttp.getInstance(url);
    }
}
