package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.GameTypeClass2Entity;
import com.liuheonline.la.network.api.IBetLotteryServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
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
 * @description 玩法列表
 */
public class GameTypeClassPresenter extends BasePresenter<BaseView<List<GameTypeClass2Entity>>>{

    public void getGameTypeClaa(int sid){

        getView().onLoading();

        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IBetLotteryServer.class)
                .getGameTypeClass(sid)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<List<GameTypeClass2Entity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<GameTypeClass2Entity>> t) throws Exception {
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
