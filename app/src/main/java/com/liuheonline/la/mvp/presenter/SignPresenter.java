package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.SignEntity;
import com.liuheonline.la.network.api.IUserServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.mylove.loglib.JLog;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

/**
 * @author BenYanYi
 * @date 2018/12/13 22:39
 * @email ben@yanyi.red
 * @overview
 */
public class SignPresenter extends BasePresenter<BaseView<SignEntity>> {
    public void sign(){
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .sign()
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<SignEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<SignEntity> t) throws Exception {
                        JLog.v(t);
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
