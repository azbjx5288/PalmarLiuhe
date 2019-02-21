package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.BetNumberEntity;
import com.liuheonline.la.network.api.IBetLotteryServer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.List;

/**
 * @author: aini
 * @date 2018/7/26 11:53
 * @description 投注号码
 */
public class BetNumberPresenter extends BasePresenter<BaseView<List<BetNumberEntity>>>{

    public void getBetNumber(int pid){

        getView().onLoading();

        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IBetLotteryServer.class)
                .betNumber(pid,100)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<List<BetNumberEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<BetNumberEntity>> t) throws Exception {
                        getView().successed(t.getmData());
                    }

                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, throwable -> {
                    getView().onLoadFailed(throwable.hashCode(),throwable.getLocalizedMessage());
                }, () -> {
                });
    }
}
