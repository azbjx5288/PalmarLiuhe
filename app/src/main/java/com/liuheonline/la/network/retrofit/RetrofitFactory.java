package com.liuheonline.la.network.retrofit;

import com.google.gson.Gson;
import com.liuheonline.la.entity.PhoneConfig;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.ui.widget.SystemUtil;
import com.liuheonline.la.utils.SharedperfencesUtil;
import com.mylove.loglib.JLog;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/21
 * @Version 1.0
 * @Description
 */
public class RetrofitFactory {

    private Retrofit mRetrofit;

    protected RetrofitFactory(String url, Converter.Factory factory) {

        //初始化retrofit
        mRetrofit = new Retrofit
                .Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(factory)
                .client(getClient())
                .build();
        getClient();

    }

    public <T> T getApiService(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }

    //设置OKhttp 相关配置
    private OkHttpClient getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //http打印日志
                //LogUtil.d("http请求",message);
                JLog.d("http请求", message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient
                .Builder()
                .proxy(Proxy.NO_PROXY)
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    // 添加全局header
                    Request.Builder requestBuilder = original.newBuilder()
                            .addHeader("accesstoken", SharedperfencesUtil.getString(LiuHeApplication.context, "token"));
                    JLog.w("token", SharedperfencesUtil.getString(LiuHeApplication.context, "token"));
                    PhoneConfig phoneConfig = new PhoneConfig(SystemUtil.getSystemVersion(), SystemUtil.getSystemModel(), SystemUtil.getDeviceBrand(), SystemUtil.getIMEI(LiuHeApplication.context), SystemUtil.getVersionName(), "android");
                    Gson gson = new Gson();
                    requestBuilder.addHeader("phoneonline", gson.toJson(phoneConfig));
                    requestBuilder.addHeader("cookie", SharedperfencesUtil.getString(LiuHeApplication.context, "cookie"));
                    // Log.w("thegson",gson.toJson(phoneConfig));
                    if (!SharedperfencesUtil.getBoolean(LiuHeApplication.context, "palmarliuhe")) {
                        SharedperfencesUtil.setBoolean(LiuHeApplication.context, "palmarliuhe", true);
                        requestBuilder.addHeader("phoneconfig", gson.toJson(phoneConfig));

                    } else {
                        requestBuilder.removeHeader("phoneconfig");
                    }

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }).build();
        return client;
    }

}

