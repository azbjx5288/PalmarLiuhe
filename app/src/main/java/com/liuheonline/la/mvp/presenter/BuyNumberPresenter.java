package com.liuheonline.la.mvp.presenter;

import com.google.gson.Gson;
import com.liuheonline.la.entity.LotteryOtherEntity;
import com.liuheonline.la.network.api.IBetLotteryServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.HashMap;
import java.util.Map;


/**
 * @author: aini
 * @date 2018/7/26 11:53
 * @description 提交购彩订单
 */
public class BuyNumberPresenter extends BasePresenter<BaseView<String>>{

    public void buyNumber(LotteryOtherEntity lotteryOtherEntity){

        Map<String,Object> map = new HashMap<>();
        map.put("specie_id",lotteryOtherEntity.getSpecie_id());
        map.put("bet_periods",lotteryOtherEntity.getBet_periods());
        map.put("lottery_amount",lotteryOtherEntity.getLottery_amount());
        map.put("lottery_num",lotteryOtherEntity.getLottery_num());
        map.put("lottery_rebates_price",lotteryOtherEntity.getLottery_rebates_price());
        map.put("lottery_class_id",lotteryOtherEntity.getLottery_id());
        map.put("lottery_class_name",lotteryOtherEntity.getLottery_class_name());
        String json = new Gson().toJson(lotteryOtherEntity.getBet_info());
        map.put("bet_info",json);
        getView().onLoading();
        //Log.w("thejson",json);

        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IBetLotteryServer.class)
                .buyNumber(map)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<Object>() {
                    @Override
                    protected void onSuccees(BaseEntity<Object> t) throws Exception {
                        getView().successed(t.getmMessage());
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
