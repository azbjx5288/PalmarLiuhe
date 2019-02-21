package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.LotteryLogDetaisEntity;
import com.liuheonline.la.network.api.IUserServer;
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
 * @date 2018/7/13 16:14
 * @description 投注订单详情
 */
public class LotteryLogDetalsPresenter extends BasePresenter<BaseView<List<LotteryLogDetaisEntity>>> {

    /**
     * @description 投注订单详情
     * @param
     * @return void
     */
    public void lotteryLogDetails(int otherType,int page){
        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .lotteryLogDetais(otherType,page,10)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<List<LotteryLogDetaisEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<LotteryLogDetaisEntity>> t) throws Exception {
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
