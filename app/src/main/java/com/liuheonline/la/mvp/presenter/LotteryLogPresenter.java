package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.LotteryLogEntity;
import com.liuheonline.la.network.api.IUserServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description 投注记录
 */
public class LotteryLogPresenter extends BasePresenter<BaseView<List<LotteryLogEntity>>> {

    /**
     * @param
     * @return void
     * @description 投注记录
     */
    public void lotteryLog(int otherType, String dui, int page) {
        getView().onLoading();
        Map<String, Object> oMap = new HashMap<>();
        oMap.put("order_type", otherType);
        oMap.put("sid", dui);
        oMap.put("p", page);
        oMap.put("row", 10);
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .lotteryLog(oMap)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<List<LotteryLogEntity>>() {
                    @Override
                    protected void onSuccees(BaseEntity<List<LotteryLogEntity>> t) throws Exception {
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
