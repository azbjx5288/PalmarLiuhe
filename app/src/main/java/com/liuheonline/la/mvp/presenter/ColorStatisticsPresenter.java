package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.ColorStatisticsEntity;
import com.liuheonline.la.network.api.ILotteryServer;
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

import io.reactivex.functions.Action;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class ColorStatisticsPresenter extends BasePresenter<BaseView<ColorStatisticsEntity>>{

    /**
     * @description 获取波色统计
     * @param
     * @return void
     */
    public void getColorStatistics(int period,int type,String sid){
        Map<String,Object> map = new HashMap<>();
        map.put("period",period);
        map.put("type",type);
        map.put("sid",sid);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(ILotteryServer.class)
                .getColorStatistics(map)
                .compose(SetThread.<BaseEntity<ColorStatisticsEntity>>io_main())
                .subscribe(new BaseConsumer<ColorStatisticsEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<ColorStatisticsEntity> t) throws Exception {
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
