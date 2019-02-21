package com.yxt.itv.library.http;

import android.content.Context;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author <font color="pink"><b>JhoneLee</b></font>
 * @Date 2017/11/2
 * @Version 1.0
 * @Description okHttp默认引擎
 */
public class OkHttpEngine implements IHttpEngine {

    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    @Override
    public void get(Context context, String url, Map<String, Object> params, final EngineCallBack callBack) {
        Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onSuccess(response.body().toString());
            }
        });


    }

    @Override
    public void post(Context context, String url, Map<String, Object> params, final EngineCallBack callBack) {

        RequestBody body = new FormBody.Builder()
                .add("键", "值")
                .add("键", "值")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(body)
                .build();

        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onSuccess(response.body().toString());
            }
        });

    }
}
