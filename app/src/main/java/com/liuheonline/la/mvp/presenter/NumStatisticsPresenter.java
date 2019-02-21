package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.NumStatisticsEntity;
import com.liuheonline.la.network.api.ILotteryServer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Action;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class NumStatisticsPresenter extends BasePresenter<BaseView<NumStatisticsEntity>>{

    /**
     * @description 获取号码统计
     * @param
     * @return void
     */
    public void getNumStatistics(int period,int type,String sid){
        Map<String,Object> map = new HashMap<>();
        map.put("period",period);
        map.put("type",type);
        map.put("sid",sid);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ILotteryServer.class)
                .getNumStatistics(map)
                .compose(SetThread.<BaseEntity<NumStatisticsEntity>>io_main())
                .subscribe(new BaseConsumer<NumStatisticsEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<NumStatisticsEntity> t) throws Exception {
                        getView().successed(t.getmData());
                    }
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, new BaseErroeConsumer() {                    @Override                    protected void onCodeError(int code, String error) throws Exception {                        getView().onLoadFailed(code, error);                    }                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }
}
