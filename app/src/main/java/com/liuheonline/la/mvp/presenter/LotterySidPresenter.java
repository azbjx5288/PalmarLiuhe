package com.liuheonline.la.mvp.presenter;


import com.liuheonline.la.entity.LotteryEntity;
import com.liuheonline.la.network.api.ILotteryServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.SetThread;

public class LotterySidPresenter extends BasePresenter<BaseView<LotteryEntity>> {

    public void getSidLottery(int sid){
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ILotteryServer.class)
                .getSidLottery(sid)
                .compose(SetThread.io_main())
                .subscribe(lotteryEntityBaseEntity -> {
                    if(lotteryEntityBaseEntity.body().getmData().getLottery() != null){
                        String time = lotteryEntityBaseEntity.headers().get("X_End_Time");
                        int code = lotteryEntityBaseEntity.body().getmCode();
                        if (code == 200 || code == 201) {
                            if (lotteryEntityBaseEntity.body().getmData().getLottery() != null) {
                                lotteryEntityBaseEntity.body().getmData().getLottery().setHeader_time(time);
                            }
                            getView().successed(lotteryEntityBaseEntity.body().getmData());
                        } else {
                            getView().onLoadFailed(lotteryEntityBaseEntity.body().getmCode(), lotteryEntityBaseEntity.body().getmMessage());
                        }
                    }else{
                        //getView().onLoadFailed(lotteryEntityBaseEntity.body().getmCode(), lotteryEntityBaseEntity.body().getmMessage());
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
