package com.liuheonline.la.mvp.presenter;

import com.mylove.loglib.JLog;
import com.liuheonline.la.entity.TokenEntity;
import com.liuheonline.la.network.api.ITokenServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.JavaBeanToString;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.HashMap;

import io.reactivex.functions.Action;

public class TokenPresenter extends BasePresenter<BaseView<TokenEntity>> {

    public void getToken(String devicekey){
        getView().onLoading();
        HashMap<String,Object> map = new HashMap<>();
        map.put("log_at",2);
        map.put("devicekey",devicekey);
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ITokenServer.class)
                .getToken(map)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<TokenEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<TokenEntity> t) throws Exception {
                        JLog.v(JavaBeanToString.toString(t));
                        getView().successed(t.getmData());
                    }

                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code,error);
                    }
                }, new BaseErroeConsumer() {                    @Override                    protected void onCodeError(int code, String error) throws Exception {                        getView().onLoadFailed(code, error);                    }                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }
}
