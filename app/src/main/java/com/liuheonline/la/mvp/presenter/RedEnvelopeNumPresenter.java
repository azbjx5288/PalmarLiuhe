package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.RedEnvelopeEntity;
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

import io.reactivex.functions.Action;

/**
 * @author BenYanYi
 * @date 2018/12/13 13:55
 * @email ben@yanyi.red
 * @overview
 */
public class RedEnvelopeNumPresenter extends BasePresenter<BaseView<RedEnvelopeEntity>> {
    public void getNum() {
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .getRedEnvelopeNum()
                .compose(SetThread.<BaseEntity<RedEnvelopeEntity>>io_main())
                .subscribe(new BaseConsumer<RedEnvelopeEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<RedEnvelopeEntity> t) throws Exception {
                        JLog.v(t.getmData());
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
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }
}
