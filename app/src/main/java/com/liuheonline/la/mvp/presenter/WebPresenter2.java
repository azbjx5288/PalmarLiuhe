package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.WebEntity;
import com.liuheonline.la.network.api.ITokenServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

public class WebPresenter2 extends BasePresenter<BaseView<String>> {


    public void getEgurl(){
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ITokenServer.class)
                .getEgurl()
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<String>() {
                    @Override
                    protected void onSuccees(BaseEntity<String> t) throws Exception {
                        getView().successed(t.getmData());
                    }
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, new BaseErroeConsumer() {
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, () -> {

                });
    }
}
