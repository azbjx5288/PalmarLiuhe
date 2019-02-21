package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.BetLotteryLogDetailsEntity;
import com.liuheonline.la.network.api.IUserServer;
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
 * @date 2018/7/13 16:14
 * @description 单注订单详情
 */
public class BetLotteryLogDetalsPresenter extends BasePresenter<BaseView<BetLotteryLogDetailsEntity>> {

    public void betLotteryLogDetails(int otherId){
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .betLotteryLogDetais(otherId)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<BetLotteryLogDetailsEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<BetLotteryLogDetailsEntity> t) throws Exception {
                        getView().successed(t.getmData());
                    }
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, new BaseErroeConsumer() {
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code,error);
                    }
                }, () -> {
                });
    }
}
