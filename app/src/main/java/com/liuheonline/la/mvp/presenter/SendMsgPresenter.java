package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.network.api.ITokenServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

/**
 * @author: aini
 * @date 2018/7/17 17:13
 * @description 发送短信
 */
public class SendMsgPresenter extends BasePresenter<BaseView<Integer>>{

    public void sendMsg(String type,String name){

        getView().onLoading();

        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ITokenServer.class)
                .sendMsg(type, name)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<Integer>() {
                    @Override
                    protected void onSuccees(BaseEntity<Integer> t) throws Exception {
                        getView().successed(t.getmData());
                    }

                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code,error);
                    }
                },new BaseErroeConsumer() {
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code,error);
                    }
                },() -> {});
    }

}
